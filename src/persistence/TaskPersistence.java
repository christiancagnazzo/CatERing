package persistence;

import businesslogic.task.Sheet;
import businesslogic.task.TaskEventReceiver;

public class TaskPersistence implements TaskEventReceiver {
    @Override
    public void updateSheetCreated(Sheet sheet) {
        Sheet.saveNewSheet(sheet);
    }
}
