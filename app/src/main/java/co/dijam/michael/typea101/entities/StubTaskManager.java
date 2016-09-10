package co.dijam.michael.typea101.entities;

import co.dijam.michael.typea101.model.Task;
import rx.Observable;

/**
 * Created by mdd23 on 9/7/2016.
 */
public class StubTaskManager implements TaskManager {
    @Override
    public Observable<Task> getAllTasks() {
        return null;
    }

    @Override
    public Observable<Task> getAllTasksForOneDay(long dateTime) {
        return null;
    }

    @Override
    public Observable<Task> getTask(int id) {
        return null;
    }

    @Override
    public int insertTask(Task newTask) {
        return 0;
    }

    @Override
    public void editTask(Task editedTask) throws Exception {

    }

    @Override
    public void deleteTask(int id) {

    }
}
