package co.dijam.michael.typea101.dailylist.interactor;

import org.joda.time.Period;

import co.dijam.michael.typea101.dailylist.model.TaskListItem;
import co.dijam.michael.typea101.entities.TaskManager;
import co.dijam.michael.typea101.model.Task;
import rx.Observable;

import static co.dijam.michael.typea101.util.TimeFormattingUtil.durationFormatter;
import static co.dijam.michael.typea101.util.TimeFormattingUtil.timeFormatter;

/**
 * Created by mdd23 on 9/10/2016.
 */
public class DailyListInteractorImpl implements DailyListInteractor {

    TaskManager taskManager;

    public DailyListInteractorImpl(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public Observable<TaskListItem> getFormattedTaskListForDay(long dateTime) {
//        return taskManager.getAllTasksForOneDay(dateTime)
        // For debugging only
        return taskManager.getAllTasks()
                .map(this::formatTask);
    }

    @Override
    public TaskListItem formatTask(Task task) {
        TaskListItem taskListItem = new TaskListItem();
        taskListItem.id = task.id;
        taskListItem.taskName = task.taskName;
        taskListItem.tag = task.tag;
        taskListItem.formattedStartTime = timeFormatter.print(task.startTime);
        taskListItem.formattedEndTime = timeFormatter.print(task.endTime);
        taskListItem.formattedDuration = durationFormatter.print(new Period(task.startTime, task.endTime));
        return taskListItem;
    }
}
