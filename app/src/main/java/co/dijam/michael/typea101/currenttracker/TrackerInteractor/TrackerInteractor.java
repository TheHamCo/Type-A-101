package co.dijam.michael.typea101.currenttracker.TrackerInteractor;

import co.dijam.michael.typea101.model.CurrentTask;
import co.dijam.michael.typea101.model.Task;

/**
 * Created by mdd23 on 9/7/2016.
 */
public interface TrackerInteractor {
    boolean currentTaskExists();
    CurrentTask getCurrentTask();
    void saveFinishedTask(Task task);
}
