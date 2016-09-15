package co.dijam.michael.typea101.modifytask.view;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.dailylist.model.TaskPrintable;
import co.dijam.michael.typea101.modifytask.ModifyTaskContract;
import rx.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModifyTaskFragment extends Fragment implements ModifyTaskContract.View {

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

    public ModifyTaskFragment() {
        // Required empty public constructor
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modify_task, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // BUTTON BINDINGS

    @OnClick(R.id.day_edit_clickable)
    public void onDayEditClick() {
    }

    @OnClick({R.id.start_time_picker_clickable, R.id.start_time_existing_task_clickable})
    public void onStartTimeClick(View view) {
        switch (view.getId()) {
            case R.id.start_time_picker_clickable:
                break;
            case R.id.start_time_existing_task_clickable:
                break;
        }
    }

    @OnClick({R.id.end_time_picker_clickable, R.id.end_time_existing_task_clickable})
    public void onEndTimeClick(View view) {
        switch (view.getId()) {
            case R.id.end_time_picker_clickable:
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

    // VIEWS
    @Override
    public void showTaskName(String taskName) {

    }

    @Override
    public void showTag(String tag) {

    }

    @Override
    public void showStartTime(String startTime) {

    }

    @Override
    public void showEndTime(String endTime) {

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
}
