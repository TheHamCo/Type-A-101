package co.dijam.michael.typea101.addcurrenttask;

/**
 * Created by mdd23 on 9/6/2016.
 */
public interface AddCurrentTaskContract {
    interface View {
        void showStartTime(String formattedStartTime);
        void closeAddTaskView();
        boolean taskNameIsValid(String taskName);
        boolean tagIsValid(String tag);
    }

    interface Presenter {
        void setCurrentTask(String taskName, String tagName, long startTime);
    }
}
