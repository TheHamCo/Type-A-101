package co.dijam.michael.typea101.mainscreen.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTimeConstants;

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
import co.dijam.michael.typea101.util.ConstantsUtil;

public class MainActivity extends AppCompatActivity implements MainScreenContract.View {

    @BindView(R.id.main_root_layout)
    CoordinatorLayout mainRootLayout;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
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
    private static final String STATE_SNACKBAR_TEXT = "STATE_SNACKBAR_TEXT";

    MainScreenContract.Presenter presenter;


    private long viewingDateTime = 0;

    Snackbar snackbar;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        CurrentTaskManager currentTaskManager = new SharedPrefCurrentTaskManager(getApplicationContext());
        presenter = new MainScreenPresenter(currentTaskManager, this);

        initSnackbar();

        if (savedInstanceState == null) {
            trackerFragment = new TrackerFragment();
            viewingDateTime = System.currentTimeMillis();
        } else {
            trackerFragment = (TrackerFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TRACKER);
            viewingDateTime = savedInstanceState.getLong(STATE_VIEWING_DATETIME);
            getSnackbarTextView().setText(savedInstanceState.getString(STATE_SNACKBAR_TEXT));
        }

        presenter.presentCorrectMainView(viewingDateTime);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(STATE_VIEWING_DATETIME, viewingDateTime);
        outState.putString(STATE_SNACKBAR_TEXT, getSnackbarTextView().getText().toString());
        super.onSaveInstanceState(outState);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LAYOUT UTILS

    private boolean isPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private void initSnackbar() {
        snackbar = Snackbar.make(mainRootLayout, "", Snackbar.LENGTH_INDEFINITE);
        if (isPortrait()) {
            // If this is changed, change R.dimen.two_line_snackbar_height
            getSnackbarTextView().setMaxLines(2);
        }
        disableSnackbarSwipeToDismiss();
    }

    private void disableSnackbarSwipeToDismiss() {
        snackbar.getView().getViewTreeObserver().addOnPreDrawListener(() -> {
            ((CoordinatorLayout.LayoutParams) snackbar.getView().getLayoutParams()).setBehavior(null);
            return true;
        });
    }

    private TextView getSnackbarTextView() {
        return (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
    }

    private void adjustMainViewBottomMarginToAccountForSnackbar(int bottomMargin) {
        CoordinatorLayout.LayoutParams lp = ((CoordinatorLayout.LayoutParams) nestedScrollView.getLayoutParams());
        lp.setMargins(0, 0, 0, bottomMargin);
        nestedScrollView.setLayoutParams(lp);
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
            getSupportFragmentManager().beginTransaction()
                    .hide(trackerFragment).commit();
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
        if (presenter.currentTaskExists()) {
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

    @OnClick(R.id.previous_day_button)
    @Override
    public void onPreviousDay() {
        viewingDateTime -= DateTimeConstants.MILLIS_PER_DAY;
        presenter.presentCorrectMainView(viewingDateTime);
    }

    @Override
    public void openDatePicker() {

    }

    @OnClick(R.id.today_button)
    @Override
    public void onToday() {
        viewingDateTime = System.currentTimeMillis();
        presenter.presentCorrectMainView(viewingDateTime);
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
        snackbar.show();
        presenter.runSnackbarTimer();

//        int twoLineSnackbarHeight = (int) getResources().getDimension(R.dimen.two_line_snackbar_height);
//        adjustMainViewBottomMarginToAccountForSnackbar(twoLineSnackbarHeight);
    }

    @Override
    public void updateTimerSnackbar(String taskName, String tag, String formattedTime) {
        String labelDurationSeparator = getConfigurationDependentSeparator();
        String snackbarMessage = taskName.toUpperCase() +
                ConstantsUtil.SPACE +
                "(" + tag + ")" +
                labelDurationSeparator + formattedTime;
        snackbar.setText(snackbarMessage);
        int snackbarHeight = snackbar.getView().getHeight();
        adjustMainViewBottomMarginToAccountForSnackbar(snackbarHeight);
    }

    private String getConfigurationDependentSeparator() {
        if (isPortrait()) {
            return ConstantsUtil.LINE_SEPARATOR;
        } else {
            return ConstantsUtil.SPACE + "-" + ConstantsUtil.SPACE;
        }
    }

    @Override
    public void hideSnackbar() {
        snackbar.dismiss();
        presenter.stopSnackBarTimer();
        adjustMainViewBottomMarginToAccountForSnackbar(0);
    }
}
