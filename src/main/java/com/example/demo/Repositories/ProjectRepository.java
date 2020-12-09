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

    public ProjectRepository() {
        setConnection();
    }

    public boolean setConnection(){
        String url = "jdbc:mysql://localhost:3306/skidegodt?serverTimezone=UTC";
        try{
            connection = DriverManager.getConnection(url,"SkideGodt","SkideGodt");
            return true;
        }
        catch (SQLException e){
            System.out.println("No connection to sever="+e.getMessage());
            return false;
        }
    }

    //JOHN
    public ArrayList<Project> getAdminProjects(int userID, boolean archived){
        String selectStatement =
                "SELECT pr.*, count(employee_id) as count FROM projects pr " +
                "LEFT JOIN employees em using(project_id) " +
                "WHERE admin_user_id = ? AND archived = ? " +
                "GROUP BY project_id " +
                        "ORDER BY pr.project_id DESC";
        ArrayList<Project> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setBoolean(2, archived);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int projectID = resultSet.getInt("project_id");
                String name = resultSet.getString("name");
                int adminUserID  = resultSet.getInt("admin_user_id");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
                int employees = resultSet.getInt("count");

                list.add(new Project(projectID, name, adminUserID, deadline, startDate, weeklyHours, weeklyDays, daysOff, archived, employees));
            }
        }
        catch (SQLException e){
            System.out.println("Failed to get admin projects="+e.getMessage());
            return null;
        }
        return list;
    }

    //JOHN TODO: Rename (Relates to non-admin projects)
    public ArrayList<Project> getOtherProjects(int userID, boolean archived){
        String selectStatement =
                "SELECT pr.*, count(employee_id) as count FROM user_project_relations up " +
                        "JOIN projects pr ON up.project_id = pr.project_id " +
                        "LEFT JOIN employees em ON pr.project_id = em.project_id " +
                        "WHERE up.user_id = ? AND pr.archived = ? " +
                        "GROUP BY project_id " +
                        "ORDER BY pr.project_id DESC";
        ArrayList<Project> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setBoolean(2, archived);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int projectID = resultSet.getInt("project_id");
                String name = resultSet.getString("name");
                int adminUserID  = resultSet.getInt("admin_user_id");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
                int employees = resultSet.getInt("count");

                list.add(new Project(projectID, name, adminUserID, deadline, startDate, weeklyHours, weeklyDays, daysOff, archived, employees));
            }
        }
        catch (SQLException e){
            System.out.println("Failed to get invited to/other projects="+e.getMessage());
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
        String selectStatement =
                "SELECT pr.*, count(employee_id) as count FROM projects pr " +
                        "JOIN employees em using(project_id)" +
                "WHERE project_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, projectID);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            String name = resultSet.getString("name");
            int adminUserID  = resultSet.getInt("admin_user_id");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
            int employees = resultSet.getInt("count");

            return new Project(projectID, name, adminUserID, deadline, startDate, weeklyHours, weeklyDays, daysOff, archived, employees);
        }
        catch (SQLException e){
            System.out.println("Failed to get project="+e.getMessage());
            return null;
        }
    }

    //JOHN
    public ArrayList<Subproject> getSubprojects(int projectID){
        String selectStatement =
                "SELECT sp.* FROM projects pr " +
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
        String selectStatement =
                "SELECT ta.* FROM projects pr " +
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
        String selectStatement =
                "SELECT st.* FROM projects pr " +
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

    //JOHN
    public int getLastCreatedID(){ //Returns AI ID of last added row (Utility method)
        String selectStatement = "SELECT last_insert_id()";
        int res = -1;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            res = resultSet.getInt("last_insert_id()");
        }
        catch (SQLException e){
            System.out.println("last_insert_id() error="+e.getMessage());
        }
        return res;
    }
}
