package co.dijam.michael.typea101.currenttracker.interactor;

import co.dijam.michael.typea101.entities.CurrentTaskManager;
import co.dijam.michael.typea101.entities.TaskManager;
import co.dijam.michael.typea101.model.CurrentTask;
import co.dijam.michael.typea101.model.Task;

/**
 * Created by mdd23 on 9/7/2016.
 */
public class TrackerInteractorImpl implements TrackerInteractor {

    CurrentTaskManager currentTaskManager;
    TaskManager taskManager;

    public TrackerInteractorImpl(CurrentTaskManager currentTaskManager, TaskManager taskManager) {
        this.currentTaskManager = currentTaskManager;
        this.taskManager = taskManager;
    }

    @Override
    public boolean currentTaskExists() {
        return currentTaskManager.currentTaskExists();
    }

    @Override
    public CurrentTask getCurrentTask() {
        return currentTaskManager.getCurrentTask();
    }

    @Override
    public void saveFinishedTask(Task task) {
        currentTaskManager.clearCurrentTask();
        taskManager.insertTask(task);
    }
}
