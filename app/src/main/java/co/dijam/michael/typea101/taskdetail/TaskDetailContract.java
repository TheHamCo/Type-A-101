package co.dijam.michael.typea101.taskdetail;

import java.util.List;

import co.dijam.michael.typea101.model.Note;
import co.dijam.michael.typea101.taskdetail.model.TaskDetail;

/**
 * Created by mdd23 on 9/11/2016.
 */
public interface TaskDetailContract {
    interface View {
        void displayTaskDetail(TaskDetail task);
        void displayNotes(List<Note> Notes);
        void addNotes(Note newNote);
        void startEditNote(int id);
    }

    interface Presenter {
        void getTaskDetails(int id);
        void getNotes(int taskId);
        void deleteTask(int taskId);
    }
}
