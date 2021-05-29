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
import businesslogic.user.User;
import javafx.collections.ObservableList;

public class TestCatERing1and2 {
    public static void main(String[] args) throws UseCaseLogicException, TaskException {
        System.out.println("TEST FAKE LOGIN");
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        User currentUser = CatERing.getInstance().getUserManager().getCurrentUser();
        System.out.println(currentUser);

        ObservableList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
        ServiceInfo s = events.get(2).getServices().get(1); // servizio 0
        Menu m = s.getMenu();

        if (m != null)
            System.out.println("Menu nr. "+m.getId());
        Sheet sheet = CatERing.getInstance().getTaskManager().createSheet(events.get(2),s);
        System.out.println(sheet);

        ObservableList<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();
        Task task = CatERing.getInstance().getTaskManager().addTask(recipes.get(0));

        System.out.println(sheet);

        // todo: migliora test (separa e Load Sheet)
        // todo: test openSheet()
    }
}