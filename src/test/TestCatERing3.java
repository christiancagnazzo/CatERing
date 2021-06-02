package test;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.recipe.Recipe;
import businesslogic.task.Sheet;
import businesslogic.task.Task;
import businesslogic.task.TaskException;
import businesslogic.user.User;
import javafx.collections.ObservableList;

public class TestCatERing3 {
    public static void main(String[] args) {
        try {
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            User currentUser = CatERing.getInstance().getUserManager().getCurrentUser();

            EventInfo event = CatERing.getInstance().getEventManager().getEventInfo().get(2);
            ServiceInfo service = event.getServices().get(0);

            Sheet sheet = CatERing.getInstance().getTaskManager().createSheet(event, service);

            ObservableList<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();
            Task t1 = CatERing.getInstance().getTaskManager().addTask(recipes.get(0));
            Task t2 = CatERing.getInstance().getTaskManager().addTask(recipes.get(1));

            System.out.println("SHEET BEFORE");
            System.out.println(sheet);

            CatERing.getInstance().getTaskManager().sortTask(t1,0);
            CatERing.getInstance().getTaskManager().sortTask(t2,2);

            System.out.println("\nSHEET AFTER");
            System.out.println(sheet);

        } catch (UseCaseLogicException | TaskException e) {
            System.out.println("Errore di logica nello use case");

        }
    }
}
