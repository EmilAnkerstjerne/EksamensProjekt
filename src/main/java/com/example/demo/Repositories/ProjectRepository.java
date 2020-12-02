package com.example.demo.Repositories;

import com.example.demo.Models.Project;
import com.example.demo.Models.Subproject;
import com.example.demo.Models.Subtask;
import com.example.demo.Models.Task;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//JOHN
public class ProjectRepository {

    private Connection connection;

    public boolean setConnection(){
        boolean bres = false;
        String url = "jdbc:mysql://localhost:3306/skidegodt?serverTimezone=UTC";
        try{
            connection = DriverManager.getConnection(url,"SkideGodt","SkideGodt");
            bres = true;
        }
        catch (SQLException e){
            System.out.println("No connection to sever="+e.getMessage());
        }
        return bres;
    }

    //TODO: Move. JOHN
    public ArrayList<String> getAllProfiles(){
        String selectStatement = "SELECT username FROM users";
        ArrayList<String> list = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String username = resultSet.getString("username");
                list.add(username);
            }
        }
        catch (SQLException e){
            System.out.println();
            return null;
        }
        return list;
    }

    //JOHN
    /**
     * @author John
     * @param projectID
     * @return Project object or null if exception
     */
    public Project getProject(int projectID){
        String selectStatement = "SELECT * FROM projects WHERE project_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, projectID);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            String name = resultSet.getString("name");
            int adminUserID  = resultSet.getInt("admin_user_id");

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date deadline = null;
            Date startDate = null;
            try{
                deadline = sdf.parse(resultSet.getString("deadline"));
            }
            catch (ParseException | NullPointerException e){
            }
            try{
                startDate = sdf.parse(resultSet.getString("startdate"));
            }
            catch (ParseException | NullPointerException e){
            }
            int weeklyHours  = resultSet.getInt("weekly_hours");
            int weeklyDays = resultSet.getInt("weekly_days");
            int daysOff = resultSet.getInt("days_off");
            boolean archived = resultSet.getBoolean("archived");

            return new Project(projectID, name, adminUserID, deadline, startDate, weeklyHours, weeklyDays, daysOff, archived);
        }
        catch (SQLException e){
            System.out.println("Failed to get project="+e.getMessage());
            return null;
        }
    }

    //JOHN
    public ArrayList<Subproject> getSubprojects(int projectID){
        String selectStatement = "SELECT sp.* FROM projects pr " +
                "JOIN subprojects sp using(project_id)" +
                "WHERE pr.project_id = ?";
        ArrayList<Subproject> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, projectID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int subprojectID = resultSet.getInt("subproject_id");
                String name = resultSet.getString("name");
                Subproject subproject = new Subproject(subprojectID, projectID, name);
                list.add(subproject);
            }
        }
        catch (SQLException e) {
            System.out.println("Failed to get subprojects="+e.getMessage());
            return null;
        }
        return list;
    }

    //JOHN
    public ArrayList<Task> getTasks(int projectID){
        String selectStatement = "SELECT ta.* FROM projects pr " +
                "JOIN subprojects sp using(project_id)" +
                "JOIN tasks ta using(subproject_id)" +
                "WHERE pr.project_id = ?";
        ArrayList<Task> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, projectID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int taskID = resultSet.getInt("task_id");
                int subprojectID = resultSet.getInt("subproject_id");
                String name = resultSet.getString("name");
                Task task = new Task(taskID, subprojectID, name);
                list.add(task);
            }
        }
        catch (SQLException e) {
            System.out.println("Failed to get tasks="+e.getMessage());
            return null;
        }
        return list;
    }

    //JOHN
    public ArrayList<Subtask> getSubtasks(int projectID){
        String selectStatement = "SELECT st.* FROM projects pr " +
                "JOIN subprojects sp using(project_id) " +
                "JOIN tasks ta using(subproject_id) " +
                "JOIN subtasks st using(task_id) " +
                "WHERE pr.project_id = ?";
        ArrayList<Subtask> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, projectID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int subtaskID = resultSet.getInt("subtask_id");
                int taskID = resultSet.getInt("task_id");
                String name = resultSet.getString("name");
                int timeEstimate = resultSet.getInt("time_estimation");
                Subtask subtask = new Subtask(subtaskID, taskID, name, timeEstimate);
                list.add(subtask);
            }
        }
        catch (SQLException e) {
            System.out.println("Failed to get subtasks="+e.getMessage());
            return null;
        }
        return list;
    }

}
