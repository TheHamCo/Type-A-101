package co.dijam.michael.typea101.modifycurrenttask.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.entities.RealmTaskManager;
import co.dijam.michael.typea101.entities.SharedPrefCurrentTaskManager;
import co.dijam.michael.typea101.mainscreen.view.MainActivity;
import co.dijam.michael.typea101.modifycurrenttask.ModifyCurrentTaskContract;
import co.dijam.michael.typea101.modifycurrenttask.interactor.ModifyCurrentTaskInteractorImpl;
import co.dijam.michael.typea101.modifycurrenttask.presenter.ModifyCurrentTaskPresenter;
import co.dijam.michael.typea101.util.TimeFormattingUtil;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ModifyCurrentTaskActivity extends AppCompatActivity implements ModifyCurrentTaskContract.View {

    @BindView(R.id.task_name_edit_layout)
    TextInputLayout taskNameEditLayout;
    @BindView(R.id.task_name_edit)
    AutoCompleteTextView taskNameEdit;
    @BindView(R.id.tag_edit_layout)
    TextInputLayout tagEditLayout;
    @BindView(R.id.tag_edit)
    AutoCompleteTextView tagEdit;

    @BindView(R.id.starting_time_text)
    TextView startingTimeText;

    // Adding Buttons
    @BindView(R.id.add_task_buttons)
    FrameLayout addTaskButtons;
    @BindView(R.id.cancel_button)
    Button cancelButton;
    @BindView(R.id.start_task_button)
    Button startTaskButton;

    // Editing Buttons
    @BindView(R.id.edit_task_buttons)
    FrameLayout editTaskButtons;
    @BindView(R.id.delete_task_button)
    Button deleteTaskButton;
    @BindView(R.id.cancel_edit_button)
    Button cancelEditButton;
    @BindView(R.id.finish_editing_button)
    Button finishEditingButton;

    private static final String BUNDLE_STARTTIME = "BUNDLE_STARTTTIME";

    long startTime;
    ModifyCurrentTaskContract.Presenter presenter;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_current_task);
        ButterKnife.bind(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm realm = Realm.getInstance(realmConfiguration);
        presenter = new ModifyCurrentTaskPresenter(this,
                new ModifyCurrentTaskInteractorImpl(
                        new SharedPrefCurrentTaskManager(getApplicationContext()),
                        new RealmTaskManager(realmConfiguration, realm)));


        if (presenter.currentTaskExists() && savedInstanceState == null) {
            startTime = presenter.getCurrentTaskStartTime();
            presenter.setupEditView();
        } else if (!presenter.currentTaskExists() && savedInstanceState == null) {
            startTime = System.currentTimeMillis();
            presenter.setupAddView(startTime);
        } else if (savedInstanceState != null) {
            startTime = savedInstanceState.getLong(BUNDLE_STARTTIME);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(BUNDLE_STARTTIME, startTime);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.autoCompleteTaskNames();
        presenter.autoCompleteTags();
        showStartTime(TimeFormattingUtil.timeFormatter.print(startTime));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // BUTTON BINDINGS


    @OnClick({R.id.start_task_button, R.id.cancel_button})
    public void onClickAdd(View view) {
        switch (view.getId()) {
            case R.id.start_task_button:
                validateInputAndSetCurrentTask();
                break;
            case R.id.cancel_button:
                closeModifyTaskView();
                break;
        }
    }

    @OnClick({R.id.delete_task_button, R.id.cancel_edit_button, R.id.finish_editing_button})
    public void onClickEdit(View view) {
        switch (view.getId()) {
            case R.id.delete_task_button:
                presenter.clearCurrentTask();
                break;
            case R.id.cancel_edit_button:
                closeModifyTaskView();
                break;
            case R.id.finish_editing_button:
                validateInputAndSetCurrentTask();
                break;
        }
    }

    private void validateInputAndSetCurrentTask() {
        String taskName = taskNameEdit.getText().toString();
        String tag = tagEdit.getText().toString();

        if (!taskNameIsValid(taskName)) {
            taskNameEditLayout.setErrorEnabled(true);
            taskNameEditLayout.setError(getString(R.string.this_field_is_required));
        } else {
            taskNameEditLayout.setErrorEnabled(false);
        }

        if (!tagIsValid(tag)) {
            tagEditLayout.setErrorEnabled(true);
            tagEditLayout.setError(getString(R.string.this_field_is_required));
        } else {
            tagEditLayout.setErrorEnabled(false);
        }

        if (taskNameIsValid(taskName) && tagIsValid(tag)) {
            presenter.setCurrentTask(
                    taskName
                    , tag
                    , startTime
            );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VIEW METHODS

    @Override
    public void showStartTime(String formattedStartTime) {
        startingTimeText.setText(formattedStartTime);
    }

    @Override
    public void closeModifyTaskView() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public boolean taskNameIsValid(String taskName) {
        return !taskName.trim().isEmpty();
    }

    @Override
    public boolean tagIsValid(String tag) {
        return !tag.trim().isEmpty();
    }

    @Override
    public void autoCompleteTaskNames(List<String> taskNames) {
        ArrayAdapter<String> taskNameAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, taskNames);
        taskNameEdit.setAdapter(taskNameAdapter);
        taskNameEdit.setThreshold(1);
    }

    @Override
    public void autoCompleteTags(List<String> tags) {
        ArrayAdapter<String> tagAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, tags);
        tagEdit.setAdapter(tagAdapter);
        tagEdit.setThreshold(1);
    }

    // EDITING
    @Override
    public void setCurrentTaskTaskName(String taskName) {
        taskNameEdit.setText(taskName);
    }

    @Override
    public void setCurrentTaskTag(String tag) {
        tagEdit.setText(tag);
    }

    @Override
    public void showAddButtonsOnly() {
        addTaskButtons.setVisibility(View.VISIBLE);
        editTaskButtons.setVisibility(View.GONE);
    }

    @Override
    public void showEditButtonsOnly() {
        editTaskButtons.setVisibility(View.VISIBLE);
        addTaskButtons.setVisibility(View.GONE);
    }
}
