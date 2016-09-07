package co.dijam.michael.typea101.model;

/**
 * Created by mdd23 on 9/6/2016.
 */
public class CurrentTask {
    public String taskName;
    public String tag;
    public long startTime;

    @Override
    public String toString() {
        return "CurrentTask{" +
                "taskName='" + taskName + '\'' +
                ", tag='" + tag + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}
