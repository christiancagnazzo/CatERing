package businesslogic.turn;

import businesslogic.user.User;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Turn {
    private Date date;
    private String place;
    private Time start;
    private Time end;
    private Time endAvailabilityDate;
    private ArrayList<User> userAvailable;
    private ArrayList<User> userAssigned;
    private int id;

    // TODO
    public Turn(){
        this.id = 0;
    }

    public boolean isExpired(){
        return false;
    }

    // TODO
    public boolean isAvailable(User u){
        return true;
    }

    public int getId() {
        return id;
    }
}
