package co.dijam.michael.typea101.mainscreen.presenter;

import org.joda.time.DateTime;

import co.dijam.michael.typea101.entities.CurrentTaskManager;
import co.dijam.michael.typea101.mainscreen.MainScreenContract;

/**
 * Created by mdd23 on 9/7/2016.
 */
public class MainScreenPresenter implements MainScreenContract.Presenter {

    MainScreenContract.View view;
    CurrentTaskManager currentTaskManager;

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
    public void runTimer() {

    }

    @Override
    public void stopTimer() {

    }
}
