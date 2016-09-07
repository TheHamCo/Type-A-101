package co.dijam.michael.typea101.currenttracker.view;


import android.os.Bundle;
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
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.currenttracker.TrackerContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackerFragment extends Fragment implements TrackerContract.View {

    @BindView(R.id.tracker_layout)
    LinearLayout trackerLayout;
    @BindView(R.id.task_text_view)
    TextView taskTextView;
    @BindView(R.id.category_text_view)
    TextView categoryTextView;
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
    @BindView(R.id.done_button)
    Button doneButton;

    TrackerContract.Presenter presenter;


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
        ButterKnife.bind(this, view);

        return view;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // BUTTON BINDINGS

    @OnClick({R.id.add_notes_button, R.id.edit_button, R.id.done_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_notes_button:
                break;
            case R.id.edit_button:
                break;
            case R.id.done_button:
                break;
        }
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
