package co.dijam.michael.typea101.modifycurrenttask.interactor;

import co.dijam.michael.typea101.model.CurrentTask;
import co.dijam.michael.typea101.model.Task;
import rx.Observable;

/**
 * Created by mdd23 on 9/6/2016.
 */
public interface ModifyCurrentTaskInteractor {
    void setOngoingTask(CurrentTask currentTask);
    Observable<Task> getAllTasks();
}
