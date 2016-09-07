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

        presenter.presentCorrectMainView(viewingDateTime);
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
    public void showTracker() {
        if (trackerFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
            .show(trackerFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.tracker_frame, trackerFragment, FRAGMENT_TRACKER).commit();
        }
    }

    @Override
    public void hideTracker() {
        if (trackerFragment.isAdded() && trackerFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().hide(trackerFragment).commit();
        }
    }

    @Override
    public void updateList(long dateTime) {
        Bundle bundle = new Bundle();
        bundle.putLong(BUNDLE_DATETIME, dateTime);
        // TODO: Replace list with proper data
    }

    // FAB
    @OnClick(R.id.main_screen_fab)
    @Override
    public void onFabClick() {
        if (presenter.currentTaskExists()){
            trackerFragment.finishTrackingClicked();
        } else {
            startActivity(new Intent(this, AddCurrentTaskActivity.class));
        }
    }

    @Override
    public void styleFabAdd() {

    }

    @Override
    public void styleFabFinish() {

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

    @Override
    public void enableNextDayButton() {

    }

    // SNACKBAR
    @Override
    public void showSnackbar() {
        
    }

    @Override
    public void updateTimerSnackbar(String taskName, String tag, String formattedTime) {

    }

    @Override
    public void hideSnackbar() {

    }
}
