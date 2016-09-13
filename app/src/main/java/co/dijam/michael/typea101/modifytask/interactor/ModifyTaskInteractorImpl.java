package co.dijam.michael.typea101.modifytask.interactor;

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

    CurrentTaskManager currentTaskManager;
    TaskManager taskManager;

    public ModifyTaskInteractorImpl(CurrentTaskManager currentTaskManager, TaskManager taskManager) {
        this.currentTaskManager = currentTaskManager;
        this.taskManager = taskManager;
    }

    @Override
    public boolean taskOverlapsOtherTasksError(long startTime, long endTime) {
        final boolean[] taskOverlaps = {false};

        if (endTime > currentTaskManager.getCurrentTask().startTime){
            taskOverlaps[0] = true;
        }

        getOverlappingTasks(startTime, endTime)
                .count()
                .subscribe(numOverlappingTasks -> {
                    if (numOverlappingTasks > 0){
                        taskOverlaps[0] = true;
                    }
                });

        return taskOverlaps[0];
    }

    @Override
    public Observable<TaskPrintable> getOverlappingTasks(long startTime, long endTime) {
        return taskManager.getAllTasks()
                .filter(task ->
                        (startTime < task.endTime && startTime > task.startTime)
                        || (endTime > task.startTime && endTime < task.endTime))
                .map(TasktoTaskPrintableConverter::formatTask);
    }

    @Override
    public Observable<TaskPrintable> getTaskDetails(int taskId) {
        return taskManager
                .getTask(taskId)
                .map(TasktoTaskPrintableConverter::formatTask);
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
}
