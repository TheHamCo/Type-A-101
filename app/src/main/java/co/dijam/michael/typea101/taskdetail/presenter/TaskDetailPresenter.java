package co.dijam.michael.typea101.taskdetail.presenter;

import co.dijam.michael.typea101.taskdetail.TaskDetailContract;
import co.dijam.michael.typea101.taskdetail.interactor.TaskDetailInteractor;

/**
 * Created by mdd23 on 9/11/2016.
 */
public class TaskDetailPresenter implements TaskDetailContract.Presenter {

    TaskDetailInteractor interactor;
    TaskDetailContract.View view;

    public TaskDetailPresenter(TaskDetailInteractor interactor, TaskDetailContract.View view) {
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void getTaskDetails(int id) {
        interactor.getTaskDetail(id)
                .subscribe(taskDetail -> view.displayTaskDetail(taskDetail));
    }

    @Override
    public void getNotes(int taskId) {

    }

    @Override
    public void deleteTask(int taskId) {
        interactor.deleteTask(taskId);
    }
}
