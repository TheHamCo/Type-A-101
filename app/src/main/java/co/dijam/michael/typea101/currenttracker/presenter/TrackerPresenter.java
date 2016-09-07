package co.dijam.michael.typea101.currenttracker.presenter;

import co.dijam.michael.typea101.currenttracker.TrackerContract;
import co.dijam.michael.typea101.entities.CurrentTaskManager;

/**
 * Created by mdd23 on 9/6/2016.
 */
public class TrackerPresenter implements TrackerContract.Presenter {
    TrackerContract.View view;
    CurrentTaskManager currentTaskManager;

    public TrackerPresenter(TrackerContract.View view, CurrentTaskManager currentTaskManager) {
        this.view = view;
        this.currentTaskManager = currentTaskManager;
    }

    @Override
    public void getCurrentTask() {

    }

    @Override
    public void runTimer() {

    }

    @Override
    public void stopTimer() {

    }

    @Override
    public void editCurrentTask() {

    }

    @Override
    public void addNotesToCurrentTask() {

    }

    @Override
    public void finishTracking() {

    }
}
