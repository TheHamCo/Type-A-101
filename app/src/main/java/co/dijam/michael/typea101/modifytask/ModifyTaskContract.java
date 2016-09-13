package co.dijam.michael.typea101.modifytask;

import java.util.List;

import co.dijam.michael.typea101.dailylist.model.TaskPrintable;
import co.dijam.michael.typea101.model.Task;
import rx.Observable;

/**
 * Created by mdd23 on 9/13/2016.
 */
public interface ModifyTaskContract {
    interface View {
        // Views
        void showTaskName(String taskName);
        void showTag(String tag);
        void showStartTime(String startTime);
        void showEndTime(String endTime);

        // Errors
        void showErrorOverlappingTask(Observable<List<TaskPrintable>> overlappingTasks);
        void showErrorTaskInFuture();
        void showErrorStartTimeAfterEndTime();
        void showErrorNoDuration();
        void showErrorTaskNameInvalid();
        void showErrorTagInvalid();

        // Autocomplete
        void autocompleteTaskNames(List<String> taskNames);
        void autocompleteTags(List<String> tags);
        void suggestNearestTaskBefore(TaskPrintable taskBefore);
        void suggestNearestTaskAfter(TaskPrintable taskAfter);

        // Final
        void closeModifyTask();
    }

    interface Presenter {
        void validateTime(long startTime, long endTime);

        // Errors
        boolean taskOverlapsOtherTasksError(long startTime, long endTime);
        boolean taskInFutureError(long startTime, long endTime);
        boolean taskStartTimeAfterEndTimeError(long startTime, long endTime);
        boolean taskHasNoDurationerror(long startTime, long endTime);
        boolean taskNameIsValid(String taskName);
        boolean tagIsValid(String tag);

        // Data
        void getTaskDetails(int taskId);
        void getNearestTaskBefore(long startTime, long endTime);
        void getNearestTaskAfter(long startTime, long endTime);

        // Final
        void saveTask(Task task);
    }
}
