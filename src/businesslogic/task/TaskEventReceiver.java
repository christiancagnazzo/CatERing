package businesslogic.task;

public interface TaskEventReceiver {
    void updateSheetCreated(Sheet sheet);

    void updateNewTaskAdded(Sheet currentSheet, Task task);
}
