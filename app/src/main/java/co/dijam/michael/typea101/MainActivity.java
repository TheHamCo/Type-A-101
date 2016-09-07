package co.dijam.michael.typea101;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.dijam.michael.typea101.addcurrenttask.view.AddCurrentTaskActivity;
import co.dijam.michael.typea101.currenttracker.view.TrackerFragment;
import co.dijam.michael.typea101.entities.CurrentTaskManager;
import co.dijam.michael.typea101.entities.SharedPrefCurrentTaskManager;
import co.dijam.michael.typea101.util.TimeFormattingUtil;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.add_task_button)
    FloatingActionButton addTaskButton;
    @BindView(R.id.tracker_frame)
    FrameLayout trackerFrame;
    @BindView(R.id.list_frame)
    FrameLayout listFrame;

    private static final String FRAGMENT_TRACKER = "FRAGMENT_TRACKER";
    TrackerFragment trackerFragment;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null){
            trackerFragment = new TrackerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.tracker_frame, trackerFragment, FRAGMENT_TRACKER).commit();
        } else {
            trackerFragment = (TrackerFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TRACKER);
        }

        CurrentTaskManager currentTaskManager = new SharedPrefCurrentTaskManager(getApplicationContext());
        String formattedTime = TimeFormattingUtil.dateTimeFormatter.print(currentTaskManager.getCurrentTask().startTime);
        Toast.makeText(MainActivity.this, currentTaskManager.getCurrentTask().toString() + formattedTime, Toast.LENGTH_SHORT).show();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // BUTTON BINDINGS

    @OnClick(R.id.add_task_button)
    void onAddTaskButton(FloatingActionButton b) {
        startActivity(new Intent(this, AddCurrentTaskActivity.class));
    }
}
