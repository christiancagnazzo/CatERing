package persistence;

import businesslogic.task.Sheet;
import businesslogic.task.Task;
import businesslogic.task.TaskEventReceiver;

public class TaskPersistence implements TaskEventReceiver {
    @Override
    public void updateSheetCreated(Sheet sheet) {
        Sheet.saveNewSheet(sheet);
    }

    @Override
    public void updateNewTaskAdded(Sheet currentSheet, Task task) {
        Task.saveNewTask(currentSheet.getId(),task);
    }
}
