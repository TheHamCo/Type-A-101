package co.dijam.michael.typea101.eventbus;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by mdd23 on 9/10/2016.
 */
public class TaskListChangeEvent {
    private static PublishSubject<Void> subject = PublishSubject.create();

    private TaskListChangeEvent(){
    }

    public static void publishChange(){
        subject.onNext(null);
    }

    public static Observable<Void> onChange() {
        return subject;
    }
}
