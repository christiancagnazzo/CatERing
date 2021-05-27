package businesslogic.task;

import businesslogic.menu.MenuItem;
import businesslogic.recipe.CookingProcedure;
import businesslogic.user.User;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Task {
    private int id;
    private String time;
    private String portion;
    private boolean completed;
    private User cook;
    private CookingProcedure procedure;

    public Task(CookingProcedure procedure){
        this.procedure = procedure;
        id = 0;
    }

    public CookingProcedure getProcedure(){ return procedure;}

    // STATIC METHODS FOR PERSISTENCE

    public static void saveAllTasks(ArrayList<Task> taskList, int sheet_id) {
        String taskInsert = "INSERT INTO catering.Tasks (procedure_id, sheet_id) VALUES (?, ?);";
        PersistenceManager.executeBatchUpdate(taskInsert, taskList.size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, taskList.get(batchCount).getProcedure().getId());
                ps.setInt(2, sheet_id);
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                taskList.get(count).id = rs.getInt(1);
            }
        });

    }

    public String toString(){
        return procedure.toString();
    }

}
