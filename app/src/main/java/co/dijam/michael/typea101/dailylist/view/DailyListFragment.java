package co.dijam.michael.typea101.dailylist.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyListFragment extends Fragment implements DailyListContract.View {

    @BindView(R.id.day_tasks_list_view)
    ListView dayTasksListView;

    DailyListContract.Presenter presenter;
    DailyListAdapter adapter;
    Subscription taskListItemSubscription;

    private long viewingDateTime = 0;

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

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getActivity()).build();
        Realm realm = Realm.getInstance(realmConfiguration);
        presenter = new DailyListPresenter(this, new DailyListInteractorImpl(new RealmTaskManager(realmConfiguration, realm)));

        adapter = new DailyListAdapter(getActivity(), new ArrayList<>());
        dayTasksListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getTaskListForDay(viewingDateTime);
    }

    @Override
    public void onPause() {
        if (taskListItemSubscription != null && !taskListItemSubscription.isUnsubscribed()) {
            taskListItemSubscription.unsubscribe();
        }
        super.onPause();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VIEW METHODS

    @Override
    public void showTaskList(Observable<List<TaskListItem>> taskListItemsObservable) {
        taskListItemSubscription = taskListItemsObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskListItems -> {
                    adapter.clear();
                    adapter.addAll(taskListItems);
                });
    }

    @Override
    public void startDetailView(int id) {

    }
}
