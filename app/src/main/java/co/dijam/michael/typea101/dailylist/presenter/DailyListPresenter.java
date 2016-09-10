package co.dijam.michael.typea101.dailylist.presenter;

import co.dijam.michael.typea101.dailylist.DailyListContract;
import co.dijam.michael.typea101.dailylist.interactor.DailyListInteractor;

/**
 * Created by mdd23 on 9/10/2016.
 */
public class DailyListPresenter implements DailyListContract.Presenter {

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
                        //Need to call first (along with filter(RealmResults::isLoaded)
                        //to make hot observable call onComplete() and make the list
                        .first()
                        .toList()
        );
    }
}
