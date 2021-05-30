package businesslogic.turn;

import java.sql.Time;
import java.util.Date;

public class ServiceTurn extends Turn{
    public ServiceTurn(Date date, Time start, Time end) {
        super(date, start, end);
    }
}
