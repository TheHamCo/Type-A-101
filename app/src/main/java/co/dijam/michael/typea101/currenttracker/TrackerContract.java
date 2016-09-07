package co.dijam.michael.typea101.currenttracker;

/**
 * Created by mdd23 on 9/6/2016.
 */
public interface TrackerContract {
    interface View {
        void showTracker();
        void hideTracker();

        void showTaskName(String taskName);
        void showTaskTag(String taskTagName);
        void showTaskStartTime(String taskStartTime);

        void updateTimer(String timerText);

        void startEditTask();
        void startAddNotes();
    }

    interface Presenter{
        void getCurrentTask();
        void runTimer();
        void stopTimer();
        void editCurrentTask();
        void addNotesToCurrentTask();
        void finishTracking();
    }


}
