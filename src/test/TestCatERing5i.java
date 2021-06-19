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

public class TestCatERing5i {
    public static void main(String[] args) {
        try {
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");

            EventInfo event = CatERing.getInstance().getEventManager().getEventInfo().get(2);
            ServiceInfo service = event.getServices().get(0);

            Sheet sheet = CatERing.getInstance().getTaskManager().createSheet(event, service);

            System.out.println("SHEET BEFORE");
            System.out.println(sheet);

            Task t1 = CatERing.getInstance().getTaskManager().getAllTask().get(0);
            CatERing.getInstance().getTaskManager().setComplete(t1,true);

            System.out.println("\nSHEET AFTER");
            System.out.println(sheet);

        } catch (UseCaseLogicException | TaskException e) {
            System.out.println("Errore di logica nello use case");

        }
    }
}
