package co.dijam.michael.typea101.entities;

import org.joda.time.DateTime;

import co.dijam.michael.typea101.model.Task;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by mdd23 on 9/10/2016.
 */
public class RealmTaskManager implements TaskManager {

    RealmConfiguration realmConfiguration;
    Realm realm;

    public RealmTaskManager(RealmConfiguration realmConfiguration, Realm realm) {
        this.realmConfiguration = realmConfiguration;
        this.realm = realm;
    }

    @Override
    public Observable<Task> getAllTasks() {
        // Need to call first() (along with filter(RealmResults::isLoaded)
        // to make hot observable call onComplete() and make the list.
        // (Live updates implemented separately)
        // Source: https://github.com/realm/realm-java/issues/2010
         return realm.where(Task.class)
                 .findAllAsync()
                 .asObservable()
                 .filter(RealmResults::isLoaded)
                 .first()
                 .flatMap(Observable::from);
    }

    @Override
    public Observable<Task> getAllTasksForOneDay(long dateTime) {
        DateTime dayMidnight = new DateTime(dateTime).withTimeAtStartOfDay();
        DateTime nextDayMidnight = dayMidnight.plusDays(1);

        return realm.where(Task.class)
                .between("startTime", dayMidnight.getMillis(), nextDayMidnight.getMillis())
                .or()
                .between("endTime", dayMidnight.getMillis(), nextDayMidnight.getMillis())
                .findAll()
                .asObservable()
                .filter(RealmResults::isLoaded)
                .first()
                .flatMap(Observable::from);
    }

    @Override
    public Observable<Task> getTask(int id) {
        return realm.where(Task.class)
                .equalTo("id", id)
                .findFirst()
                .asObservable();
    }

    @Override
    public int insertTask(Task newTask) {
        realm.executeTransaction(realm1 -> {
            newTask.id = autoIncrementId();
            realm1.copyToRealm(newTask);
        });
        return newTask.id;
    }

    @Override
    public void editTask(Task editedTask) throws Exception {
        realm.executeTransaction(realm1 -> {
            Task result = realm1.where(Task.class)
                   .equalTo("id", editedTask.id)
                   .findFirst();
            result.taskName = editedTask.taskName;
            result.tag = editedTask.tag;
            result.startTime = editedTask.startTime;
            result.endTime = editedTask.endTime;
        });
    }

    @Override
    public void deleteTask(int id) {
        realm.executeTransaction(realm1 -> {
            Task taskToDelete = realm1.where(Task.class)
                    .equalTo("id", id)
                    .findFirst();
            taskToDelete.deleteFromRealm();
        });
    }

    private int autoIncrementId(){
        int nextId;
        Number potentialMaxId = realm.where(Task.class).max("id");
        if (potentialMaxId == null){
            nextId = 0;
        } else {
            nextId = potentialMaxId.intValue() + 1;
        }

        return  nextId;
    }
}
