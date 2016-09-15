package co.dijam.michael.typea101.modifytask.view;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.dailylist.model.TaskPrintable;
import co.dijam.michael.typea101.entities.RealmTaskManager;
import co.dijam.michael.typea101.entities.SharedPrefCurrentTaskManager;
import co.dijam.michael.typea101.modifytask.ModifyTaskContract;
import co.dijam.michael.typea101.modifytask.interactor.ModifyTaskInteractorImpl;
import co.dijam.michael.typea101.modifytask.presenter.ModifyTaskPresenter;
import co.dijam.michael.typea101.taskdetail.view.TaskDetailActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModifyTaskActivity extends AppCompatActivity implements ModifyTaskContract.View {

    // TASK NAME + TAG
    @BindView(R.id.task_name_edit)
    AutoCompleteTextView taskNameEdit;
    @BindView(R.id.task_name_edit_layout)
    TextInputLayout taskNameEditLayout;
    @BindView(R.id.tag_edit)
    AutoCompleteTextView tagEdit;
    @BindView(R.id.tag_edit_layout)
    TextInputLayout tagEditLayout;

    // DATE
    @BindView(R.id.date_text_view)
    TextView dateTextView;
    @BindView(R.id.day_edit_clickable)
    FrameLayout dayEditClickable;

    // START TIME
    @BindView(R.id.starttime_text_view)
    TextView starttimeTextView;
    @BindView(R.id.start_time_picker_clickable)
    FrameLayout startTimePickerClickable;
    @BindView(R.id.start_time_count_label)
    TextView startTimeCountLabel;
    @BindView(R.id.start_time_existing_task_icon)
    ImageView startTimeExistingTaskIcon;
    @BindView(R.id.start_time_existing_task_clickable)
    LinearLayout startTimeExistingTaskClickable;
    @BindView(R.id.start_time_task_recycler)
    RecyclerView startTimeTaskRecycler;
    @BindView(R.id.expandable_start_time_list)
    LinearLayout expandableStartTimeList;

    // END TIME
    @BindView(R.id.endtime_text_view)
    TextView endtimeTextView;
    @BindView(R.id.end_time_picker_clickable)
    FrameLayout endTimePickerClickable;
    @BindView(R.id.end_time_count_label)
    TextView endTimeCountLabel;
    @BindView(R.id.end_time_existing_task_icon)
    ImageView endTimeExistingTaskIcon;
    @BindView(R.id.end_time_existing_task_clickable)
    LinearLayout endTimeExistingTaskClickable;
    @BindView(R.id.end_time_task_recycler)
    RecyclerView endTimeTaskRecycler;
    @BindView(R.id.expandable_end_time_list)
    LinearLayout expandableEndTimeList;

    // DURATION
    @BindView(R.id.duration_text_view)
    TextView durationTextView;

    // FINISH BUTTONS
    @BindView(R.id.confirm_button)
    ImageButton confirmButton;
    @BindView(R.id.cancel_button)
    ImageButton cancelButton;

    ModifyTaskContract.Presenter presenter;

    private static final int NO_TASK_ID = -1;
    private int mTaskId = NO_TASK_ID;
    private static final long NO_START_TIME = -1;
    private long mStartTime = NO_START_TIME;
    private long mEndTime = 0;

    private static final String STATE_TASKID = "STATE_TASKID";
    private static final String STATE_STARTTIME = "STATE_STARTTIME";
    private static final String STATE_ENDTIME = "END_STARTTIME";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mTaskId = extras.getInt(TaskDetailActivity.BUNDLE_TASKID);
        }

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm realm = Realm.getInstance(realmConfig);
        presenter =
                new ModifyTaskPresenter(
                        new ModifyTaskInteractorImpl(
                            new SharedPrefCurrentTaskManager(this), new RealmTaskManager(realmConfig, realm)
                        ),
                        this
                );

        if (mTaskId != NO_TASK_ID && savedInstanceState == null){
            presenter.getTaskDetails(mTaskId);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTaskId = savedInstanceState.getInt(STATE_TASKID);
        setStartTime(savedInstanceState.getLong(STATE_STARTTIME));
        setEndTime(savedInstanceState.getLong(STATE_ENDTIME));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.restoreViews(mStartTime, mEndTime);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_TASKID, mTaskId);
        outState.putLong(STATE_STARTTIME, mStartTime);
        outState.putLong(STATE_ENDTIME, mEndTime);
        super.onSaveInstanceState(outState);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // BUTTON BINDINGS

    @OnClick(R.id.day_edit_clickable)
    public void onDayEditClick() {
        showDayPicker();
    }

    @OnClick({R.id.start_time_picker_clickable, R.id.start_time_existing_task_clickable})
    public void onStartTimeClick(View view) {
        switch (view.getId()) {
            case R.id.start_time_picker_clickable:
                showTimePicker();
                break;
            case R.id.start_time_existing_task_clickable:
                break;
        }
    }

    @OnClick({R.id.end_time_picker_clickable, R.id.end_time_existing_task_clickable})
    public void onEndTimeClick(View view) {
        switch (view.getId()) {
            case R.id.end_time_picker_clickable:
                showTimePicker();
                break;
            case R.id.end_time_existing_task_clickable:
                break;
        }
    }

    @OnClick({R.id.confirm_button, R.id.cancel_button})
    public void onConfirmCancelClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_button:
                break;
            case R.id.cancel_button:
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VIEW METHODS

    // DATA
    @Override
    public void setStartTime(long startTime) {
        mStartTime = startTime;
    }

    @Override
    public void setEndTime(long endTime) {
        mEndTime = endTime;
    }

    // PICKERS
    @Override
    public void showDayPicker() {
        DateTime shown;
        if (mStartTime == NO_START_TIME) {
            shown = DateTime.now();
        } else {
            shown = new DateTime(mStartTime);
        }
        DatePickerDialog dpd = new DatePickerDialog(this, this::onDateSet, shown.getYear(), shown.getMonthOfYear(), shown.getDayOfMonth());
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
        dpd.show();
    }

    public void showTimePicker() {
        DateTime shown = new DateTime(mStartTime);
        TimePickerDialog tpd = new TimePickerDialog(this, this::onTimeSet, shown.getHourOfDay(), shown.getMinuteOfHour(), DateFormat.is24HourFormat(this));
        tpd.show();
    }

    // VIEWS
    @Override
    public void showTaskName(String taskName) {
        taskNameEdit.setText(taskName);
    }

    @Override
    public void showTag(String tag) {
        tagEdit.setText(tag);
    }

    @Override
    public void showStartTime(String startTime) {
        starttimeTextView.setText(startTime);
    }

    @Override
    public void showEndTime(String endTime) {
        endtimeTextView.setText(endTime);
    }

    @Override
    public void showDay(String formattedDay) {
        dateTextView.setText(formattedDay);
    }

    @Override
    public void showDuration(String formattedDuration) {
        durationTextView.setText(formattedDuration);
    }

    @Override
    public void showPercentage(String formattedPercentage) {

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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // CALLBACKS

    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }

    private void onTimeSet(TimePicker tp, int hour, int minute){

    }
}
