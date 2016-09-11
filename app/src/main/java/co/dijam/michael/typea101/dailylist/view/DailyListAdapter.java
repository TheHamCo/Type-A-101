package co.dijam.michael.typea101.dailylist.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.dailylist.model.TaskListItem;

/**
 * Created by mdd23 on 9/10/2016.
 */
public class DailyListAdapter extends RecyclerView.Adapter<DailyListAdapter.ViewHolder> {

    private List<TaskListItem> taskListItems;
    private Context context;

    public DailyListAdapter(Context context, List<TaskListItem> taskListItems) {
        this.taskListItems = taskListItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View taskListItemView = LayoutInflater.from(context).inflate(R.layout.list_item_daily, parent, false);
        return new ViewHolder(taskListItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        TaskListItem t = taskListItems.get(position);
        vh.startTimeTextView.setText(t.formattedStartTime);
        vh.endTimeTextView.setText(t.formattedEndTime);
        vh.taskNameTextView.setText(t.taskName);
        vh.tagTextView.setText(t.tag);
        vh.durationTextView.setText(t.formattedDuration);
        vh.percentageTextView.setText(t.getFormattedPercentage());
    }

    @Override
    public int getItemCount() {
        return taskListItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.start_time_text_view)
        TextView startTimeTextView;
        @BindView(R.id.end_time_text_view)
        TextView endTimeTextView;
        @BindView(R.id.task_name_text_view)
        TextView taskNameTextView;
        @BindView(R.id.tag_name_text_view)
        TextView tagTextView;
        @BindView(R.id.duration_text_view)
        TextView durationTextView;
        @BindView(R.id.percentage_text_view)
        TextView percentageTextView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
