package co.dijam.michael.typea101.dailylist.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.dijam.michael.typea101.R;
import co.dijam.michael.typea101.dailylist.model.TaskListItem;

/**
 * Created by mdd23 on 9/10/2016.
 */
public class DailyListAdapter extends ArrayAdapter<TaskListItem> {

    public DailyListAdapter(Context context, List<TaskListItem> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskListItem t = getItem(position);
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_daily, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();

        vh.startTimeTextView.setText(t.formattedStartTime);
        vh.endTimeTextView.setText(t.formattedEndTime);
        vh.taskNameTextView.setText(t.taskName);
        vh.tagTextView.setText(t.tag);
        vh.durationTextView.setText(t.formattedDuration);

        return convertView;
    }

    static class ViewHolder {
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
