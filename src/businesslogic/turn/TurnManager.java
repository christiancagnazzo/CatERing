package businesslogic.turn;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TurnManager {

    public TurnManager() {
        Turn.loadAllPreparationTurns();
        Turn.loadAllServiceTurns();
    }

    // TODO: TEST
    public ObservableList<PreparationTurn> getPreparationsTurns() {
        return FXCollections.unmodifiableObservableList(Turn.getAllPreparationTurns());
    }


    public void setSaturated(PreparationTurn turn, boolean saturated){
        turn.setSaturated(saturated);
        // TODO: notify (turn persistence)
    }
}

