package co.dijam.michael.typea101.currenttracker.presenter;

import android.util.Log;

import org.joda.time.Period;

import java.util.concurrent.TimeUnit;

import co.dijam.michael.typea101.currenttracker.TrackerContract;
import co.dijam.michael.typea101.entities.CurrentTaskManager;
import co.dijam.michael.typea101.model.CurrentTask;
import co.dijam.michael.typea101.util.TimeFormattingUtil;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mdd23 on 9/6/2016.
 */
public class TrackerPresenter implements TrackerContract.Presenter {
    private static final String TAG = TrackerPresenter.class.getName();

    TrackerContract.View view;
    CurrentTaskManager currentTaskManager;
    Subscription timerSubscription;

    CurrentTask ct;

    public TrackerPresenter(TrackerContract.View view, CurrentTaskManager currentTaskManager) {
        this.view = view;
        this.currentTaskManager = currentTaskManager;
    }

    @Override
    public void getCurrentTask() {
        if (currentTaskManager.currentTaskExists()) {
            ct = currentTaskManager.getCurrentTask();
            view.showTracker();
            view.showTaskName(ct.taskName);
            view.showTag(ct.tag);
            view.showTaskStartTime(TimeFormattingUtil.dateTimeFormatter.print(ct.startTime));
            runTimer();
        } else {
            view.hideTracker();
        }
    }

    @Override
    public void runTimer() {
        timerSubscription = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tick -> {
                    Period period = new Period(ct.startTime, System.currentTimeMillis());
                    view.updateTimer(TimeFormattingUtil.durationFormatter.print(period));
                },throwable -> Log.e(TAG, "runTimer: ", throwable));

    }

    @Override
    public void stopTimer() {
        if (timerSubscription != null && !timerSubscription.isUnsubscribed()){
            timerSubscription.unsubscribe();
        }
    }

    @Override
    public void editCurrentTask() {
        view.startEditTask();
    }

    @Override
    public void addNotesToCurrentTask() {
        view.startAddNotes();
    }

    @Override
    public void finishTracking() {
        stopTimer();
        view.hideTracker();
        currentTaskManager.clearCurrentTask();
        //TODO Persist data
    }
}
