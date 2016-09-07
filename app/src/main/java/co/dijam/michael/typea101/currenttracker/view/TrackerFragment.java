package co.dijam.michael.typea101.currenttracker.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.currenttracker.TrackerContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackerFragment extends Fragment implements TrackerContract.View {


    public TrackerFragment() {
        // Required empty public constructor
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tracker, container, false);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VIEW METHODS

    @Override
    public void showTracker() {

    }

    @Override
    public void hideTracker() {

    }

    @Override
    public void showTaskName(String taskName) {

    }

    @Override
    public void showTaskTag(String taskTagName) {

    }

    @Override
    public void showTaskStartTime(String taskStartTime) {

    }

    @Override
    public void updateTimer(String timerText) {

    }

    @Override
    public void startEditTask() {

    }

    @Override
    public void startAddNotes() {

    }
}
