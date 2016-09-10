package co.dijam.michael.typea101.dailylist;

import java.util.List;

import co.dijam.michael.typea101.dailylist.model.TaskListItem;

/**
 * Created by mdd23 on 9/10/2016.
 */
public interface DailyListContract {
    interface View {
        void showTaskList(List<TaskListItem> taskListItems);
        void startDetailView(int id);
    }

    interface Presenter {
        void getTaskListForDay(long dateTime);
    }
}
