package co.dijam.michael.typea101.currenttracker.presenter;

import android.util.Log;

import org.joda.time.Period;

import java.util.concurrent.TimeUnit;

import co.dijam.michael.typea101.currenttracker.TrackerContract;
import co.dijam.michael.typea101.currenttracker.TrackerInteractor.TrackerInteractor;
import co.dijam.michael.typea101.model.CurrentTask;
import co.dijam.michael.typea101.model.Task;
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
    TrackerInteractor interactor;
    Subscription timerSubscription;

    CurrentTask ct;

    public TrackerPresenter(TrackerContract.View view, TrackerInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void getCurrentTask() {
        if (interactor.currentTaskExists()) {
            ct = interactor.getCurrentTask();
            view.showTracker();
            view.showTaskName(ct.taskName);
            view.showTag(ct.tag);
            view.showTaskStartTime(TimeFormattingUtil.timeFormatter.print(ct.startTime));
            // View runs timer due to lifecycle
            // runTimer();
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
                    String formattedDuration = TimeFormattingUtil.durationFormatter.print(period);
                    view.updateTimer(formattedDuration);
                    Log.d(TAG, "runTimer: " + formattedDuration);
                },throwable -> Log.e(TAG, "runTimer: ", throwable));

    }

    @Override
    public void stopTimer() {
        if (timerSubscription != null && !timerSubscription.isUnsubscribed()){
            timerSubscription.unsubscribe();
            Log.d(TAG, "stopTimer: STOP TRACKER");
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
        Task finishedTask = new Task();
        finishedTask.taskName = ct.taskName;
        finishedTask.tag = ct.tag;
        finishedTask.startTime = ct.startTime;
        finishedTask.endTime = System.currentTimeMillis();
        interactor.saveFinishedTask(finishedTask);
    }
}
