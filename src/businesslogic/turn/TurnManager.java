package businesslogic.turn;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TurnManager {

    public TurnManager() {
        Turn.loadAllPreparationTurns();
        Turn.loadAllServiceTurns();
    }

    public ObservableList<PreparationTurn> getPreparationsTurns() {
        return FXCollections.unmodifiableObservableList(Turn.getAllPreparationTurns());
    }

    public ObservableList<ServiceTurn> getServiceTurns() {
        return FXCollections.unmodifiableObservableList(Turn.getAllServiceTurns());
    }

    public void setSaturated(PreparationTurn turn, boolean saturated){
        turn.setSaturated(saturated);
        // TODO: notify (turn persistence)
    }
}

