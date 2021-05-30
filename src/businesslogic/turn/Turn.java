package businesslogic.turn;

import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class Turn {
    private Date date;
    private String place;
    private Time start;
    private Time end;
    private Time endAvailabilityDate;
    private ArrayList<User> userAvailable;
    private ArrayList<User> userAssigned;
    private int id;

    private static Map<Integer, PreparationTurn> allPreparation = new HashMap<>();
    private static Map<Integer, ServiceTurn> allService = new HashMap<>();

    // TODO
    public Turn(Date date, Time start, Time end){
        this.id = 0;
        this.date = date;
        this.start = start;
        this.end = end;
        userAvailable = new ArrayList<>();
        userAssigned = new ArrayList<>();
    }

    public static void loadAllPreparationTurns() {
        // TODO
        // add fake turn
        allPreparation.put(0, new PreparationTurn(new Date(2021,7,3), new Time(13,40,00), new Time(15,40,00)));
        allPreparation.put(1, new PreparationTurn(new Date(2021,4,23), new Time(10,40,00), new Time(12,00,00)));
        allPreparation.put(2, new PreparationTurn(new Date(2021,10,12), new Time(11,50,00), new Time(14,00,00)));
        allPreparation.put(3, new PreparationTurn(new Date(2021,12,20), new Time(8,30,00), new Time(10,30,00)));
    }

    public static ObservableList<PreparationTurn> getAllPreparationTurns() {
        return FXCollections.observableArrayList(allPreparation.values());
    }

    public static void loadAllServiceTurns() {
        // TODO
    }

    public static ObservableList<ServiceTurn> getAllServiceTurns() {
        return FXCollections.observableArrayList(allService.values());
    }

    public boolean isExpired(){
        return this.date.before(new Date());
    }

    // TODO
    public boolean isAvailable(User u){
        //return this.userAvailable.contains(u);
        return true;
    }

    public int getId() {
        return id;
    }

}
