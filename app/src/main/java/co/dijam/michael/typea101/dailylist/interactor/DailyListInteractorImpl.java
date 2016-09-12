package co.dijam.michael.typea101.dailylist.interactor;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.List;

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
    private static final String TAG = DailyListInteractorImpl.class.getName();

    TaskManager taskManager;

    public DailyListInteractorImpl(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public Observable<TaskListItem> getFormattedTaskListForDay(long dateTime) {
        DateTime dayMidnight = new DateTime(dateTime).withTimeAtStartOfDay();
        DateTime nextDayMidnight = dayMidnight.plusDays(1);

        List<Task> splitTasks = new ArrayList<>();

        taskManager.getAllTasksForOneDay(dateTime)
                .toList()
                .subscribe(tasks -> {
                    for (Task t : tasks) {
                        boolean timeRollsOverFromYesterday =
                                t.startTime < dayMidnight.getMillis() && t.endTime > dayMidnight.getMillis();
                        boolean timeSpillsIntoTomorrow =
                                t.startTime < nextDayMidnight.getMillis() && t.endTime > nextDayMidnight.getMillis();

                        if (timeRollsOverFromYesterday) {
                            Task earlierTask = copyTask(t);

                            earlierTask.startTime = dayMidnight.getMillis();
                            splitTasks.add(earlierTask);

                        } else if (timeSpillsIntoTomorrow) {
                            Task laterTask = copyTask(t);

                            laterTask.endTime = nextDayMidnight.minusSeconds(1).getMillis();
                            splitTasks.add(laterTask);

                        } else {
                            splitTasks.add(t);
                        }
                    }

                });

        return Observable.from(splitTasks)
                .sorted((task, task2) -> Long.valueOf(task.startTime).compareTo(task2.startTime))
                .map(this::formatTask);
    }

    private Task copyTask(Task task) {
        Task newTask = new Task();
        newTask.id = task.id;
        newTask.taskName = task.taskName;
        newTask.tag = task.tag;
        newTask.startTime = task.startTime;
        newTask.endTime = task.endTime;
        return newTask;
    }

    @Override
    public TaskListItem formatTask(Task task) {
        TaskListItem taskListItem = new TaskListItem();
        taskListItem.id = task.id;
        taskListItem.taskName = task.taskName;
        taskListItem.tag = task.tag;
        taskListItem.formattedStartTime = timeFormatter.print(task.startTime);
        taskListItem.formattedEndTime = timeFormatter.print(task.endTime);

        long durationMillis = task.endTime - task.startTime;

        taskListItem.formattedDuration = durationFormatter.print(new Period(durationMillis));

        float percentage = (durationMillis / ((float) DateTimeConstants.MILLIS_PER_DAY)) * 100;
        Log.d(TAG, "formatTask: " + percentage);
        taskListItem.setFormattedPercentage(percentage);

        return taskListItem;
    }
}
