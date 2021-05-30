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

    public void removeTask(Task task){
        this.taskList.remove(task);
    }

    public int getTaskPosition(Task task){
        return taskList.indexOf(task);
    }

    public int getSize(){
        return taskList.size();
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

    public boolean isTaskIn(Task task) {
        return taskList.contains(task);
    }

    public void moveTask(Task task, int position) {
        taskList.remove(task);
        taskList.add(position,task);
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

    public static void saveTaskOrder(Sheet sheet) {
        String s = "UPDATE Tasks SET position = ? WHERE id = ?";
        PersistenceManager.executeBatchUpdate(s, sheet.taskList.size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, batchCount);
                ps.setInt(2, sheet.taskList.get(batchCount).getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                // no generated ids to handle
            }
        });
    }


    // TODO
    /*public static ObservableList<Sheet> loadAllSheet(){
        String query = "SELECT * FROM Sheets WHERE " + true;
        ArrayList<Sheet> newSheets = new ArrayList<>();
        ArrayList<Sheet> oldSheets = new ArrayList<>();
        ArrayList<Integer> oldUids = new ArrayList<>();
        ArrayList<Integer> oldSids = new ArrayList<>();

        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                if (loadedSheet.containsKey(id)) {
                    Sheet s = loadedSheet.get(id);
                    oldUids.add(rs.getInt("owner_id"));
                    oldSids.add(rs.getInt("service_id"));
                    oldSheets.add(s);
                } else {
                    int user_id = rs.getInt("owner_id");
                    int service_id = rs.getInt("service_id");
                    User u = User.loadUserById(user_id);
                    ServiceInfo ser = ServiceInfo.loadServiceById(service_id);
                    Sheet s = new Sheet(u,ser);
                    s.id = id;
                    newSheets.add(s);
                }
            }
        });
    }*/

  }