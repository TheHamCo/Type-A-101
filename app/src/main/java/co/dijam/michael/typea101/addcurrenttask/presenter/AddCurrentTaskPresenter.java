package co.dijam.michael.typea101.addcurrenttask.presenter;

import co.dijam.michael.typea101.addcurrenttask.AddCurrentTaskContract;
import co.dijam.michael.typea101.addcurrenttask.interactor.AddCurrentTaskInteractor;
import co.dijam.michael.typea101.model.CurrentTask;

/**
 * Created by mdd23 on 9/6/2016.
 */
public class AddCurrentTaskPresenter implements AddCurrentTaskContract.Presenter {
    AddCurrentTaskContract.View view;
    AddCurrentTaskInteractor interactor;

    public AddCurrentTaskPresenter(AddCurrentTaskContract.View view, AddCurrentTaskInteractor interactor) {
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
        view.closeAddTaskView();
    }
}
