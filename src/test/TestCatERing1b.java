package test;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.task.Sheet;
import businesslogic.task.Task;
import businesslogic.task.TaskException;
import javafx.collections.ObservableList;

import java.util.Collection;

public class TestCatERing1b {
    public static void main(String[] args) {
        try {
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");

            Sheet s = CatERing.getInstance().getTaskManager().getAllSheet().get(0);

            CatERing.getInstance().getTaskManager().openSheet(s);

            ObservableList<Task> tasks = CatERing.getInstance().getTaskManager().getAllTask();

            for (Task t : tasks){
                System.out.println(t);
            }

        } catch (UseCaseLogicException | TaskException e) {
            System.out.println("Errore di logica nello use case");

        }
    }
}
