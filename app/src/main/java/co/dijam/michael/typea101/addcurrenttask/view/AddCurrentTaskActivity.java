package co.dijam.michael.typea101.addcurrenttask.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.addcurrenttask.AddCurrentTaskContract;

public class AddCurrentTaskActivity extends AppCompatActivity implements AddCurrentTaskContract.View {

    AddCurrentTaskContract.Presenter presenter;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_current_task);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VIEW METHODS

    @Override
    public void closeAddTaskView() {

    }
}
