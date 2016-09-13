package co.dijam.michael.typea101.converter;

import org.joda.time.DateTimeConstants;
import org.joda.time.Period;

import co.dijam.michael.typea101.dailylist.model.TaskPrintable;
import co.dijam.michael.typea101.model.Task;

import static co.dijam.michael.typea101.util.TimeFormattingUtil.durationFormatter;
import static co.dijam.michael.typea101.util.TimeFormattingUtil.timeFormatter;

/**
 * Created by mdd23 on 9/13/2016.
 */
public class TasktoTaskPrintableConverter {
    public static TaskPrintable formatTask(Task task) {
        TaskPrintable taskPrintable = new TaskPrintable();
        taskPrintable.id = task.id;
        taskPrintable.taskName = task.taskName;
        taskPrintable.tag = task.tag;
        taskPrintable.formattedStartTime = timeFormatter.print(task.startTime);
        taskPrintable.formattedEndTime = timeFormatter.print(task.endTime);

        long durationMillis = task.endTime - task.startTime;

        taskPrintable.formattedDuration = durationFormatter.print(new Period(durationMillis));

        float percentage = (durationMillis / ((float) DateTimeConstants.MILLIS_PER_DAY)) * 100;
        taskPrintable.setFormattedPercentage(percentage);

        return taskPrintable;
    }
}
