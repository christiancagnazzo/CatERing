package businesslogic.task;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.menu.Menu;
import businesslogic.menu.MenuEventReceiver;
import businesslogic.menu.MenuItem;
import businesslogic.recipe.CookingProcedure;
import businesslogic.recipe.Recipe;
import businesslogic.user.User;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private Sheet currentSheet;
    private ArrayList<TaskEventReceiver> eventReceivers;

    public TaskManager(){
        eventReceivers = new ArrayList<>();
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
            sheet.addNewTask(recipe);
            ArrayList<CookingProcedure> allMi = CatERing.getInstance().getRecipeManager().getAllNecessaryProcedure(recipe);
            for (CookingProcedure cp : allMi){
                sheet.addNewTask(recipe);
            }
        }

        this.setCurrentSheet(sheet);
        this.notifySheetCreated(sheet);

        return sheet;
    }

    public Task addTask(CookingProcedure procedure) throws UseCaseLogicException {
        if (currentSheet == null)
                throw new UseCaseLogicException();

        Task task = currentSheet.addNewTask(procedure);

        this.notifyNewTaskAdded(task);
        return task;
    }

    public void openSheet(Sheet sheet) throws UseCaseLogicException, TaskException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();

        if (!user.isChef())
            throw new UseCaseLogicException();

        if (!sheet.isOwner(user))
            throw new TaskException();

        setCurrentSheet(sheet);
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

    private void setCurrentSheet(Sheet sheet) {
        this.currentSheet = sheet;
    }

    public void addEventReceiver(TaskEventReceiver rec) {
        this.eventReceivers.add(rec);
    }

    public void removeEventReceiver(TaskEventReceiver rec) {
        this.eventReceivers.remove(rec);
    }

}
