package co.dijam.michael.typea101.currenttracker.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.currenttracker.TrackerContract;
import co.dijam.michael.typea101.currenttracker.TrackerInteractor.TrackerInteractorImpl;
import co.dijam.michael.typea101.currenttracker.presenter.TrackerPresenter;
import co.dijam.michael.typea101.entities.SharedPrefCurrentTaskManager;
import co.dijam.michael.typea101.entities.SqlTaskManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackerFragment extends Fragment implements TrackerContract.View {

    @BindView(R.id.tracker_layout)
    LinearLayout trackerLayout;
    @BindView(R.id.task_text_view)
    TextView taskTextView;
    @BindView(R.id.tag_text_view)
    TextView tagTextView;
    @BindView(R.id.started_on)
    TextView startedOn;
    @BindView(R.id.starting_time_text_view)
    TextView startingTimeTextView;
    @BindView(R.id.running_time_text_view)
    TextView runningTimeTextView;
    @BindView(R.id.add_notes_button)
    Button addNotesButton;
    @BindView(R.id.edit_button)
    Button editButton;

    private static final String STATE_TIMERTEXT = "STATE_TIMERTEXT";

    TrackerContract.Presenter presenter;
    private Unbinder unbinder;

    public TrackerFragment() {
        // Required empty public constructor
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter = new TrackerPresenter(this,
                new TrackerInteractorImpl(new SharedPrefCurrentTaskManager(getContext()), new SqlTaskManager()));
        presenter.getCurrentTask();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_TIMERTEXT, runningTimeTextView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            updateTimer(savedInstanceState.getString(STATE_TIMERTEXT));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // BUTTON BINDINGS

    @OnClick({R.id.add_notes_button, R.id.edit_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_notes_button:
                break;
            case R.id.edit_button:
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VIEW METHODS

    @Override
    public void showTracker() {
        trackerLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTracker() {
        trackerLayout.setVisibility(View.GONE);
    }

    @Override
    public void showTaskName(String taskName) {
        taskTextView.setText(taskName);
    }

    @Override
    public void showTag(String taskTagName) {
        tagTextView.setText(taskTagName);
    }

    @Override
    public void showTaskStartTime(String taskStartTime) {
        startingTimeTextView.setText(taskStartTime);
    }

    @Override
    public void updateTimer(String timerText) {
        runningTimeTextView.setText(timerText);
    }

    @Override
    public void finishTrackingClicked() {
        presenter.finishTracking();
    }

    @Override
    public void startEditTask() {

    }

    @Override
    public void startAddNotes() {

    }
}
