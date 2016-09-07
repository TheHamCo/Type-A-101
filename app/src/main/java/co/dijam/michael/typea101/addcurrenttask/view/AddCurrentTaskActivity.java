package co.dijam.michael.typea101.addcurrenttask.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.addcurrenttask.AddCurrentTaskContract;
import co.dijam.michael.typea101.addcurrenttask.presenter.AddCurrentTaskPresenter;

public class AddCurrentTaskActivity extends AppCompatActivity implements AddCurrentTaskContract.View {


    @BindView(R.id.task_name_edit)
    EditText taskNameEdit;
    @BindView(R.id.tag_name_edit)
    EditText tagNameEdit;
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

        presenter = new AddCurrentTaskPresenter(this);
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
        
    }
}
