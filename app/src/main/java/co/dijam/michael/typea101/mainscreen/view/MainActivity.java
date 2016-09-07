package co.dijam.michael.typea101.mainscreen.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.addcurrenttask.view.AddCurrentTaskActivity;
import co.dijam.michael.typea101.currenttracker.view.TrackerFragment;
import co.dijam.michael.typea101.entities.CurrentTaskManager;
import co.dijam.michael.typea101.entities.SharedPrefCurrentTaskManager;
import co.dijam.michael.typea101.mainscreen.MainScreenContract;

public class MainActivity extends AppCompatActivity implements MainScreenContract.View {

    @BindView(R.id.main_screen_fab)
    FloatingActionButton mainScreenFab;
    @BindView(R.id.tracker_frame)
    FrameLayout trackerFrame;
    @BindView(R.id.list_frame)
    FrameLayout listFrame;

    private static final String FRAGMENT_TRACKER = "FRAGMENT_TRACKER";
    TrackerFragment trackerFragment;

    CurrentTaskManager currentTaskManager;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        currentTaskManager = new SharedPrefCurrentTaskManager(getApplicationContext());

        if (savedInstanceState == null){
            trackerFragment = new TrackerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.tracker_frame, trackerFragment, FRAGMENT_TRACKER).commit();
        } else {
            trackerFragment = (TrackerFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TRACKER);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VIEW METHODS + BUTTON BINDINGS

    @OnClick(R.id.main_screen_fab)
    @Override
    public void onFABclick() {
        if (currentTaskManager.currentTaskExists()){
            trackerFragment.finishTrackingClicked();
        } else {
            startActivity(new Intent(this, AddCurrentTaskActivity.class));
        }
    }

    // NAV DRAWER
    @Override
    public void startTagListFeature() {

    }

    @Override
    public void startLifeTimeStatsFeature() {

    }

    @Override
    public void startExportDataFeature() {

    }

    @Override
    public void openSettings() {

    }

    // TOOLBAR
    @Override
    public void showDate(String formattedDate) {

    }

    @Override
    public void onPreviousDay() {

    }

    @Override
    public void openDatePicker() {

    }

    @Override
    public void onToday() {

    }

    @Override
    public void onNextDay() {

    }

    // SNACKBAR
    @Override
    public void showTimerSnackbar(String taskName, String tag, String formattedTime) {

    }

    @Override
    public void hideSnackbar() {

    }

    // MAIN VIEW
    @Override
    public void swapMainView(long dateTime) {

    }
}
