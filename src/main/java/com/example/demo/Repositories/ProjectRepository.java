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

    public ArrayList<Project> getAdminProjects(int userID, boolean archived){
        String selectStatement =
                "SELECT * FROM projects " +
                "WHERE admin_user_id = ? AND archived = ?";
        ArrayList<Project> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setBoolean(2, archived);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int projectID = resultSet.getInt("projectID");
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

                list.add(new Project(projectID, name, adminUserID, deadline, startDate, weeklyHours, weeklyDays, daysOff, archived));
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
                "SELECT pr.* FROM user_project_relations up " +
                        "JOIN projects pr ON up.project_id = pr.project_id " +
                        "WHERE up.user_id = ? pr.archived = ?";
        ArrayList<Project> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setBoolean(2, archived);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int projectID = resultSet.getInt("projectID");
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

                list.add(new Project(projectID, name, adminUserID, deadline, startDate, weeklyHours, weeklyDays, daysOff, archived));
            }
        }
        catch (SQLException e){
            System.out.println("Failed to get invited to projects="+e.getMessage());
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
                "SELECT * FROM projects " +
                "WHERE project_id = ?";
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
    public boolean createProject(int adminUserID){
        String insertStatement = "INSERT INTO projects (admin_user_id) VALUES (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, adminUserID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to create project="+e.getMessage());
            return false;
        }
    }

    //JOHN
    public boolean createSubproject(int projectID, String name){
        String insertStatement = "INSERT INTO subprojects (project_id, name) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, projectID);
            preparedStatement.setString(2, name);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to create subproject="+e.getMessage());
            return false;
        }
    }

    //JOHN
    public boolean createTask(int subprojectID, String name){
        String insertStatement = "INSERT INTO tasks (subproject_id, name) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, subprojectID);
            preparedStatement.setString(2, name);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to create task="+e.getMessage());
            return false;
        }
    }

    //JOHN
    public boolean createSubtask(int taskID, String name, int timeEstimation){
        String insertStatement = "INSERT INTO tasks (subproject_id, name, time_estimation) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, taskID);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(2, timeEstimation);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to create subtask="+e.getMessage());
            return false;
        }
    }

    //JOHN TODO: Split to Essential information
    public boolean changeProject(int projectID, String name){
        String updateStatement =
                "UPDATE projects SET name = ? " +
                "WHERE project_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, projectID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to update project="+e.getMessage());
            return false;
        }
    }

    //JOHN
    public boolean changeEssentialInformation(int projectID, Date deadline, Date startDate, int weeklyHours, int weeklyDays, int daysOff, boolean archived){
        String updateStatement =
                "UPDATE projects SET deadline = ?, startdate = ?, weekly_hours = ?, weekly_days = ?, days_off = ?, archived = ? " +
                        "WHERE project_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
            preparedStatement.setDate(1, new java.sql.Date((deadline.getTime())));
            preparedStatement.setDate(2, new java.sql.Date((startDate.getTime())));
            preparedStatement.setInt(3, weeklyHours);
            preparedStatement.setInt(4, weeklyDays);
            preparedStatement.setInt(5,daysOff);
            preparedStatement.setBoolean(6, archived);
            preparedStatement.setInt(7, projectID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to update essential information="+e.getMessage());
            return false;
        }
    }

    //JOHN
    public boolean changeSubproject(int subprojectID, String name){
        String updateStatement =
                "UPDATE subprojects SET name = ? " +
                "WHERE subproject_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, subprojectID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to update subproject="+e.getMessage());
            return false;
        }
    }

    //JOHN
    public boolean changeTask(int taskID, String name){
        String updateStatement =
                "UPDATE tasks SET name = ? " +
                "WHERE task_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, taskID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to update task="+e.getMessage());
            return false;
        }
    }

    //JOHN
    public boolean changeSubtask(int subtaskID, String name, int timeEstimation){
        String updateStatement =
                "UPDATE subtasks SET name = ?, time_estimation = ? " +
                "WHERE subtask_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2,timeEstimation);
            preparedStatement.setInt(3, subtaskID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to update subtask="+e.getMessage());
            return false;
        }
    }

    //JOHN
    public boolean deleteProject(int projectID){
        String deleteStatement =
                "DELETE FROM projects " +
                "WHERE project_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement);
            preparedStatement.setInt(1, projectID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e) {
            System.out.println("Failed to delete project="+e.getMessage());
            return false;
        }
    }

    //JOHN
    public boolean deleteSubproject(int subprojectID){
        String deleteStatement =
                "DELETE FROM subprojects " +
                "WHERE subproject_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement);
            preparedStatement.setInt(1, subprojectID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e) {
            System.out.println("Failed to delete subproject="+e.getMessage());
            return false;
        }
    }

    //JOHN
    public boolean deleteTask(int taskID){
        String deleteStatement =
                "DELETE FROM tasks " +
                "WHERE task_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement);
            preparedStatement.setInt(1, taskID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e) {
            System.out.println("Failed to delete task="+e.getMessage());
            return false;
        }
    }

    //JOHN
    public boolean deleteSubtask(int subtaskID){
        String deleteStatement =
                "DELETE FROM subtasks " +
                "WHERE subtask_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement);
            preparedStatement.setInt(1, subtaskID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e) {
            System.out.println("Failed to delete subtask="+e.getMessage());
            return false;
        }
    }
}
