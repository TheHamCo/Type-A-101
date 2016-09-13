package co.dijam.michael.typea101.modifytask.presenter;

import co.dijam.michael.typea101.model.Task;
import co.dijam.michael.typea101.modifytask.ModifyTaskContract;
import co.dijam.michael.typea101.modifytask.interactor.ModifyTaskInteractor;

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
    public void validateTime(long startTime, long endTime) {
        if (taskOverlapsOtherTasksError(startTime, endTime)){
            view.showErrorOverlappingTask(
                    interactor.getOverlappingTasks(startTime, endTime).toList()
            );
        } else if (taskInFutureError(startTime, endTime)){
            view.showErrorTaskInFuture();
        } else if (taskStartTimeAfterEndTimeError(startTime, endTime)) {
            view.showErrorStartTimeAfterEndTime();
        } else if (taskHasNoDurationerror(startTime, endTime)) {
            view.showErrorNoDuration();
        }
    }

    @Override
    public boolean taskOverlapsOtherTasksError(long startTime, long endTime) {
        return interactor.taskOverlapsOtherTasksError(startTime, endTime);
    }

    @Override
    public boolean taskInFutureError(long startTime, long endTime) {
        return endTime > System.currentTimeMillis();
    }

    @Override
    public boolean taskStartTimeAfterEndTimeError(long startTime, long endTime) {
        return endTime > startTime;
    }

    @Override
    public boolean taskHasNoDurationerror(long startTime, long endTime) {
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
                .subscribe(taskPrintable -> {
                    view.showTaskName(taskPrintable.taskName);
                    view.showTag(taskPrintable.tag);
                    view.showStartTime(taskPrintable.formattedStartTime);
                    view.showEndTime(taskPrintable.formattedEndTime);
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
    public void saveTask(Task task) {
        interactor.saveTask(task);
    }
}
