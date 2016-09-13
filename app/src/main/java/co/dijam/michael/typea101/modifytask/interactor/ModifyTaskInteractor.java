package co.dijam.michael.typea101.modifytask.interactor;

import co.dijam.michael.typea101.dailylist.model.TaskPrintable;
import co.dijam.michael.typea101.model.Task;
import rx.Observable;

/**
 * Created by mdd23 on 9/13/2016.
 */
public interface ModifyTaskInteractor {
    // Errors
    boolean taskOverlapsOtherTasksError(long startTime, long endTime);
    Observable<TaskPrintable> getOverlappingTasks(long startTime, long endTime);

    // Data
    Observable<TaskPrintable> getTaskDetails(int taskId);
    Observable<TaskPrintable> getNearestTaskBefore(long startTime, long endTime);
    Observable<TaskPrintable> getNearestTaskAfter(long startTime, long endTime);

    // Final
    void saveTask(Task task);
}
