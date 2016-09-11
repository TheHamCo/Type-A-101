package co.dijam.michael.typea101.addcurrenttask.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.addcurrenttask.AddCurrentTaskContract;
import co.dijam.michael.typea101.addcurrenttask.interactor.AddCurrentTaskInteractorImpl;
import co.dijam.michael.typea101.addcurrenttask.presenter.AddCurrentTaskPresenter;
import co.dijam.michael.typea101.entities.RealmTaskManager;
import co.dijam.michael.typea101.entities.SharedPrefCurrentTaskManager;
import co.dijam.michael.typea101.mainscreen.view.MainActivity;
import co.dijam.michael.typea101.util.TimeFormattingUtil;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddCurrentTaskActivity extends AppCompatActivity implements AddCurrentTaskContract.View {


    @BindView(R.id.task_name_edit)
    AutoCompleteTextView taskNameEdit;
    @BindView(R.id.tag_edit)
    AutoCompleteTextView tagEdit;
    @BindView(R.id.cancel_button)
    Button cancelButton;
    @BindView(R.id.start_task_button)
    Button startTaskButton;
    @BindView(R.id.starting_time_text)
    TextView startingTimeText;

    long startTime;
    AddCurrentTaskContract.Presenter presenter;
    @BindView(R.id.task_name_edit_layout)
    TextInputLayout taskNameEditLayout;
    @BindView(R.id.tag_edit_layout)
    TextInputLayout tagEditLayout;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_current_task);
        ButterKnife.bind(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm realm = Realm.getInstance(realmConfiguration);
        presenter = new AddCurrentTaskPresenter(this,
                new AddCurrentTaskInteractorImpl(
                        new SharedPrefCurrentTaskManager(getApplicationContext()),
                        new RealmTaskManager(realmConfiguration, realm)));

        startTime = System.currentTimeMillis();
        showStartTime(TimeFormattingUtil.timeFormatter.print(startTime));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.autoCompleteTaskNames();
        presenter.autoCompleteTags();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // BUTTON BINDINGS

    @OnClick({R.id.start_task_button, R.id.cancel_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_task_button:
                String taskName = taskNameEdit.getText().toString();
                String tag = tagEdit.getText().toString();

                if (!taskNameIsValid(taskName)){
                    taskNameEditLayout.setErrorEnabled(true);
                    taskNameEditLayout.setError(getString(R.string.this_field_is_required));
                } else {
                    taskNameEditLayout.setErrorEnabled(false);
                }

                if (!tagIsValid(tag)){
                    tagEditLayout.setErrorEnabled(true);
                    tagEditLayout.setError(getString(R.string.this_field_is_required));
                } else {
                    tagEditLayout.setErrorEnabled(false);
                }

                if (taskNameIsValid(taskName) && tagIsValid(tag)){
                    presenter.setCurrentTask(
                            taskName
                            , tag
                            , startTime
                    );
                }
                break;
            case R.id.cancel_button:
                closeAddTaskView();
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VIEW METHODS

    @Override
    public void showStartTime(String formattedStartTime) {
        startingTimeText.setText(formattedStartTime);
    }

    @Override
    public void closeAddTaskView() {
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
}
