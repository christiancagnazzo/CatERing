package test;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.menu.MenuException;
import businesslogic.task.Sheet;
import businesslogic.task.TaskException;
import businesslogic.user.User;
import javafx.collections.ObservableList;

public class TestCatERing1 {
    public static void main(String[] args) {
        try {
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");

            EventInfo event = CatERing.getInstance().getEventManager().getEventInfo().get(2);
            ServiceInfo service = event.getServices().get(0);

            Sheet sheet = CatERing.getInstance().getTaskManager().createSheet(event, service);

            System.out.println("TEST CREATE NEW SHEET");
            System.out.println(sheet);


        } catch (UseCaseLogicException | TaskException e) {
            System.out.println("Errore di logica nello use case");

        }
    }
}
