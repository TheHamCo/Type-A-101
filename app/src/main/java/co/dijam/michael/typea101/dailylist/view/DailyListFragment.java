package co.dijam.michael.typea101.dailylist.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.dailylist.DailyListContract;
import co.dijam.michael.typea101.dailylist.interactor.DailyListInteractorImpl;
import co.dijam.michael.typea101.dailylist.model.TaskListItem;
import co.dijam.michael.typea101.dailylist.presenter.DailyListPresenter;
import co.dijam.michael.typea101.entities.RealmTaskManager;
import co.dijam.michael.typea101.eventbus.TaskListChangeEvent;
import co.dijam.michael.typea101.mainscreen.view.MainActivity;
import co.dijam.michael.typea101.taskdetail.view.TaskDetailActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyListFragment extends Fragment implements DailyListContract.View {
    private static final String TAG = DailyListFragment.class.getName();

    @BindView(R.id.day_tasks_list_view)
    RecyclerView dayTasksRecyclerView;

    DailyListContract.Presenter presenter;
    DailyListAdapter adapter;
    CompositeSubscription s;

    private long viewingDateTime = 0;

    ArrayList<TaskListItem> taskListItems;

    public static final String BUNDLE_TASKID = "BUNDLE_TASKID";

    public DailyListFragment() {
        // Required empty public constructor
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LIFECYCLE

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_list, container, false);
        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        if (args != null){
            viewingDateTime = args.getLong(MainActivity.BUNDLE_DATETIME);
        }

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getActivity()).build();
        Realm realm = Realm.getInstance(realmConfiguration);
        presenter = new DailyListPresenter(this, new DailyListInteractorImpl(new RealmTaskManager(realmConfiguration, realm)));

        taskListItems = new ArrayList<>();
        adapter = new DailyListAdapter(getActivity(), taskListItems);

        setOnItemClickListener();

        dayTasksRecyclerView.setAdapter(adapter);
        dayTasksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        s = new CompositeSubscription();
        s.add(
                TaskListChangeEvent.onChange().subscribe(__ -> presenter.getTaskListForDay(viewingDateTime))
        );
        presenter.getTaskListForDay(viewingDateTime);
    }

    @Override
    public void onPause() {
        if (s != null && s.hasSubscriptions() && !s.isUnsubscribed()) {
            s.unsubscribe();
        }
        super.onPause();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // BUTTON BINDINGS

    private void setOnItemClickListener() {
        adapter.setOnItemClickListener((itemView, position) -> startDetailView(adapter.getItem(position).id));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VIEW METHODS

    @Override
    public void showTaskList(Observable<List<TaskListItem>> taskListItemsObservable) {
        Log.d(TAG, "showTaskList: UPDATE LIST");
        s.add(
                taskListItemsObservable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(taskListItems1 -> {
                            taskListItems.clear();
                            taskListItems.addAll(taskListItems1);
                            for (TaskListItem tli :
                                    taskListItems1) {
                                Log.d(TAG, "showTaskList: " + tli.taskName);
                            }
                            adapter.notifyDataSetChanged();
                        })
        );
    }

    @Override
    public void startDetailView(int id) {
        Intent detailIntent = new Intent(getActivity(), TaskDetailActivity.class);
        detailIntent.putExtra(BUNDLE_TASKID, id);
        startActivity(detailIntent);
    }
}
