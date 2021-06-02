package businesslogic.task;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.menu.Menu;
import businesslogic.menu.MenuItem;
import businesslogic.recipe.CookingProcedure;
import businesslogic.recipe.Recipe;
import businesslogic.turn.PreparationTurn;
import businesslogic.turn.Turn;
import businesslogic.user.User;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private Sheet currentSheet;
    private ArrayList<TaskEventReceiver> eventReceivers;

    public TaskManager(){
        eventReceivers = new ArrayList<>();
    }

    // TODO provaaaaaaaaaaaaaaaaaa
    public ArrayList<Task> getTask(){
        return currentSheet.getTaskList();
    }

    public Sheet createSheet(EventInfo ev, ServiceInfo serv) throws UseCaseLogicException, TaskException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();

        if (!user.isChef())
            throw new UseCaseLogicException();

        if (!ev.isAssigned(user))
            throw new TaskException();

        if (!ev.isPlanned(serv) || (serv.getMenu() == null))
            throw new UseCaseLogicException();

        Sheet sheet = new Sheet(user, serv);
        Menu m = serv.getMenu();

        List<MenuItem> allItems = m.getAllItems();

        for (MenuItem item: allItems){
            Recipe recipe = item.getItemRecipe();
            sheet.addNewTask(recipe, false);
            ArrayList<CookingProcedure> allMi = CatERing.getInstance().getRecipeManager().getAllNecessaryProcedure(recipe);
            for (CookingProcedure cp : allMi){
                sheet.addNewTask(cp, false);
            }
        }

        this.setCurrentSheet(sheet);
        this.notifySheetCreated(sheet);

        return sheet;
    }

    public Task addTask(CookingProcedure procedure) throws UseCaseLogicException {
        if (currentSheet == null)
                throw new UseCaseLogicException();

        Task task = currentSheet.addNewTask(procedure, true);

        this.notifyNewTaskAdded(task);
        return task;
    }

    public void deleteTask(Task task) throws UseCaseLogicException {
        if (currentSheet == null)
                throw new UseCaseLogicException();

        currentSheet.removeTask(task);

        this.notifyTaskDeleted(task);
    }

    public void sortTask(Task task, int position) throws UseCaseLogicException, TaskException {
        if (currentSheet == null || !currentSheet.isTaskIn(task))
            throw new UseCaseLogicException();

        if (position < 0 || position >= currentSheet.getSize())
            throw new TaskException();

        currentSheet.moveTask(task, position);

        this.notifyTaskRearranged(currentSheet);
    }


    public void openSheet(Sheet sheet) throws UseCaseLogicException, TaskException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();

        if (!user.isChef())
            throw new UseCaseLogicException();

        if (!sheet.isOwner(user))
            throw new TaskException();

        this.setCurrentSheet(sheet);
    }

    public void regenerateSheet(Sheet sheet) throws TaskException, UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();

        if (!user.isChef())
            throw new UseCaseLogicException();

        if (!sheet.isOwner(user))
            throw new TaskException();

        ArrayList<Task> deleted = currentSheet.regenerate();
        this.notifySheetRegenerated();
        for (Task task : deleted)
            this.notifyTaskDeleted(task);
    }

    /* se non vuole inserire tempo o porzioni stringhe vuote */
    public void assignTask(PreparationTurn turn, Task task, User cook, String time, String portion) throws UseCaseLogicException, TaskException {
        if (currentSheet == null || !currentSheet.isTaskIn(task))
            throw new UseCaseLogicException();

        if ( (cook != null && (!cook.isCook() || !turn.isAvailable(cook))) || (turn.isExpired()) || (turn.isSaturated()) )
            throw new TaskException();

        task.setTurn(turn, cook, time, portion);

        this.notifyTaskAssigned(task);
    }

    public void assignTask(PreparationTurn turn, Task task, String time, String portion) throws TaskException, UseCaseLogicException {
        assignTask(turn, task, null, time, portion);
    }


    public void setTime(Task task, String time) throws UseCaseLogicException {
        if (currentSheet == null || !currentSheet.isTaskIn(task))
            throw new UseCaseLogicException();

        task.setTime(time);
        this.notifyTimeChanged(task);
    }


    public void setPortions(Task task, String portions) throws UseCaseLogicException {
        if (currentSheet == null || !currentSheet.isTaskIn(task))
            throw new UseCaseLogicException();

        task.setPortions(portions);
        this.notifyPortionsChanged(task);
    }

    public void setComplete(Task task, Boolean complete) throws UseCaseLogicException {
        if (currentSheet == null || !currentSheet.isTaskIn(task))
            throw new UseCaseLogicException();

        task.setComplete(complete);
        this.notifyCompleteChanged(task);
    }


    public void deleteAssignment(Task task) throws UseCaseLogicException {
        if (currentSheet == null || !currentSheet.isTaskIn(task))
            throw new UseCaseLogicException();

        task.removeAssignment();
        this.notifyAssignmentRemoved(task);
    }

    public void setCook(Task task, User cook) throws UseCaseLogicException, TaskException {
        if (task.getTurn() == null)
            throw new UseCaseLogicException();

        this.assignTask(task.getTurn(),task,cook,task.getTime(),task.getPortions());
    }

    public void setNewTurn(Task task, PreparationTurn turn) throws TaskException, UseCaseLogicException {
        if (task.getTurn() == null)
            throw new UseCaseLogicException();
        this.assignTask(turn,task,task.getCook(),task.getTime(),task.getPortions());
    }

    //

    private void notifyNewTaskAdded(Task task) {
        for (TaskEventReceiver er : this.eventReceivers) {
            er.updateNewTaskAdded(this.currentSheet,task);
        }
    }

    private void notifySheetCreated(Sheet sheet) {
        for (TaskEventReceiver er : this.eventReceivers) {
            er.updateSheetCreated(sheet);
        }
    }

    private void notifyTaskDeleted(Task task) {
        for (TaskEventReceiver er : this.eventReceivers) {
            er.updateTaskDeleted(task);
        }
    }

    private void notifyTaskRearranged(Sheet currentSheet) {
        for (TaskEventReceiver er : this.eventReceivers) {
            er.updateTaskRearranged(currentSheet);
        }
    }

    private void setCurrentSheet(Sheet sheet) {
        this.currentSheet = sheet;
    }

    private void notifyTaskAssigned(Task task) {
        for (TaskEventReceiver er : this.eventReceivers) {
            er.updateTaskAssigned(task);
        }
    }

    private void notifyTimeChanged(Task task) {
        for (TaskEventReceiver er : this.eventReceivers) {
            er.updateTimeChanged(task);
        }
    }

    private void notifyPortionsChanged(Task task) {
        for (TaskEventReceiver er : this.eventReceivers) {
            er.updatePortionsChanged(task);
        }
    }

    private void notifyCompleteChanged(Task task) {
        for (TaskEventReceiver er : this.eventReceivers) {
            er.updateCompleteChanged(task);
        }
    }

    private void notifyAssignmentRemoved(Task task) {
        for (TaskEventReceiver er : this.eventReceivers) {
            er.updateAssignmentRemoved(task);
        }
    }

    private void notifySheetRegenerated() {
        for (TaskEventReceiver er : this.eventReceivers) {
            er.updateSheetRegenerated(currentSheet);
        }
    }

    public void addEventReceiver(TaskEventReceiver rec) {
        this.eventReceivers.add(rec);
    }

    public void removeEventReceiver(TaskEventReceiver rec) {
        this.eventReceivers.remove(rec);
    }

}
