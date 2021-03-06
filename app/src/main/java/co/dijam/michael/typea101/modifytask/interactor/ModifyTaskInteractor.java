package co.dijam.michael.typea101.modifytask.interactor;

import co.dijam.michael.typea101.dailylist.model.TaskPrintable;
import co.dijam.michael.typea101.model.Task;
import rx.Observable;

/**
 * Created by mdd23 on 9/13/2016.
 */
public interface ModifyTaskInteractor {
    // Errors
    boolean taskOverlapsOtherTasksError(int taskId,long startTime, long endTime);
    Observable<TaskPrintable> getOverlappingTasks(int taskId,long startTime, long endTime);

    // Data
    Observable<Task> getTaskDetails(int taskId);
    Observable<TaskPrintable> getNearestTaskBefore(long startTime, long endTime);
    Observable<TaskPrintable> getNearestTaskAfter(long startTime, long endTime);
    Observable<TaskPrintable> getAllTasksForOneDay(long dateTime);

    // Final
    void saveTask(Task task);
    void updateTask(Task editedTask);
}
