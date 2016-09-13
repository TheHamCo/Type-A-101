package co.dijam.michael.typea101.modifytask.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.dailylist.model.TaskPrintable;
import co.dijam.michael.typea101.modifytask.ModifyTaskContract;
import rx.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModifyTaskFragment extends Fragment implements ModifyTaskContract.View {


    public ModifyTaskFragment() {
        // Required empty public constructor
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modify_task, container, false);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VIEW METHODS

    // VIEWS
    @Override
    public void showTaskName(String taskName) {

    }

    @Override
    public void showTag(String tag) {

    }

    @Override
    public void showStartTime(String startTime) {

    }

    @Override
    public void showEndTime(String endTime) {

    }

    // ERRORS
    @Override
    public void showErrorOverlappingTask(Observable<List<TaskPrintable>> overlappingTasks) {

    }

    @Override
    public void showErrorTaskInFuture() {

    }

    @Override
    public void showErrorStartTimeAfterEndTime() {

    }

    @Override
    public void showErrorNoDuration() {

    }

    @Override
    public void showErrorTaskNameInvalid() {

    }

    @Override
    public void showErrorTagInvalid() {

    }


    // AUTOCOMPLETE
    @Override
    public void autocompleteTaskNames(List<String> taskNames) {

    }

    @Override
    public void autocompleteTags(List<String> tags) {

    }

    @Override
    public void suggestNearestTaskBefore(TaskPrintable taskBefore) {

    }

    @Override
    public void suggestNearestTaskAfter(TaskPrintable taskAfter) {

    }

    // FINAL
    @Override
    public void closeModifyTask() {

    }
}
