package businesslogic.turn;

import java.sql.Time;
import java.util.Date;

public class PreparationTurn extends Turn{
    private boolean saturated;

    public PreparationTurn(Date date, Time start, Time end) {
        super(date, start, end);
    }

    public void setSaturated(boolean saturated){
        this.saturated = saturated;
    }

    public boolean isSaturated() {
        return saturated;
    }

    @Override
    public String toString() {
        String s = super.toString();
        s += " Saturated: "+ saturated;
        return s;
    }
}
