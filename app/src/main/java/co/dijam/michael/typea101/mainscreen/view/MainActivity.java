package co.dijam.michael.typea101.mainscreen.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTimeConstants;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.currenttracker.view.TrackerFragment;
import co.dijam.michael.typea101.dailylist.view.DailyListFragment;
import co.dijam.michael.typea101.entities.CurrentTaskManager;
import co.dijam.michael.typea101.entities.SharedPrefCurrentTaskManager;
import co.dijam.michael.typea101.mainscreen.MainScreenContract;
import co.dijam.michael.typea101.mainscreen.presenter.MainScreenPresenter;
import co.dijam.michael.typea101.modifycurrenttask.view.ModifyCurrentTaskActivity;
import co.dijam.michael.typea101.util.ConstantsUtil;
import co.dijam.michael.typea101.util.TimeFormattingUtil;

public class MainActivity extends AppCompatActivity implements MainScreenContract.View {
    private static final String TAG = MainActivity.class.getName();

    // Toolbar Bindings
    @BindView(R.id.nav_drawer_button)
    ImageButton navDrawerButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.previous_day_button)
    ImageButton previousDayButton;
    @BindView(R.id.date_picker_button)
    ImageButton datePickerButton;
    @BindView(R.id.date_text_view)
    TextView dateTextView;
    @BindView(R.id.next_day_button)
    ImageButton nextDayButton;

    // Main Screen Bindings
    @BindView(R.id.main_root_layout)
    CoordinatorLayout mainRootLayout;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.tracker_frame)
    FrameLayout trackerFrame;
    @BindView(R.id.list_frame)
    FrameLayout listFrame;

    // Floating Action Buttons
    @BindView(R.id.main_screen_fab)
    FloatingActionButton mainScreenFab;
    @BindView(R.id.add_past_fab)
    FloatingActionButton addPastFab;
    @BindView(R.id.past_label)
    TextView pastLabel;
    @BindView(R.id.add_present_fab)
    FloatingActionButton addPresentFab;
    @BindView(R.id.present_label)
    TextView presentLabel;
    // FAB dummies
    @BindView(R.id.fab_positioning_dummy_past)
    View fabPositioningDummyPast;
    @BindView(R.id.label_positioning_dummy_past)
    View labelPositioningDummyPast;
    @BindView(R.id.fab_positioning_dummy_present)
    View fabPositioningDummyPresent;
    @BindView(R.id.label_positioning_dummy_present)
    View labelPositioningDummyPresent;

    // FAB Icons
    @BindDrawable(R.drawable.ic_add_task)
    Drawable addCurrentTask;
    @BindDrawable(R.drawable.ic_check)
    Drawable finishCurrentTask;

    // Colors
    @BindColor(R.color.addFab)
    int addFabColor;
    @BindColor(R.color.finishTaskFab)
    int finishTaskFabColor;

    // Fragments
    private static final String FRAGMENT_TRACKER = "FRAGMENT_TRACKER";
    TrackerFragment trackerFragment;
    public static final String BUNDLE_DATETIME = "BUNDLE_DATETIME";
    private static final String FRAGMENT_DAILYLIST = "FRAGMENT_DAILYLIST";
    DailyListFragment dailyListFragment;

    // State
    private static final String STATE_VIEWING_DATETIME = "STATE_VIEWING_DATETIME";
    private static final String STATE_SNACKBAR_TEXT = "STATE_SNACKBAR_TEXT";
    private static final String STATE_ADD_FABS_SHOWN = "STATE_ADD_FABS_SHOWN";

    // Fields
    private long viewingDateTime = 0;
    Snackbar snackbar;
    boolean addFabsShown = false;

    // Dependencies
    MainScreenContract.Presenter presenter;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        CurrentTaskManager currentTaskManager = new SharedPrefCurrentTaskManager(getApplicationContext());
        presenter = new MainScreenPresenter(currentTaskManager, this);

        initSnackbar();

        if (savedInstanceState == null) {
            trackerFragment = new TrackerFragment();
            dailyListFragment = new DailyListFragment();
            viewingDateTime = System.currentTimeMillis();
        } else {
            trackerFragment = (TrackerFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TRACKER);
            dailyListFragment = (DailyListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_DAILYLIST);
            viewingDateTime = savedInstanceState.getLong(STATE_VIEWING_DATETIME);
            getSnackbarTextView().setText(savedInstanceState.getString(STATE_SNACKBAR_TEXT));
        }

        presenter.presentCorrectMainView(viewingDateTime);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter.currentTaskExists() && presenter.isToday(viewingDateTime)) {
            int top = 0;
            nestedScrollView.scrollTo(0, top);
        }
        if (snackbar.isShown()) {
            presenter.runSnackbarTimer();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(STATE_VIEWING_DATETIME, viewingDateTime);
        outState.putString(STATE_SNACKBAR_TEXT, getSnackbarTextView().getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        presenter.stopSnackBarTimer();
        super.onPause();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TOOLBAR BINDINGS

    @OnClick({R.id.previous_day_button, R.id.date_picker_button, R.id.date_text_view, R.id.next_day_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.previous_day_button:
                onPreviousDay();
                break;
            case R.id.date_picker_button:
                //TODO: Add date picker dialog
                break;
            case R.id.date_text_view:
                onToday();
                break;
            case R.id.next_day_button:
                onNextDay();
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LAYOUT UTILS

    private boolean isPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private void initSnackbar() {
        snackbar = Snackbar.make(mainRootLayout, "", Snackbar.LENGTH_INDEFINITE);
        if (isPortrait()) {
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

    private void adjustMainViewBottomMargin(int bottomMargin) {
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
        dailyListFragment = new DailyListFragment();
        dailyListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.list_frame, dailyListFragment, FRAGMENT_DAILYLIST).commit();
    }

    // FAB
    @OnClick(R.id.main_screen_fab)
    @Override
    public void onFabClick() {
        if (presenter.currentTaskExists()) {
            trackerFragment.finishTrackingClicked();
            hideSnackbar();
            styleFabAdd();
        } else if (addFabsShown) {
            hideAddFabs();
        } else {
            showAddFabs();
        }
    }

    @OnClick({R.id.add_past_fab, R.id.add_present_fab})
    public void onAddFabsClick(View view) {
        switch (view.getId()) {
            case R.id.add_past_fab:
                break;
            case R.id.add_present_fab:
                startActivity(new Intent(this, ModifyCurrentTaskActivity.class));
                styleFabFinish();
                hideAddFabs();
                break;
        }
    }

    @Override
    public void styleFabAdd() {
        mainScreenFab.setImageDrawable(addCurrentTask);
        ColorStateList addTaskTint = ColorStateList.valueOf(addFabColor);
        mainScreenFab.setBackgroundTintList(addTaskTint);
    }

    @Override
    public void styleFabFinish() {
        mainScreenFab.setImageDrawable(finishCurrentTask);
        ColorStateList finishTaskTint = ColorStateList.valueOf(finishTaskFabColor);
        mainScreenFab.setBackgroundTintList(finishTaskTint);
    }

    @Override
    public void showAddFabs() {
//        fabPositioningDummyPresent.setVisibility(View.VISIBLE);
//        labelPositioningDummyPresent.setVisibility(View.VISIBLE);
//        fabPositioningDummyPast.setVisibility(View.VISIBLE);
//        labelPositioningDummyPast.setVisibility(View.VISIBLE);

        addPresentFab.show();
        addPastFab.show();
        pastLabel.setVisibility(View.VISIBLE);
        presentLabel.setVisibility(View.VISIBLE);


        addFabsShown = true;
    }

    @Override
    public void hideAddFabs() {
//        fabPositioningDummyPresent.setVisibility(View.GONE);
//        labelPositioningDummyPresent.setVisibility(View.GONE);
//        fabPositioningDummyPast.setVisibility(View.GONE);
//        labelPositioningDummyPast.setVisibility(View.GONE);

        addPresentFab.hide();
        addPastFab.hide();
        pastLabel.setVisibility(View.INVISIBLE);
        presentLabel.setVisibility(View.INVISIBLE);

        addFabsShown = false;
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
        dateTextView.setText(formattedDate);
    }

    @Override
    public void onPreviousDay() {
        viewingDateTime -= DateTimeConstants.MILLIS_PER_DAY;
        presenter.presentCorrectMainView(viewingDateTime);
    }

    @Override
    public void openDatePicker() {

    }

    @OnClick(R.id.date_text_view)
    @Override
    public void onToday() {
        viewingDateTime = System.currentTimeMillis();
        presenter.presentCorrectMainView(viewingDateTime);
        String todayToastMessage = getString(R.string.returned_to_today) + TimeFormattingUtil.dateFormatter.print(viewingDateTime);
        Toast.makeText(MainActivity.this, todayToastMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNextDay() {
        viewingDateTime += DateTimeConstants.MILLIS_PER_DAY;
        presenter.presentCorrectMainView(viewingDateTime);
    }

    @Override
    public void disableNextDayButton() {
        nextDayButton.setAlpha(0.25f);
        nextDayButton.setEnabled(false);
    }

    @Override
    public void enableNextDayButton() {
        nextDayButton.setAlpha(1f);
        nextDayButton.setEnabled(true);
    }

    // SNACKBAR
    @Override
    public void showSnackbar() {
        snackbar.show();
        presenter.runSnackbarTimer();
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
        adjustMainViewBottomMargin(snackbarHeight);
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
        adjustMainViewBottomMargin(0);
    }
}
