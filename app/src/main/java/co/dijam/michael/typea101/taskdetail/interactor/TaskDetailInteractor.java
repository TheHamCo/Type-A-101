package co.dijam.michael.typea101.taskdetail.interactor;

import co.dijam.michael.typea101.model.Task;
import co.dijam.michael.typea101.taskdetail.model.NoteListItem;
import co.dijam.michael.typea101.taskdetail.model.TaskDetail;
import rx.Observable;

/**
 * Created by mdd23 on 9/11/2016.
 */
public interface TaskDetailInteractor {
    Observable<TaskDetail> getTaskDetail(int id);
    Observable<NoteListItem> getNotes(int taskId);
    TaskDetail formatTask(Task task);
    void deleteTask(int taskId);
}
