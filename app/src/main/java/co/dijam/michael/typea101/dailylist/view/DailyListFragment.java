package co.dijam.michael.typea101.dailylist.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.dailylist.DailyListContract;
import co.dijam.michael.typea101.dailylist.model.TaskListItem;
import rx.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyListFragment extends Fragment implements DailyListContract.View {


    public DailyListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_list, container, false);
    }

    @Override
    public void showTaskList(Observable<TaskListItem> taskListItems) {

    }

    @Override
    public void startDetailView(int id) {

    }
}
