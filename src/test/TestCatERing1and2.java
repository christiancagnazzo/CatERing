package test;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.menu.Menu;
import businesslogic.recipe.Recipe;
import businesslogic.task.Sheet;
import businesslogic.task.Task;
import businesslogic.task.TaskException;
import businesslogic.turn.PreparationTurn;
import businesslogic.turn.Turn;
import businesslogic.user.User;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TestCatERing1and2 {
    public static void main(String[] args) throws UseCaseLogicException, TaskException {
        System.out.println("TEST FAKE LOGIN");
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        User currentUser = CatERing.getInstance().getUserManager().getCurrentUser();
        System.out.println(currentUser);

        ObservableList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
        ServiceInfo s = events.get(2).getServices().get(0);
        Menu m = s.getMenu();

        if (m != null)
            System.out.println("Menu nr. "+m.getId());
        Sheet sheet = CatERing.getInstance().getTaskManager().createSheet(events.get(2),s);
        //System.out.println(sheet);

        ObservableList<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();
        Task task = CatERing.getInstance().getTaskManager().addTask(recipes.get(0));
        ObservableList<PreparationTurn> turns = CatERing.getInstance().getTurnManager().getPreparationsTurns();

        Task t = CatERing.getInstance().getTaskManager().getTask().get(0);
        CatERing.getInstance().getTaskManager().assignTask(turns.get(2),t,"cdicdisci","cjdnjcdj");
        CatERing.getInstance().getTaskManager().setComplete(t, true);
        CatERing.getInstance().getTaskManager().setTime(t, "bjbbj");
        CatERing.getInstance().getTaskManager().setPortions(t, "djnde");


        System.out.println(sheet);
        //CatERing.getInstance().getTaskManager().sortTask(task, 3);

        //CatERing.getInstance().getTaskManager().deleteTask(task);
        //System.out.println(sheet);

        User cook = User.loadUserById(3); // !!!!!

        CatERing.getInstance().getTaskManager().assignTask(turns.get(0), task,"","3 porzioni");

        CatERing.getInstance().getTaskManager().setTime(task,"mezz'ora");
        CatERing.getInstance().getTaskManager().setPortions(task,"10 porzioni");
        CatERing.getInstance().getTaskManager().setComplete(task, true);

        //CatERing.getInstance().getTaskManager().deleteAssignment(task);
        CatERing.getInstance().getTaskManager().setNewTurn(task, turns.get(1));
        CatERing.getInstance().getTaskManager().setCook(task, cook);

        CatERing.getInstance().getTaskManager().regenerateSheet(sheet);
        System.out.println(sheet);
        // todo: test openSheet()
        // todo: test loadsheet()
    }
}