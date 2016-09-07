package co.dijam.michael.typea101.entities;

import android.content.Context;
import android.content.SharedPreferences;

import co.dijam.michael.typea101.model.CurrentTask;
import co.dijam.michael.typea101.util.CurrentTaskConstantsUtil;

import static co.dijam.michael.typea101.util.SharedPrefConstantsUtil.CURRENT_TASK;
import static co.dijam.michael.typea101.util.SharedPrefConstantsUtil.ID;
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
        currentTask.id = settings.getInt(ID, CurrentTaskConstantsUtil.NO_CURRENT_TASK_ID);
        currentTask.taskName = settings.getString(TASK_NAME, "");
        currentTask.tag = settings.getString(TAG, "");
        currentTask.startTime = settings.getLong(START_TIME, 0);
        return currentTask;
    }

    @Override
    public void setCurrentTask(CurrentTask currentTask) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(ID, currentTask.id);
        editor.putString(TASK_NAME, currentTask.taskName);
        editor.putString(TAG, currentTask.tag);
        editor.putLong(START_TIME, currentTask.startTime);
        editor.apply();
    }

    @Override
    public void clearCurrentTask() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(ID, CurrentTaskConstantsUtil.NO_CURRENT_TASK_ID);
        editor.putString(TASK_NAME, "");
        editor.putString(TAG, "");
        editor.putLong(START_TIME, 0);
        editor.apply();
    }
}
