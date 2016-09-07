package co.dijam.michael.typea101;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.dijam.michael.typea101.addcurrenttask.view.AddCurrentTaskActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.add_task_button)
    FloatingActionButton addTaskButton;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // BUTTON BINDINGS

    @OnClick(R.id.add_task_button)
    void onAddTaskButton(FloatingActionButton b){
        startActivity(new Intent(this, AddCurrentTaskActivity.class));
    }
}
