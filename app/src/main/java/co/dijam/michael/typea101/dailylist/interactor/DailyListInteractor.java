package co.dijam.michael.typea101.dailylist.interactor;

import java.util.List;

import co.dijam.michael.typea101.dailylist.model.TaskListItem;
import co.dijam.michael.typea101.model.Task;

/**
 * Created by mdd23 on 9/10/2016.
 */
public interface DailyListInteractor {
    List<TaskListItem> getFormattedTaskListForDay(long dateTime);
    TaskListItem formatTask(Task task);
}
