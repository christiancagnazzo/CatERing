package businesslogic.task;

import businesslogic.event.ServiceInfo;
import businesslogic.menu.Menu;
import businesslogic.recipe.CookingProcedure;
import businesslogic.user.User;
import javafx.collections.FXCollections;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    // STATIC METHODS FOR PERSISTENCE
    // todo: load all sheet

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
}