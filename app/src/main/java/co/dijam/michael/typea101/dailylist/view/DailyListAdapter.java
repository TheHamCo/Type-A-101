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
import co.dijam.michael.typea101.dailylist.model.TaskPrintable;

/**
 * Created by mdd23 on 9/10/2016.
 */
public class DailyListAdapter extends RecyclerView.Adapter<DailyListAdapter.ViewHolder> {

    private List<TaskPrintable> taskPrintables;
    private Context context;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // OnItemClickListener (also see ViewHolder constructor)

    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public TaskPrintable getItem(int position){
        return taskPrintables.get(position);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public DailyListAdapter(Context context, List<TaskPrintable> taskPrintables) {
        this.taskPrintables = taskPrintables;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View taskListItemView = LayoutInflater.from(context).inflate(R.layout.list_item_daily, parent, false);
        return new ViewHolder(taskListItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        TaskPrintable t = taskPrintables.get(position);
        vh.startTimeTextView.setText(t.formattedStartTime);
        vh.endTimeTextView.setText(t.formattedEndTime);
        vh.taskNameTextView.setText(t.taskName);
        vh.tagTextView.setText(t.tag);
        vh.durationTextView.setText(t.formattedDuration);
        vh.percentageTextView.setText(t.getFormattedPercentage());
    }

    @Override
    public int getItemCount() {
        return taskPrintables.size();
    }

    // This does not need to be static because even though the static one will take less memory,
    // the recycler will recycle the instances, so memory impact is not a problem.
    // Source: http://stackoverflow.com/a/31302613/5302182
    public class ViewHolder extends RecyclerView.ViewHolder {
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

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                if (listener != null){
                    listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }
}
