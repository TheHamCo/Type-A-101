package co.dijam.michael.typea101.modifytask.interactor;

import co.dijam.michael.typea101.converter.CurrentTaskToTaskPrintableConverter;
import co.dijam.michael.typea101.converter.TasktoTaskPrintableConverter;
import co.dijam.michael.typea101.dailylist.model.TaskPrintable;
import co.dijam.michael.typea101.entities.CurrentTaskManager;
import co.dijam.michael.typea101.entities.TaskManager;
import co.dijam.michael.typea101.model.Task;
import rx.Observable;

/**
 * Created by mdd23 on 9/13/2016.
 */
public class ModifyTaskInteractorImpl implements ModifyTaskInteractor {
    private static final String TAG = ModifyTaskInteractorImpl.class.getName();

    CurrentTaskManager currentTaskManager;
    TaskManager taskManager;

    public ModifyTaskInteractorImpl(CurrentTaskManager currentTaskManager, TaskManager taskManager) {
        this.currentTaskManager = currentTaskManager;
        this.taskManager = taskManager;
    }

    @Override
    public boolean taskOverlapsOtherTasksError(int taskId, long startTime, long endTime) {
        final boolean[] taskOverlaps = {false};

        if (taskOverlapsWithCurrentTask(endTime)){
            taskOverlaps[0] = true;
        }

        getOverlappingTasks(taskId, startTime, endTime)
                .count()
                .subscribe(numOverlappingTasks -> {
//                    Log.d(TAG, "taskOverlapsOtherTasksError: " + numOverlappingTasks);
                    if (numOverlappingTasks > 0){
                        taskOverlaps[0] = true;
                    }
                });
        return taskOverlaps[0];
    }

    private boolean taskOverlapsWithCurrentTask(long endTime) {
        return currentTaskManager.currentTaskExists() && endTime > currentTaskManager.getCurrentTask().startTime;
    }

    @Override
    public Observable<TaskPrintable> getOverlappingTasks(int taskId, long startTime, long endTime) {
        Observable<TaskPrintable> overlaps = taskManager.getAllTasks()
                .filter(task ->
                        (startTime < task.endTime && startTime > task.startTime)
                                || (endTime > task.startTime && endTime < task.endTime))
                .filter(task1 -> task1.id != taskId)
//                .doOnNext(task -> Log.d(TAG, "getOverlappingTasks: " + task.taskName))
                .map(TasktoTaskPrintableConverter::formatTask);

        if (taskOverlapsWithCurrentTask(endTime)){
            overlaps = overlaps
                    .concatWith(
                            Observable.just(currentTaskManager.getCurrentTask())
                            .map(CurrentTaskToTaskPrintableConverter::formatTask)
                    );
        }

        return overlaps;
    }

    @Override
    public Observable<Task> getTaskDetails(int taskId) {
        return taskManager
                .getTask(taskId);
    }

    @Override
    public Observable<TaskPrintable> getNearestTaskBefore(long startTime, long endTime) {
        return taskManager.getAllTasksForOneDay(startTime)
                .filter(task -> task.endTime < startTime)
                .sorted((task1, task2) -> Long.valueOf(task1.endTime).compareTo(task2.endTime))
                .last()
                .map(TasktoTaskPrintableConverter::formatTask);
    }

    @Override
    public Observable<TaskPrintable> getNearestTaskAfter(long startTime, long endTime) {
        return taskManager.getAllTasksForOneDay(endTime)
                .filter(task -> task.startTime > endTime)
                .sorted((task1, task2) -> Long.valueOf(task1.endTime).compareTo(task2.endTime))
                .first()
                .map(TasktoTaskPrintableConverter::formatTask);

    }

    @Override
    public void saveTask(Task task) {
        taskManager.insertTask(task);
    }

    @Override
    public void updateTask(Task editedTask) {
        try {
            taskManager.editTask(editedTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
