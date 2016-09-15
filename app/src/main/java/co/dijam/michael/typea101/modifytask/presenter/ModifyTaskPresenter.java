package co.dijam.michael.typea101.modifytask.presenter;

import org.joda.time.Period;

import co.dijam.michael.typea101.model.Task;
import co.dijam.michael.typea101.modifytask.ModifyTaskConstants;
import co.dijam.michael.typea101.modifytask.ModifyTaskContract;
import co.dijam.michael.typea101.modifytask.interactor.ModifyTaskInteractor;

import static co.dijam.michael.typea101.util.TimeFormattingUtil.dateFormatter;
import static co.dijam.michael.typea101.util.TimeFormattingUtil.durationFormatter;
import static co.dijam.michael.typea101.util.TimeFormattingUtil.percentageFormatter;
import static co.dijam.michael.typea101.util.TimeFormattingUtil.timeFormatter;

/**
 * Created by mdd23 on 9/13/2016.
 */
public class ModifyTaskPresenter implements ModifyTaskContract.Presenter {

    ModifyTaskInteractor interactor;
    ModifyTaskContract.View view;

    public ModifyTaskPresenter(ModifyTaskInteractor interactor, ModifyTaskContract.View view) {
        this.interactor = interactor;
        this.view = view;
    }


    @Override
    public boolean validateInput(int taskId, String taskName, String tag, long startTime, long endTime) {
        boolean isValid = true;

        if (!taskNameIsValid(taskName)) {
            view.showErrorTaskNameInvalid();
            isValid = false;
        }

        if (!tagIsValid(tag)) {
            view.showErrorTagInvalid();
            isValid = false;
        }

        // Task Overlap is irrelevant if start time is before end time
        if (!taskStartTimeAfterEndTimeError(startTime, endTime)
                && taskOverlapsOtherTasksError(taskId, startTime, endTime)){
            view.showErrorOverlappingTask(
                    interactor.getOverlappingTasks(taskId, startTime, endTime).toList()
            );
            isValid = false;
        }

        if (taskInFutureError(startTime, endTime)){
            view.showErrorTaskInFuture();
            isValid = false;
        }

        if (taskStartTimeAfterEndTimeError(startTime, endTime)) {
            view.showErrorStartTimeAfterEndTime();
            isValid = false;
        }

        if (taskHasNoDurationError(startTime, endTime)) {
            view.showErrorNoDuration();
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void restoreViews(long startTime, long endTime) {
        view.showStartDay(dateFormatter.print(startTime));
        view.showStartTime(timeFormatter.print(startTime));
        view.showEndDay(dateFormatter.print(endTime));
        view.showEndTime(timeFormatter.print(endTime));
        view.showDuration(durationFormatter.print(new Period(startTime, endTime)));
        view.showPercentage(percentageFormatter(startTime, endTime));
    }

    @Override
    public boolean taskOverlapsOtherTasksError(int taskId, long startTime, long endTime) {
        return interactor.taskOverlapsOtherTasksError(taskId, startTime, endTime);
    }

    @Override
    public boolean taskInFutureError(long startTime, long endTime) {
        return endTime > System.currentTimeMillis();
    }

    @Override
    public boolean taskStartTimeAfterEndTimeError(long startTime, long endTime) {
        return endTime < startTime;
    }

    @Override
    public boolean taskHasNoDurationError(long startTime, long endTime) {
        return startTime == endTime;
    }

    @Override
    public boolean taskNameIsValid(String taskName) {
        return !taskName.trim().isEmpty();
    }

    @Override
    public boolean tagIsValid(String tag) {
        return !tag.trim().isEmpty();
    }

    @Override
    public void getTaskDetails(int taskId) {
        interactor.getTaskDetails(taskId)
                .subscribe(task -> {
                    view.showTaskName(task.taskName);
                    view.showTag(task.tag);
                    view.showStartDay(dateFormatter.print(task.startTime));
                    view.showStartTime(timeFormatter.print(task.startTime));
                    view.showEndDay(dateFormatter.print(task.endTime));
                    view.showEndTime(timeFormatter.print(task.endTime));
                    view.showDuration(durationFormatter.print(new Period(task.startTime, task.endTime)));
                    view.showPercentage(percentageFormatter(task.startTime, task.endTime));

                    view.setStartTime(task.startTime);
                    view.setEndTime(task.endTime);
                });
    }

    @Override
    public void getNearestTaskBefore(long startTime, long endTime) {
        interactor.getNearestTaskBefore(startTime, endTime)
                .subscribe(taskPrintable -> view.suggestNearestTaskBefore(taskPrintable));
    }

    @Override
    public void getNearestTaskAfter(long startTime, long endTime) {
        interactor.getNearestTaskAfter(startTime, endTime)
                .subscribe(taskPrintable -> view.suggestNearestTaskAfter(taskPrintable));
    }

    @Override
    public void saveTask(int taskId, String taskName, String tag, long startDateTime, long endDateTime) {
        Task task = new Task();
        task.id = taskId;
        task.taskName = taskName;
        task.tag = tag;
        task.startTime = startDateTime;
        task.endTime = endDateTime;

        if (taskId == ModifyTaskConstants.NO_TASK_ID) {
            interactor.saveTask(task);
        } else {
            interactor.updateTask(task);
        }
    }
}
