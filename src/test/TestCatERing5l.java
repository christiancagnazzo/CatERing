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

public class TestCatERing5l {
    public static void main(String[] args) {
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");

        ObservableList<PreparationTurn> turns = CatERing.getInstance().getTurnManager().getPreparationsTurns();
        PreparationTurn turn = turns.get(0);

        System.out.println(turn);
        CatERing.getInstance().getTurnManager().setSaturated(turn,true);
        System.out.println(turn);

    }
}
