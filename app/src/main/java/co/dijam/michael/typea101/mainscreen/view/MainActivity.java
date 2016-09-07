package co.dijam.michael.typea101.mainscreen.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.addcurrenttask.view.AddCurrentTaskActivity;
import co.dijam.michael.typea101.currenttracker.view.TrackerFragment;
import co.dijam.michael.typea101.entities.CurrentTaskManager;
import co.dijam.michael.typea101.entities.SharedPrefCurrentTaskManager;
import co.dijam.michael.typea101.mainscreen.MainScreenContract;
import co.dijam.michael.typea101.mainscreen.presenter.MainScreenPresenter;
import co.dijam.michael.typea101.util.TimeFormattingUtil;

public class MainActivity extends AppCompatActivity implements MainScreenContract.View {

    @BindView(R.id.main_screen_fab)
    FloatingActionButton mainScreenFab;
    @BindView(R.id.tracker_frame)
    FrameLayout trackerFrame;
    @BindView(R.id.list_frame)
    FrameLayout listFrame;

    private static final String FRAGMENT_TRACKER = "FRAGMENT_TRACKER";
    TrackerFragment trackerFragment;

    private static final String BUNDLE_DATETIME = "BUNDLE_DATETIME";

    private static final String STATE_VIEWING_DATETIME = "STATE_VIEWING_DATETIME";

    MainScreenContract.Presenter presenter;

    private long viewingDateTime = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        CurrentTaskManager currentTaskManager = new SharedPrefCurrentTaskManager(getApplicationContext());
        presenter = new MainScreenPresenter(currentTaskManager, this);

        if (savedInstanceState == null){
            trackerFragment = new TrackerFragment();
            viewingDateTime = System.currentTimeMillis();
        } else {
            trackerFragment = (TrackerFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TRACKER);
            viewingDateTime = savedInstanceState.getLong(STATE_VIEWING_DATETIME);
        }

        swapMainView(viewingDateTime);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(STATE_VIEWING_DATETIME, viewingDateTime);
        super.onSaveInstanceState(outState);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VIEW METHODS + BUTTON BINDINGS

    // MAIN VIEW
    @Override
    public void swapMainView(long dateTime) {
        if (presenter.isToday(dateTime)){
            this.hideSnackbar();
            this.disableNextDayButton();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.tracker_frame, trackerFragment, FRAGMENT_TRACKER).commit();
            trackerFragment.showTracker();
        } else {
            trackerFragment.hideTracker();
            Bundle bundle = new Bundle();
            bundle.putLong(BUNDLE_DATETIME, dateTime);
            // TODO: Replace list with data
        }
        this.showDate(TimeFormattingUtil.dateFormatter.print(dateTime));
    }

    @OnClick(R.id.main_screen_fab)
    @Override
    public void onFABclick() {
        if (presenter.currentTaskExists()){
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
        // For debugging only
        Toast.makeText(MainActivity.this, formattedDate, Toast.LENGTH_SHORT).show();
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

    @Override
    public void disableNextDayButton() {

    }

    // SNACKBAR
    @Override
    public void showTimerSnackbar(String taskName, String tag, String formattedTime) {

    }

    @Override
    public void hideSnackbar() {

    }
}
