package co.dijam.michael.typea101.addcurrenttask.interactor;

import co.dijam.michael.typea101.entities.CurrentTaskManager;
import co.dijam.michael.typea101.entities.TaskManager;
import co.dijam.michael.typea101.model.CurrentTask;
import co.dijam.michael.typea101.model.Task;
import rx.Observable;

/**
 * Created by mdd23 on 9/6/2016.
 */
public class AddCurrentTaskInteractorImpl implements AddCurrentTaskInteractor {

    CurrentTaskManager currentTaskManager;
    TaskManager taskManager;

    public AddCurrentTaskInteractorImpl(CurrentTaskManager currentTaskManager, TaskManager taskManager) {
        this.currentTaskManager = currentTaskManager;
        this.taskManager = taskManager;
    }

    @Override
    public void setOngoingTask(CurrentTask currentTask) {
        currentTaskManager.setCurrentTask(currentTask);
    }

    @Override
    public Observable<Task> getAllTasks() {
        return taskManager.getAllTasks();
    }
}
