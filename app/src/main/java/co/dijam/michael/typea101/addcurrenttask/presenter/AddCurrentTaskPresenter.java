package co.dijam.michael.typea101.addcurrenttask.presenter;

import co.dijam.michael.typea101.addcurrenttask.AddCurrentTaskContract;

/**
 * Created by mdd23 on 9/6/2016.
 */
public class AddCurrentTaskPresenter implements AddCurrentTaskContract.Presenter {
    AddCurrentTaskContract.View view;

    public AddCurrentTaskPresenter(AddCurrentTaskContract.View view) {
        this.view = view;
    }

    @Override
    public void setCurrentTask(String taskName, String tagName, long startTime) {
        //TODO Persist current task information
        view.closeAddTaskView();
    }
}
