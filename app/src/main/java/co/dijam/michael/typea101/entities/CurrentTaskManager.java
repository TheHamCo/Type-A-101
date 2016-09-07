package co.dijam.michael.typea101.entities;

import co.dijam.michael.typea101.model.CurrentTask;

/**
 * Created by mdd23 on 9/6/2016.
 */
public interface CurrentTaskManager {
    CurrentTask getCurrentTask();
    void setCurrentTask(CurrentTask currentTask);
    void clearCurrentTask();
}
