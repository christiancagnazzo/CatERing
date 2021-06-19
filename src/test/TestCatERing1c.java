package test;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.recipe.Recipe;
import businesslogic.task.Sheet;
import businesslogic.task.Task;
import businesslogic.task.TaskException;
import businesslogic.turn.PreparationTurn;
import javafx.collections.ObservableList;

public class TestCatERing1c {
    public static void main(String[] args) {
        try {
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");

            EventInfo event = CatERing.getInstance().getEventManager().getEventInfo().get(2);
            ServiceInfo service = event.getServices().get(0);

            Sheet sheet = CatERing.getInstance().getTaskManager().createSheet(event, service);

            ObservableList<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();
            CatERing.getInstance().getTaskManager().addTask(recipes.get(0));
            ObservableList<PreparationTurn> turns = CatERing.getInstance().getTurnManager().getPreparationsTurns();

            Task t = CatERing.getInstance().getTaskManager().getAllTask().get(0);
            CatERing.getInstance().getTaskManager().assignTask(turns.get(2),t,"mezz'ora","3/4 porzioni");
            CatERing.getInstance().getTaskManager().setTime(t, "");
            CatERing.getInstance().getTaskManager().setPortions(t, "djnde");


            System.out.println("SHEET BEFORE");
            System.out.println(sheet);

            CatERing.getInstance().getTaskManager().regenerateSheet(sheet);


            System.out.println("\nSHEET AFTER");
            System.out.println(sheet);

        } catch (UseCaseLogicException | TaskException e) {
            System.out.println("Errore di logica nello use case");

        }
    }
}