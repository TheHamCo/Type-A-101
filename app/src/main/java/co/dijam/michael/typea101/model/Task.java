package co.dijam.michael.typea101.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mdd23 on 9/6/2016.
 */
public class Task extends RealmObject{
    @PrimaryKey
    public int id;
    public String taskName;
    public String tag;
    public long startTime;
    public long endTime;
}
