package co.dijam.michael.typea101.addcurrenttask.interactor;

import co.dijam.michael.typea101.entities.CurrentTaskManager;
import co.dijam.michael.typea101.model.CurrentTask;

/**
 * Created by mdd23 on 9/6/2016.
 */
public class AddCurrentTaskInteractorImpl implements AddCurrentTaskInteractor {

    CurrentTaskManager currentTaskManager;

    public AddCurrentTaskInteractorImpl(CurrentTaskManager currentTaskManager) {
        this.currentTaskManager = currentTaskManager;
    }

    @Override
    public void setOngoingTask(CurrentTask currentTask) {
        currentTaskManager.setCurrentTask(currentTask);
    }
}
