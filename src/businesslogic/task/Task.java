package businesslogic.task;

import businesslogic.recipe.CookingProcedure;
import businesslogic.turn.PreparationTurn;
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
    private String portions;
    private boolean completed;
    private User cook;
    private CookingProcedure procedure;
    private PreparationTurn turn;

    public Task(CookingProcedure procedure){
        this.cook = null;
        this.turn = null;
        this.procedure = procedure;
        id = 0;
    }

    public int getId() {
        return id;
    }

    public String toString(){
        return procedure.toString();
    }

    public CookingProcedure getProcedure(){ return procedure;}

    public void setTurn(PreparationTurn turn, User cook, String time, String portion) {
        this.turn = turn;
        this.cook = cook;
        this.time = time;
        this.portions = portion;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPortions(String portions){
        this.portions = portions;
    }

    public void setComplete(boolean complete){
        this.completed = complete;
    }

    // STATIC METHODS FOR PERSISTENCE

    public static void saveAllTasks(ArrayList<Task> taskList, int sheet_id) {
        String taskInsert = "INSERT INTO catering.Tasks (procedure_id, sheet_id, position) VALUES (?, ?, ?);";
        PersistenceManager.executeBatchUpdate(taskInsert, taskList.size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, taskList.get(batchCount).getProcedure().getId());
                ps.setInt(2, sheet_id);
                ps.setInt(3, batchCount);
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                taskList.get(count).id = rs.getInt(1);
            }
        });

    }

    public static void saveNewTask(int sheet_id, Task task, int position) {
        String taskInsert = "INSERT INTO catering.Tasks (procedure_id, sheet_id, position) " +
                "VALUES (" + task.getProcedure().getId() + ", " + sheet_id + ", " + position +");";
        PersistenceManager.executeUpdate(taskInsert);

        task.id = PersistenceManager.getLastId();
    }

    public static void removeTask(int task_id) {
        String rem = "DELETE FROM Tasks WHERE id = " + task_id;
        PersistenceManager.executeUpdate(rem);
    }

    public static void updateAssignment(Task task) {
        String s = "UPDATE Tasks SET time='"+ PersistenceManager.escapeString(task.time)+
                "', portions='"+PersistenceManager.escapeString(task.portions)+
                "', turn_id="+task.turn.getId();

        if (task.cook != null)
            s += ", cook_id="+task.cook.getId();
        s+= " WHERE id="+task.getId();

        PersistenceManager.executeUpdate(s);
    }

    public static void updateTime(Task task) {
        String s = "UPDATE Tasks SET time = '" + PersistenceManager.escapeString(task.time) + "'" +
                " WHERE id = " + task.getId();
        PersistenceManager.executeUpdate(s);
    }

    public static void updatePortions(Task task) {
        String s = "UPDATE Tasks SET portions = '" + PersistenceManager.escapeString(task.portions) + "'" +
                " WHERE id = " + task.getId();
        PersistenceManager.executeUpdate(s);
    }

    public static void updateCompleted(Task task) {
        // TODO !!!
        String s = "UPDATE Tasks SET completed = '" + PersistenceManager.escapeString(task.portions) + "'" +
                " WHERE id = " + task.completed;
        PersistenceManager.executeUpdate(s);
    }

}
