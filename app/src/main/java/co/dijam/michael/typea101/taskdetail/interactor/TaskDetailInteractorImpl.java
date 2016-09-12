package co.dijam.michael.typea101.taskdetail.interactor;

import org.joda.time.DateTimeConstants;
import org.joda.time.Period;

import co.dijam.michael.typea101.entities.TaskManager;
import co.dijam.michael.typea101.model.Task;
import co.dijam.michael.typea101.taskdetail.model.NoteListItem;
import co.dijam.michael.typea101.taskdetail.model.TaskDetail;
import rx.Observable;

import static co.dijam.michael.typea101.util.TimeFormattingUtil.durationFormatter;
import static co.dijam.michael.typea101.util.TimeFormattingUtil.timeFormatter;

/**
 * Created by mdd23 on 9/11/2016.
 */
public class TaskDetailInteractorImpl implements TaskDetailInteractor {

    TaskManager taskManager;

    public TaskDetailInteractorImpl(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public Observable<TaskDetail> getTaskDetail(int id) {
        return taskManager.getTask(id)
                .map(this::formatTask);
    }

    @Override
    public Observable<NoteListItem> getNotes(int taskId) {
        return null;
    }

    @Override
    public TaskDetail formatTask(Task task) {
        //TODO: This is repeated from the DailyListInteractor.  If final functionality does not change, share method
        TaskDetail taskDetail = new TaskDetail();
        taskDetail.id = task.id;
        taskDetail.taskName = task.taskName;
        taskDetail.tag = task.tag;
        taskDetail.formattedStartTime = timeFormatter.print(task.startTime);
        taskDetail.formattedEndTime = timeFormatter.print(task.endTime);

        long durationMillis = task.endTime - task.startTime;

        taskDetail.formattedDuration = durationFormatter.print(new Period(durationMillis));

        float percentage = (durationMillis / ((float) DateTimeConstants.MILLIS_PER_DAY)) * 100;
        taskDetail.setFormattedPercentage(percentage);

        return taskDetail;
    }

    @Override
    public void deleteTask(int taskId) {
        taskManager.deleteTask(taskId);
    }
}
