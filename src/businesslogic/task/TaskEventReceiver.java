package businesslogic.task;

public interface TaskEventReceiver {
    void updateSheetCreated(Sheet sheet);

    void updateNewTaskAdded(Sheet currentSheet, Task task);

    void updateTaskDeleted(Task task);

    void updateTaskRearranged(Sheet currentSheet);

    void updateTaskAssigned(Task task);

    void updateTimeChanged(Task task);

    void updatePortionsChanged(Task task);

    void updateCompleteChanged(Task task);
}
