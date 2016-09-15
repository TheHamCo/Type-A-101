package co.dijam.michael.typea101.modifytask;

import java.util.List;

import co.dijam.michael.typea101.dailylist.model.TaskPrintable;
import rx.Observable;

/**
 * Created by mdd23 on 9/13/2016.
 */
public interface ModifyTaskContract {
    interface View {
        // Data
        void setStartTime(long startTime);
        void setEndTime(long endTime);

        // Pickers
        void showStartDayPicker();
        void showEndDayPicker();
        void showStartTimePicker();
        void showEndTimePicker();

        // Views
        void showTaskName(String taskName);
        void showTag(String tag);
        void showStartDay(String formattedDay);
        void showStartTime(String startTime);
        void showEndDay(String formattedDay);
        void showEndTime(String endTime);
        void showDuration(String formattedDuration);
        void showPercentage(String formattedPercentage);

        // Errors
        void showErrorList();
        void hideErrorList();
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
        void confirmModifyTask();
    }

    interface Presenter {
        boolean validateInput(int taskId, String taskName, String tag, long startTime, long endTime);
        void restoreViews(long startTime, long endTime);

        // Errors
        boolean taskOverlapsOtherTasksError(int taskId, long startTime, long endTime);
        boolean taskInFutureError(long startTime, long endTime);
        boolean taskStartTimeAfterEndTimeError(long startTime, long endTime);
        boolean taskHasNoDurationError(long startTime, long endTime);
        boolean taskNameIsValid(String taskName);
        boolean tagIsValid(String tag);

        // Data
        void getTaskDetails(int taskId);
        void getNearestTaskBefore(long startTime, long endTime);
        void getNearestTaskAfter(long startTime, long endTime);

        // Final
        void saveTask(int taskId, String taskName, String tag, long startDateTime, long endDateTime);
    }
}
