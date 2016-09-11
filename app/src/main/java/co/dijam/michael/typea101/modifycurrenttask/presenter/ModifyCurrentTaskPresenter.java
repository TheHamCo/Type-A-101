package co.dijam.michael.typea101.modifycurrenttask.presenter;

import co.dijam.michael.typea101.modifycurrenttask.ModifyCurrentTaskContract;
import co.dijam.michael.typea101.modifycurrenttask.interactor.ModifyCurrentTaskInteractor;
import co.dijam.michael.typea101.model.CurrentTask;

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
        view.closeAddTaskView();
    }

    @Override
    public void autoCompleteTaskNames() {
        interactor.getAllTasks()
                .map(task -> task.taskName)
                .toList()
                .subscribe(tasks -> view.autoCompleteTaskNames(tasks));
    }

    @Override
    public void autoCompleteTags() {
        interactor.getAllTasks()
                .map(task -> task.tag)
                .toList()
                .subscribe(tags -> view.autoCompleteTags(tags));
    }
}
