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

        System.out.println(sheet);
        //CatERing.getInstance().getTaskManager().sortTask(task, 3);

        //CatERing.getInstance().getTaskManager().deleteTask(task);
        //System.out.println(sheet);

        User cook = User.loadUserById(3); // !!!!!
        PreparationTurn turn = new PreparationTurn(); // !!!!
        CatERing.getInstance().getTaskManager().assignTask(turn, task,"","3 porzioni");

        CatERing.getInstance().getTaskManager().setTime(task,"mezz'ora");
        CatERing.getInstance().getTaskManager().setPortions(task,"10 porzioni");
        CatERing.getInstance().getTaskManager().setComplete(task, true);

        // todo: migliora test (separa e Load Sheet)
        // todo: test openSheet()
        // todo: test loadsheet()
    }
}