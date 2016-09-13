package co.dijam.michael.typea101.dailylist.interactor;

import co.dijam.michael.typea101.dailylist.model.TaskPrintable;
import co.dijam.michael.typea101.model.Task;
import rx.Observable;

/**
 * Created by mdd23 on 9/10/2016.
 */
public interface DailyListInteractor {
    Observable<TaskPrintable> getFormattedTaskListForDay(long dateTime);
    TaskPrintable formatTask(Task task);
}
