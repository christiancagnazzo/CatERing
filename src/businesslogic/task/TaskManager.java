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

    public Sheet createSheet(EventInfo ev, ServiceInfo serv) throws UseCaseLogicException {
        // TODO: TASK NOME DELLA RICETTA E NON DESCRIZIONE

        User user = CatERing.getInstance().getUserManager().getCurrentUser();

        if (!user.isChef())
            throw new UseCaseLogicException();

        if (!ev.isPlanned(serv) || (serv.getMenu() == null))
            throw new UseCaseLogicException();

        Sheet sheet = new Sheet(user, serv);
        Menu m = serv.getMenu();

        List<MenuItem> allItems = m.getAllItems();

        for (MenuItem mi: allItems){
            Recipe r = mi.getItemRecipe();
            sheet.addTask(new Task(r));
            ArrayList<CookingProcedure> allMi = CatERing.getInstance().getRecipeManager().getAllNecessaryProcedure(r);
            for (CookingProcedure cp : allMi){
                sheet.addTask(new Task(cp));
            }
        }

        this.setCurrentSheet(sheet);
        this.notifySheetCreated(sheet);

        return sheet;
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
