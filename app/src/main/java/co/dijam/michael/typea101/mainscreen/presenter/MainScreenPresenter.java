package co.dijam.michael.typea101.mainscreen.presenter;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.concurrent.TimeUnit;

import co.dijam.michael.typea101.entities.CurrentTaskManager;
import co.dijam.michael.typea101.mainscreen.MainScreenContract;
import co.dijam.michael.typea101.model.CurrentTask;
import co.dijam.michael.typea101.util.TimeFormattingUtil;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mdd23 on 9/7/2016.
 */
public class MainScreenPresenter implements MainScreenContract.Presenter {
    private final String TAG = MainScreenPresenter.class.getName();

    MainScreenContract.View view;
    CurrentTaskManager currentTaskManager;

    Subscription timerSubscription;

    public MainScreenPresenter(CurrentTaskManager currentTaskManager, MainScreenContract.View view) {
        this.currentTaskManager = currentTaskManager;
        this.view = view;
    }

    @Override
    public boolean currentTaskExists() {
        return  currentTaskManager.currentTaskExists();
    }

    @Override
    public boolean isToday(long dateTime) {
        DateTime midnightToday =  DateTime.now().withTimeAtStartOfDay();
        DateTime dayInQuestion = new DateTime(dateTime);
        return dayInQuestion.isAfter(midnightToday);
    }

    @Override
    public void presentCorrectMainView(long dateTime) {
        if (isToday(dateTime)){
            view.hideSnackbar();
            view.disableNextDayButton();
            if (currentTaskManager.currentTaskExists()){
                view.showTracker();
            }
        } else {
            if (currentTaskManager.currentTaskExists()){
                view.showSnackbar();
            }
            view.hideTracker();
            view.updateList(dateTime);
        }
        view.showDate(TimeFormattingUtil.dateFormatter.print(dateTime));
    }

    @Override
    public void runSnackbarTimer() {
        timerSubscription = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tick -> {
                    CurrentTask ct = currentTaskManager.getCurrentTask();
                    Period period = new Period(ct.startTime, System.currentTimeMillis());
                    String formattedDuration = TimeFormattingUtil.durationFormatter.print(period);
                    view.updateTimerSnackbar(ct.taskName, ct.tag, formattedDuration);
                },throwable -> Log.e(TAG, "runSnackbarTimer: ", throwable));
    }

    @Override
    public void stopSnackBarTimer() {
        if (timerSubscription != null && !timerSubscription.isUnsubscribed()){
            timerSubscription.unsubscribe();
        }
    }
}
