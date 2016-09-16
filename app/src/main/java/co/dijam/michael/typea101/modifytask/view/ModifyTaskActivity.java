package co.dijam.michael.typea101.modifytask.view;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.dailylist.model.TaskPrintable;
import co.dijam.michael.typea101.dailylist.view.TaskPrintableAdapter;
import co.dijam.michael.typea101.entities.RealmTaskManager;
import co.dijam.michael.typea101.entities.SharedPrefCurrentTaskManager;
import co.dijam.michael.typea101.modifytask.ModifyTaskContract;
import co.dijam.michael.typea101.modifytask.interactor.ModifyTaskInteractorImpl;
import co.dijam.michael.typea101.modifytask.presenter.ModifyTaskPresenter;
import co.dijam.michael.typea101.taskdetail.view.TaskDetailActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;

import static co.dijam.michael.typea101.modifytask.ModifyTaskConstants.NO_TASK_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModifyTaskActivity extends AppCompatActivity implements ModifyTaskContract.View {
    private static final String TAG = ModifyTaskActivity.class.getName();

    // TASK NAME + TAG
    @BindView(R.id.task_name_edit)
    AutoCompleteTextView taskNameEdit;
    @BindView(R.id.task_name_edit_layout)
    TextInputLayout taskNameEditLayout;
    @BindView(R.id.tag_edit)
    AutoCompleteTextView tagEdit;
    @BindView(R.id.tag_edit_layout)
    TextInputLayout tagEditLayout;

    // START DAY
    @BindView(R.id.start_date_text_view)
    TextView startDateTextView;
    @BindView(R.id.start_day_edit_clickable)
    LinearLayout startDayEditClickable;

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

    // END DAY
    @BindView(R.id.end_date_text_view)
    TextView endDateTextView;
    @BindView(R.id.end_day_edit_clickable)
    LinearLayout endDayEditClickable;

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
    @BindView(R.id.percentage_text_view)
    TextView percentageTextView;

    // ERRORS
    @BindView(R.id.error_count_label)
    TextView errorCountLabel;
    @BindView(R.id.error_recycler)
    RecyclerView errorRecycler;
    @BindView(R.id.error_cardview)
    CardView errorCardview;

    // FINISH BUTTONS
    @BindView(R.id.confirm_button)
    ImageButton confirmButton;
    @BindView(R.id.cancel_button)
    ImageButton cancelButton;

    ModifyTaskContract.Presenter presenter;
    ModifyTaskAnimator animator;

    private int mTaskId = NO_TASK_ID;
    private long mStartTime = 0;
    private long mEndTime = 0;
    private boolean startTimeTasksIsCollapsed = true;
    private boolean endTimeTasksIsCollapsed = true;

    private static final String STATE_TASKID = "STATE_TASKID";
    private static final String STATE_STARTTIME = "STATE_STARTTIME";
    private static final String STATE_ENDTIME = "END_STARTTIME";
    private static final String STATE_STARTTIMETASKS_ISCOLLAPSED = "STATE_STARTTIMETASKS_ISCOLLAPSED";
    private static final String STATE_ENDTIMETASKS_ISCOLLAPSED = "STATE_ENDTIMETASKS_ISCOLLAPSED";

    ArrayList<TaskPrintable> startDayTasks;
    TaskPrintableAdapter startDayTasksAdapter;
    ArrayList<TaskPrintable> endDayTasks;
    TaskPrintableAdapter endDayTasksAdapter;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mTaskId = extras.getInt(TaskDetailActivity.BUNDLE_TASKID, NO_TASK_ID);
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

        if (mTaskId != NO_TASK_ID && savedInstanceState == null) {
            presenter.getTaskDetails(mTaskId);
        } else if (mTaskId == NO_TASK_ID && savedInstanceState == null) {
            mStartTime = System.currentTimeMillis();
            mEndTime = System.currentTimeMillis();
        }

        startDayTasks = new ArrayList<>();
        startDayTasksAdapter = new TaskPrintableAdapter(this, startDayTasks);
        startTimeTaskRecycler.setAdapter(startDayTasksAdapter);
        startTimeTaskRecycler.setLayoutManager(new LinearLayoutManager(this));

        endDayTasks = new ArrayList<>();
        endDayTasksAdapter = new TaskPrintableAdapter(this, endDayTasks);
        endTimeTaskRecycler.setAdapter(endDayTasksAdapter);
        endTimeTaskRecycler.setLayoutManager(new LinearLayoutManager(this));

        presenter.getStartDayTasks(mStartTime);
        presenter.getEndDayTasks(mEndTime);

        animator = new ModifyTaskAnimator();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTaskId = savedInstanceState.getInt(STATE_TASKID);
        setStartTime(savedInstanceState.getLong(STATE_STARTTIME));
        setEndTime(savedInstanceState.getLong(STATE_ENDTIME));
        startTimeTasksIsCollapsed = savedInstanceState.getBoolean(STATE_STARTTIMETASKS_ISCOLLAPSED);
        endTimeTasksIsCollapsed = savedInstanceState.getBoolean(STATE_ENDTIMETASKS_ISCOLLAPSED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.restoreViews(mStartTime, mEndTime);
        presenter.getStartDayTasks(mStartTime);
        presenter.getEndDayTasks(mEndTime);
        if (startTimeTasksIsCollapsed) {
            expandableStartTimeList.setVisibility(View.GONE);
        }

        if (endTimeTasksIsCollapsed) {
            expandableEndTimeList.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_TASKID, mTaskId);
        outState.putLong(STATE_STARTTIME, mStartTime);
        outState.putLong(STATE_ENDTIME, mEndTime);
        outState.putBoolean(STATE_STARTTIMETASKS_ISCOLLAPSED, !expandableStartTimeList.isShown());
        outState.putBoolean(STATE_ENDTIMETASKS_ISCOLLAPSED, !expandableEndTimeList.isShown());
        super.onSaveInstanceState(outState);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // BUTTON BINDINGS

    @OnClick(R.id.start_day_edit_clickable)
    public void onStartDayClick() {
        showStartDayPicker();
    }

    @OnClick({R.id.start_time_picker_clickable, R.id.start_time_existing_task_clickable})
    public void onStartTimeClicks(View view) {
        switch (view.getId()) {
            case R.id.start_time_picker_clickable:
                showStartTimePicker();
                break;
            case R.id.start_time_existing_task_clickable:
                if (expandableStartTimeList.isShown()) {
                    hideExpandableStartDayTasks();
                } else {
                    showExpandableStartDayTasks();
                }
                break;
        }
    }

    @OnClick(R.id.end_day_edit_clickable)
    public void onEndDayClick() {
        showEndDayPicker();
    }

    @OnClick({R.id.end_time_picker_clickable, R.id.end_time_existing_task_clickable})
    public void onEndTimeClicks(View view) {
        switch (view.getId()) {
            case R.id.end_time_picker_clickable:
                showEndTimePicker();
                break;
            case R.id.end_time_existing_task_clickable:
                if (expandableEndTimeList.isShown()) {
                    hideExpandableEndDayTasks();
                } else {
                    showExpandableEndDayTasks();
                }
                break;
        }
    }


    @OnClick({R.id.confirm_button, R.id.cancel_button})
    public void onFinishClicks(View view) {
        switch (view.getId()) {
            case R.id.confirm_button:
                confirmModifyTask();
                break;
            case R.id.cancel_button:
                closeModifyTask();
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
    public void showStartDayPicker() {
        showDayPicker(mStartTime, this::onStartDateSet);
    }

    @Override
    public void showEndDayPicker() {
        showDayPicker(mEndTime, this::onEndDateSet);
    }

    private void showDayPicker(long dateTime, DatePickerDialog.OnDateSetListener listener) {
        DateTime shown = new DateTime(dateTime);
        DatePickerDialog dpd =
                new DatePickerDialog(
                        this, listener,
                        shown.getYear(), shown.getMonthOfYear(), shown.getDayOfMonth()
                );
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
        dpd.show();
    }

    @Override
    public void showStartTimePicker() {
        showTimePicker(mStartTime, this::onStartTimeSet);
    }

    @Override
    public void showEndTimePicker() {
        showTimePicker(mEndTime, this::onEndTimeSet);
    }

    private void showTimePicker(long dateTime, TimePickerDialog.OnTimeSetListener listener) {
        DateTime shown = new DateTime(dateTime);
        TimePickerDialog tpd =
                new TimePickerDialog(
                        this, listener,
                        shown.getHourOfDay(), shown.getMinuteOfHour(),
                        DateFormat.is24HourFormat(this)
                );
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
    public void showStartDay(String formattedDay) {
        startDateTextView.setText(formattedDay);
    }

    @Override
    public void showStartTime(String startTime) {
        starttimeTextView.setText(startTime);
    }

    @Override
    public void showEndDay(String formattedDay) {
        endDateTextView.setText(formattedDay);
    }

    @Override
    public void showEndTime(String endTime) {
        endtimeTextView.setText(endTime);
    }

    @Override
    public void showDuration(String formattedDuration) {
        durationTextView.setText(formattedDuration);
    }

    @Override
    public void showPercentage(String formattedPercentage) {
        percentageTextView.setText(formattedPercentage);
    }

    @Override
    public void showExpandableStartDayTasks() {
        animator.toggleExpandCollapseAnimation(expandableStartTimeList, startTimeExistingTaskClickable);
        animator.rotateButton(startTimeExistingTaskIcon);
    }

    @Override
    public void hideExpandableStartDayTasks() {
        animator.toggleExpandCollapseAnimation(expandableStartTimeList, startTimeExistingTaskClickable);
        animator.rotateButton(startTimeExistingTaskIcon);
    }

    @Override
    public void showExpandableEndDayTasks() {
        animator.toggleExpandCollapseAnimation(expandableEndTimeList, endTimeExistingTaskClickable);
        animator.rotateButton(endTimeExistingTaskIcon);
    }

    @Override
    public void hideExpandableEndDayTasks() {
        animator.toggleExpandCollapseAnimation(expandableEndTimeList, endTimeExistingTaskClickable);
        animator.rotateButton(endTimeExistingTaskIcon);
    }

    // ERRORS
    @Override
    public void showErrorList() {
        errorCardview.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorList() {
        errorCardview.setVisibility(View.GONE);
    }

    @Override
    public void showErrorOverlappingTask(Observable<List<TaskPrintable>> overlappingTasks) {
        Toast.makeText(ModifyTaskActivity.this, "Overlapping", Toast.LENGTH_SHORT).show();
        overlappingTasks
                .subscribe(taskPrintables -> {
                    for (TaskPrintable tp : taskPrintables) {
                        Toast.makeText(ModifyTaskActivity.this, tp.taskName, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void showErrorTaskInFuture() {
        Toast.makeText(ModifyTaskActivity.this, "In future", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorStartTimeAfterEndTime() {
        Toast.makeText(ModifyTaskActivity.this, "Negative duration", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorNoDuration() {
        Toast.makeText(ModifyTaskActivity.this, "No duration", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorTaskNameInvalid() {
        Toast.makeText(ModifyTaskActivity.this, "Task Name Invalid", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorTagInvalid() {
        Toast.makeText(ModifyTaskActivity.this, "Tag Invalid", Toast.LENGTH_SHORT).show();
    }


    // AUTOCOMPLETE
    @Override
    public void autocompleteTaskNames(List<String> taskNames) {

    }

    @Override
    public void autocompleteTags(List<String> tags) {

    }

    @Override
    public void showStartDayTasks(List<TaskPrintable> startDayTasks) {
        startTimeCountLabel.setText(Integer.toString(startDayTasks.size()));
        this.startDayTasks.clear();
        this.startDayTasks.addAll(startDayTasks);
        startDayTasksAdapter.notifyDataSetChanged();
        expandableStartTimeList.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
    }

    @Override
    public void showEndDayTasks(List<TaskPrintable> endDayTasks) {
        endTimeCountLabel.setText(Integer.toString(endDayTasks.size()));
        this.endDayTasks.clear();
        this.endDayTasks.addAll(endDayTasks);
        endDayTasksAdapter.notifyDataSetChanged();
        expandableEndTimeList.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
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
        finish();
    }

    @Override
    public void confirmModifyTask() {
        boolean isValid = presenter.validateInput(
                mTaskId,
                taskNameEdit.getText().toString(),
                tagEdit.getText().toString(),
                mStartTime,
                mEndTime
        );

        if (isValid) {
            hideErrorList();
            presenter.saveTask(
                    mTaskId,
                    taskNameEdit.getText().toString(),
                    tagEdit.getText().toString(),
                    mStartTime,
                    mEndTime
            );
            finish();
        } else {
            showErrorList();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // CALLBACKS

    public void onStartDateSet(DatePicker datePicker, int year, int month, int day) {
        DateTime startDateTime = new DateTime(mStartTime)
                .withYear(year)
                // Must add 1 because DatePicker zero indexes month,
                // but DateTime uses regular month numbers
                .withMonthOfYear(month + 1)
                .withDayOfMonth(day);
        mStartTime = startDateTime.getMillis();
        presenter.restoreViews(mStartTime, mEndTime);
        presenter.getStartDayTasks(mStartTime);
    }

    private void onStartTimeSet(TimePicker tp, int hour, int minute) {
        DateTime startDateTime = new DateTime(mStartTime)
                .withHourOfDay(hour)
                .withMinuteOfHour(minute);
        mStartTime = startDateTime.getMillis();
        presenter.restoreViews(mStartTime, mEndTime);
    }

    public void onEndDateSet(DatePicker datePicker, int year, int month, int day) {
        DateTime endDateTime = new DateTime(mEndTime)
                .withYear(year)
                // Must add 1 because DatePicker zero indexes month,
                // but DateTime uses regular month numbers
                .withMonthOfYear(month + 1)
                .withDayOfMonth(day);
        mEndTime = endDateTime.getMillis();
        presenter.restoreViews(mStartTime, mEndTime);
        presenter.getEndDayTasks(mEndTime);
    }

    private void onEndTimeSet(TimePicker tp, int hour, int minute) {
        DateTime endDateTime = new DateTime(mEndTime)
                .withHourOfDay(hour)
                .withMinuteOfHour(minute);
        mEndTime = endDateTime.getMillis();
        presenter.restoreViews(mStartTime, mEndTime);
    }
}
