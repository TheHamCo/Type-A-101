package co.dijam.michael.typea101.dailylist.presenter;

import android.util.Log;

import co.dijam.michael.typea101.dailylist.DailyListContract;
import co.dijam.michael.typea101.dailylist.interactor.DailyListInteractor;

/**
 * Created by mdd23 on 9/10/2016.
 */
public class DailyListPresenter implements DailyListContract.Presenter {
    private static final String TAG = DailyListPresenter.class.getName();

    DailyListContract.View view;
    DailyListInteractor interactor;

    public DailyListPresenter(DailyListContract.View view, DailyListInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void getTaskListForDay(long dateTime) {
        view.showTaskList(
                interactor.getFormattedTaskListForDay(dateTime)
                        .doOnNext(taskListItem -> Log.d(TAG, "getTaskListForDay: " + taskListItem.taskName))
                        .toList()
        );
    }
}
