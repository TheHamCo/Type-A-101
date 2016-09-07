package co.dijam.michael.typea101.entities;

import android.content.Context;
import android.content.SharedPreferences;

import co.dijam.michael.typea101.model.CurrentTask;

import static co.dijam.michael.typea101.util.CurrentTaskConstantsUtil.NO_CURRENT_TASK;
import static co.dijam.michael.typea101.util.CurrentTaskConstantsUtil.NO_START_TIME;
import static co.dijam.michael.typea101.util.SharedPrefConstantsUtil.CURRENT_TASK;
import static co.dijam.michael.typea101.util.SharedPrefConstantsUtil.START_TIME;
import static co.dijam.michael.typea101.util.SharedPrefConstantsUtil.TAG;
import static co.dijam.michael.typea101.util.SharedPrefConstantsUtil.TASK_NAME;

/**
 * Created by mdd23 on 9/6/2016.
 */
public class SharedPrefCurrentTaskManager implements CurrentTaskManager {

    SharedPreferences settings;

    public SharedPrefCurrentTaskManager(Context context) {
        settings = context.getSharedPreferences(CURRENT_TASK, Context.MODE_PRIVATE);
    }

    @Override
    public CurrentTask getCurrentTask() {
        CurrentTask currentTask = new CurrentTask();
        currentTask.taskName = settings.getString(TASK_NAME, NO_CURRENT_TASK);
        currentTask.tag = settings.getString(TAG, NO_CURRENT_TASK);
        currentTask.startTime = settings.getLong(START_TIME, NO_START_TIME);
        return currentTask;
    }

    @Override
    public void setCurrentTask(CurrentTask currentTask) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(TASK_NAME, currentTask.taskName);
        editor.putString(TAG, currentTask.tag);
        editor.putLong(START_TIME, currentTask.startTime);
        editor.apply();
    }

    @Override
    public void clearCurrentTask() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(TASK_NAME, NO_CURRENT_TASK);
        editor.putString(TAG, NO_CURRENT_TASK);
        editor.putLong(START_TIME, NO_START_TIME);
        editor.apply();
    }

    @Override
    public boolean currentTaskExists() {
        return !(settings.getString(TASK_NAME, NO_CURRENT_TASK).equals(NO_CURRENT_TASK)
                && settings.getString(TAG, NO_CURRENT_TASK).equals(NO_CURRENT_TASK)
                && settings.getLong(START_TIME, NO_START_TIME) == NO_START_TIME);
    }
}
