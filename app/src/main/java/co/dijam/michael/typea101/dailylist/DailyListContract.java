package co.dijam.michael.typea101.dailylist;

import co.dijam.michael.typea101.dailylist.model.TaskListItem;
import rx.Observable;

/**
 * Created by mdd23 on 9/10/2016.
 */
public interface DailyListContract {
    interface View {
        void showTaskList(Observable<TaskListItem> taskListItems);
        void startDetailView(int id);
    }

    interface Presenter {
        void getTaskListForDay(long dateTime);
    }
}
