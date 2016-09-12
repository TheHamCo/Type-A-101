package co.dijam.michael.typea101.taskdetail.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.dailylist.view.DailyListFragment;
import co.dijam.michael.typea101.entities.RealmTaskManager;
import co.dijam.michael.typea101.model.Note;
import co.dijam.michael.typea101.taskdetail.TaskDetailContract;
import co.dijam.michael.typea101.taskdetail.interactor.TaskDetailInteractorImpl;
import co.dijam.michael.typea101.taskdetail.model.TaskDetail;
import co.dijam.michael.typea101.taskdetail.presenter.TaskDetailPresenter;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TaskDetailActivity extends AppCompatActivity implements TaskDetailContract.View {

    @BindView(R.id.task_name_text_view)
    TextView taskNameTextView;
    @BindView(R.id.tag_text_view)
    TextView tagTextView;
    @BindView(R.id.starting_time_text)
    TextView startingTimeText;
    @BindView(R.id.ending_time_text)
    TextView endingTimeText;
    @BindView(R.id.duration_text_view)
    TextView durationTextView;
    @BindView(R.id.percentage_text_view)
    TextView percentageTextView;

    @BindView(R.id.delete_task_button)
    Button deleteTaskButton;
    @BindView(R.id.edit_button)
    Button editButton;

    TaskDetailContract.Presenter presenter;

    private int mTaskId = 0;

    private static final String STATE_TASKID = "STATE_TASKID";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        mTaskId = extras.getInt(DailyListFragment.BUNDLE_TASKID);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm realm = Realm.getInstance(realmConfiguration);
        presenter = new TaskDetailPresenter(new TaskDetailInteractorImpl(new RealmTaskManager(realmConfiguration, realm)), this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTaskId = savedInstanceState.getInt(STATE_TASKID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getTaskDetails(mTaskId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_TASKID, mTaskId);
        super.onSaveInstanceState(outState);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // BUTTON BINDINGS

    @OnClick({R.id.delete_task_button, R.id.edit_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_task_button:
                presenter.deleteTask(mTaskId);
                finish();
                break;
            case R.id.edit_button:
                startEditNote(mTaskId);
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VIEW METHODS

    @Override
    public void displayTaskDetail(TaskDetail task) {
        taskNameTextView.setText(task.taskName);
        tagTextView.setText(task.tag);
        startingTimeText.setText(task.formattedStartTime);
        endingTimeText.setText(task.formattedEndTime);
        durationTextView.setText(task.formattedDuration);
        percentageTextView.setText(task.getFormattedPercentage());
    }

    @Override
    public void displayNotes(List<Note> Notes) {

    }

    @Override
    public void addNotes(Note newNote) {

    }

    @Override
    public void startEditNote(int id) {

    }
}
