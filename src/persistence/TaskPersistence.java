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
        Task.saveNewTask(currentSheet.getId(),task, currentSheet.getTaskPosition(task)); }

    @Override
    public void updateTaskDeleted(Task task) {
        Task.removeTask(task.getId());
    }

    @Override
    public void updateTaskRearranged(Sheet sheet) {
        Sheet.saveTaskOrder(sheet);
    }

    @Override
    public void updateTaskAssigned(Task task) { Task.updateAssignment(task); }

    @Override
    public void updateTimeChanged(Task task) { Task.updateTime(task); }

    @Override
    public void updatePortionsChanged(Task task) { Task.updatePortions(task);}

    @Override
    public void updateCompleteChanged(Task task) { Task.updateCompleted(task); }

    @Override
    public void updateAssignmentRemoved(Task task) { Task.updateAssignmentRemoved(task);}
}
