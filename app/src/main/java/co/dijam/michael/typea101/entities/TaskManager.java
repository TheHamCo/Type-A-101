package co.dijam.michael.typea101.entities;

import org.joda.time.DateTime;

import co.dijam.michael.typea101.model.Task;
import rx.Observable;

/**
 * Created by mdd23 on 9/7/2016.
 */
public interface TaskManager {
    Observable<Task> getAllTasks();
    Observable<Task> getAllTasksForOneDay(long dateTime);
    Observable<Task> getTask(int id);

    int insertTask(Task task);
    void editTask(Task editedTask);
    void deleteTask(int id);
}
}
