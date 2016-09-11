package co.dijam.michael.typea101.modifycurrenttask;

import java.util.List;

/**
 * Created by mdd23 on 9/6/2016.
 */
public interface ModifyCurrentTaskContract {
    interface View {
        void showStartTime(String formattedStartTime);
        void closeAddTaskView();
        boolean taskNameIsValid(String taskName);
        boolean tagIsValid(String tag);
        void autoCompleteTaskNames(List<String> taskNames);
        void autoCompleteTags(List<String> tags);
    }

    interface Presenter {
        void setCurrentTask(String taskName, String tagName, long startTime);
        void autoCompleteTaskNames();
        void autoCompleteTags();
    }
}
