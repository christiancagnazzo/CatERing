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
import businesslogic.user.User;
import javafx.collections.ObservableList;

public class TestCatERingCompleto {
        public static void main(String[] args) {
            try {
                CatERing.getInstance().getUserManager().fakeLogin("Lidia");
                User cook = User.loadUser("Marinella");
                User cook2 = User.loadUser("Antonietta");

                EventInfo event = CatERing.getInstance().getEventManager().getEventInfo().get(2);
                ServiceInfo service = event.getServices().get(0);
                ObservableList<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();
                ObservableList<PreparationTurn> turns = CatERing.getInstance().getTurnManager().getPreparationsTurns();

                Sheet sheet = CatERing.getInstance().getTaskManager().createSheet(event, service);

                System.out.println("TEST CREATE NEW SHEET");
                System.out.println(sheet);

                Task t1 = CatERing.getInstance().getTaskManager().addTask(recipes.get(0));
                Task t2 = CatERing.getInstance().getTaskManager().addTask(recipes.get(1));
                Task t3 = CatERing.getInstance().getTaskManager().addTask(recipes.get(2));

                System.out.println("\n\nTEST ADD NEW TASK");
                System.out.println(sheet);

                CatERing.getInstance().getTaskManager().deleteTask(t3);

                System.out.println("\n\nTEST REMOVE TASK");
                System.out.println(sheet);


                CatERing.getInstance().getTaskManager().sortTask(t1,3);

                System.out.println("\n\nTEST ORDER TASK");
                System.out.println(sheet);

                CatERing.getInstance().getTaskManager().assignTask(turns.get(0),t1,cook,"mezz'ora","");
                CatERing.getInstance().getTaskManager().assignTask(turns.get(1),t2,"","4 porzioni");

                System.out.println("\n\nTEST ASSIGN TASK");
                System.out.println(sheet);

                CatERing.getInstance().getTaskManager().setCook(t1,cook2);

                System.out.println("\n\nTEST MODIFY COOK");
                System.out.println(sheet);

                CatERing.getInstance().getTaskManager().setNewTurn(t2,turns.get(2));

                System.out.println("\n\nTEST MODIFY TURN");
                System.out.println(sheet);

                CatERing.getInstance().getTaskManager().setTime(t2,"un'ora");

                System.out.println("\n\nTEST MODIFY TIME");
                System.out.println(sheet);

                CatERing.getInstance().getTaskManager().setPortions(t1,"una porzione");

                System.out.println("\n\nTEST MODIFY PORTION");
                System.out.println(sheet);

                CatERing.getInstance().getTaskManager().deleteAssignment(t1);

                System.out.println("\n\nTEST DELETE ASSIGNMENT");
                System.out.println(sheet);

                CatERing.getInstance().getTaskManager().setComplete(t1,true);

                System.out.println("\n\nTEST SET COMPLETED");
                System.out.println(sheet);

                CatERing.getInstance().getTurnManager().setSaturated(turns.get(0),true);

                System.out.println("\n\nTEST SET SATURATED");
                System.out.println(turns.get(0));

                CatERing.getInstance().getTaskManager().regenerateSheet(sheet);

                System.out.println("\n\nTEST REGENERATE SHEET");
                System.out.println(sheet);

            } catch (UseCaseLogicException | TaskException e) {
                System.out.println("Errore di logica nello use case");

            }
        }
}
