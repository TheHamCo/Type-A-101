package co.dijam.michael.typea101.dailylist.model;

import co.dijam.michael.typea101.MyApplication;
import co.dijam.michael.typea101.R;

/**
 * Created by mdd23 on 9/10/2016.
 */
public class TaskListItem {
    public int id;
    public String taskName;
    public String tag;
    public String formattedStartTime;
    public String formattedEndTime;
    public String formattedDuration;
    private String formattedPercentage;

    public String getFormattedPercentage() {
        return formattedPercentage;
    }

    public void setFormattedPercentage(String formattedPercentage) {
        this.formattedPercentage = formattedPercentage;
    }

    public void setFormattedPercentage(float percentage){
        // TODO: Add device locale to String.format
        this.formattedPercentage = String.format("%.3f %s", percentage, MyApplication.getContext().getString(R.string.percent_of_day));
    }
}
