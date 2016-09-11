package co.dijam.michael.typea101.modifycurrenttask.presenter;

import co.dijam.michael.typea101.modifycurrenttask.ModifyCurrentTaskContract;
import co.dijam.michael.typea101.modifycurrenttask.interactor.ModifyCurrentTaskInteractor;
import co.dijam.michael.typea101.model.CurrentTask;
import co.dijam.michael.typea101.util.TimeFormattingUtil;

/**
 * Created by mdd23 on 9/6/2016.
 */
public class ModifyCurrentTaskPresenter implements ModifyCurrentTaskContract.Presenter {
    ModifyCurrentTaskContract.View view;
    ModifyCurrentTaskInteractor interactor;

    public ModifyCurrentTaskPresenter(ModifyCurrentTaskContract.View view, ModifyCurrentTaskInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void setCurrentTask(String taskName, String tagName, long startTime) {
        CurrentTask currentTask = new CurrentTask();
        currentTask.taskName = taskName;
        currentTask.tag = tagName;
        currentTask.startTime = startTime;
        interactor.setOngoingTask(currentTask);
        view.closeModifyTaskView();
    }

    @Override
    public void clearCurrentTask() {
        interactor.clearCurrentTask();
        view.closeModifyTaskView();
    }

    @Override
    public void autoCompleteTaskNames() {
        interactor.getAllTasks()
                .map(task -> task.taskName)
                .distinct()
                .toList()
                .subscribe(tasks -> view.autoCompleteTaskNames(tasks));
    }

    @Override
    public void autoCompleteTags() {
        interactor.getAllTasks()
                .map(task -> task.tag)
                .distinct()
                .toList()
                .subscribe(tags -> view.autoCompleteTags(tags));
    }

    @Override
    public boolean currentTaskExists() {
        return interactor.currentTaskExists();
    }

    @Override
    public void setupEditView() {
        view.showEditButtonsOnly();
        CurrentTask currentTask = interactor.getCurrentTask();
        view.setCurrentTaskTaskName(currentTask.taskName);
        view.setCurrentTaskTag(currentTask.tag);
        view.showStartTime(TimeFormattingUtil.timeFormatter.print(currentTask.startTime));
    }

    @Override
    public void setupAddView(long startTime) {
        view.showAddButtonsOnly();
        view.showStartTime(TimeFormattingUtil.timeFormatter.print(startTime));
    }

    @Override
    public long getCurrentTaskStartTime() {
        return interactor.getCurrentTask().startTime;
    }
}
