package co.dijam.michael.typea101.addcurrenttask.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.addcurrenttask.AddCurrentTaskContract;
import co.dijam.michael.typea101.addcurrenttask.interactor.AddCurrentTaskInteractorImpl;
import co.dijam.michael.typea101.addcurrenttask.presenter.AddCurrentTaskPresenter;
import co.dijam.michael.typea101.entities.SharedPrefCurrentTaskManager;
import co.dijam.michael.typea101.mainscreen.view.MainActivity;
import co.dijam.michael.typea101.util.TimeFormattingUtil;

public class AddCurrentTaskActivity extends AppCompatActivity implements AddCurrentTaskContract.View {


    @BindView(R.id.task_name_edit)
    TextInputEditText taskNameEdit;
    @BindView(R.id.tag_name_edit)
    TextInputEditText tagNameEdit;
    @BindView(R.id.cancel_button)
    Button cancelButton;
    @BindView(R.id.start_task_button)
    Button startTaskButton;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.starting_time_text)
    TextView startingTimeText;

    long startTime;
    AddCurrentTaskContract.Presenter presenter;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_current_task);
        ButterKnife.bind(this);

        presenter = new AddCurrentTaskPresenter(this,
                new AddCurrentTaskInteractorImpl(new SharedPrefCurrentTaskManager(getApplicationContext())));

        startTime = System.currentTimeMillis();
        startingTimeText.setText(TimeFormattingUtil.timeFormatter.print(startTime));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // BUTTON BINDINGS

    @OnClick(R.id.cancel_button)
    void onCancelButton(Button b){
        closeAddTaskView();
    }

    @OnClick(R.id.start_task_button)
    void onFinishButton(Button b){
        presenter.setCurrentTask(
                  taskNameEdit.getText().toString()
                , tagNameEdit.getText().toString()
                , startTime
        );
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VIEW METHODS

    @Override
    public void closeAddTaskView() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
