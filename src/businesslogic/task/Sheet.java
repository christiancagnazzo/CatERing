package businesslogic.task;

import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.menu.Menu;
import businesslogic.menu.MenuItem;
import businesslogic.menu.Section;
import businesslogic.recipe.CookingProcedure;
import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sheet {
    private static Map<Integer, Sheet> loadedSheet = FXCollections.observableHashMap();
    private int id;
    private User owner;
    private ServiceInfo service;
    private ArrayList<Task> taskList;

    public Sheet(User owner, ServiceInfo service){
        id = 0;
        this.service = service;
        this.owner = owner;
        taskList = new ArrayList<>();
    }

    public Task addNewTask(CookingProcedure cookingProcedure){
        Task task = new Task(cookingProcedure);
        taskList.add(task);
        return task;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("EVENT ID: ").append(service.getEvenId()).append("\n");
        s.append(service.toString()).append("\n");
        s.append("FOGLIO RIEPILOGATIVO");
        for (Task t : taskList)
            s.append("\n").append(t);
        return s.toString();
    }

    public int getId(){ return id;}

    public boolean isOwner(User user){
        return user.getId() == owner.getId();
    }



    // STATIC METHODS FOR PERSISTENCE

    public static void saveNewSheet(Sheet s) {
        String sheetInsert = "INSERT INTO catering.Sheets (service_id, owner_id) VALUES (?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(sheetInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, s.service.getServiceId());
                ps.setInt(2, s.owner.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                // should be only one
                if (count == 0) {
                    s.id = rs.getInt(1);
                }
            }
        });
        if (result[0] > 0) { // foglio effettivamente inserito
            // salvo tutti i task
            Task.saveAllTasks(s.taskList, s.id);

        loadedSheet.put(s.id,s);
        }
    }


   /* public static ObservableList<Sheet> loadAllSheet() {
        ObservableList<Sheet> all = FXCollections.observableArrayList();
        String query = "SELECT * FROM Sheets WHERE true";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                int service_id = rs.getInt("service_id");
                int owner_id = rs.getInt("owner_id");
                ServiceInfo service = ServiceInfo.loadServiceById(service_id);
                User user = User.loadUserById(owner_id);
                Sheet sheet = new Sheet(user,service);
                sheet.id = id;
                all.add(sheet);
            }
        });

        for (Sheet e : all) {
            loadTasksForSheet(e.id);
            query select task where sheet_id = id
                    ...
        }
        return all;
    }*/
  }