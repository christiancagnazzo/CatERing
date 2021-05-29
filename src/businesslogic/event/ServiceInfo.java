package businesslogic.event;

import businesslogic.menu.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class ServiceInfo implements EventItemInfo {
    private int id;
    private String name;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private int participants;
    private int event;
    private Menu menu;

    public ServiceInfo(String name) {
        this.name = name;
    }


    public String toString() {
        return name + ": " + date + " (" + timeStart + "-" + timeEnd + "), " + participants + " pp.";
    }

    public Menu getMenu(){
        return menu;
    }

    public int getEvenId(){ return event;}
    public int getServiceId(){ return id;}

    // STATIC METHODS FOR PERSISTENCE

    public static ObservableList<ServiceInfo> loadServiceInfoForEvent(int event_id) {
        ObservableList<ServiceInfo> result = FXCollections.observableArrayList();
        String query = "SELECT id, name, service_date, time_start, time_end, expected_participants, approved_menu_id " +
                "FROM Services WHERE event_id = " + event_id;
        PersistenceManager.executeQuery(query, rs -> {
            String s = rs.getString("name");
            ServiceInfo serv = new ServiceInfo(s);
            serv.id = rs.getInt("id");
            serv.date = rs.getDate("service_date");
            serv.timeStart = rs.getTime("time_start");
            serv.timeEnd = rs.getTime("time_end");
            serv.participants = rs.getInt("expected_participants");
            serv.event = event_id;
            int menu_id = rs.getInt("approved_menu_id");
            if (menu_id != 0)
                serv.menu = Menu.loadMenuById(menu_id);
            result.add(serv);
        });

        return result;
    }
}
