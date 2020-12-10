package com.example.demo.Repositories;

import java.sql.*;
import java.util.Date;

//JOHN
public class ProcessRepository extends Repository{ //Create, edit, delete process elements
    public ProcessRepository() {
        setConnection();
    }


    //JOHN, TOBIAS
    public int createProject(int adminUserID, String projectName){
        String insertStatement = "INSERT INTO projects (admin_user_id, name) VALUES (?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, adminUserID);
            preparedStatement.setString(2,projectName);
            preparedStatement.execute();
            return getLastCreatedID();
        }
        catch (SQLException e){
            System.out.println("Failed to create project="+e.getMessage());
            return -1;
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
        String insertStatement = "INSERT INTO subtasks (task_id, name, time_estimation) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, taskID);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, timeEstimation);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to create subtask="+e.getMessage());
            return false;
        }
    }

    //JOHN
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
    public boolean changeEssentialInformation(int projectID, Date deadline, Date startDate, int weeklyHours, int weeklyDays, int daysOff){
        String updateStatement =
                "UPDATE projects SET deadline = ?, startdate = ?, weekly_hours = ?, weekly_days = ?, days_off = ? " +
                        "WHERE project_id = ?";
        try{
            java.sql.Date newDeadline;
            java.sql.Date newStartDate;
            if (deadline == null){
                newDeadline = null;
            }
            else {
                newDeadline = new java.sql.Date((deadline.getTime()));
            }

            if (startDate == null){
                newStartDate = null;
            }
            else {
                newStartDate = new java.sql.Date((startDate.getTime()));
            }

            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
            preparedStatement.setDate(1, newDeadline);
            preparedStatement.setDate(2, newStartDate);
            preparedStatement.setInt(3, weeklyHours);
            preparedStatement.setInt(4, weeklyDays);
            preparedStatement.setInt(5,daysOff);
            preparedStatement.setInt(6, projectID);
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

    //JOHN
    public boolean checkAdminSubprojectRelation(int adminID, int subprojectID){
        String selectStatement =
                "SELECT s.* FROM projects pr " +
                "JOIN subprojects s on pr.project_id = s.project_id " +
                "WHERE admin_user_id = ? AND subproject_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, adminID);
            preparedStatement.setInt(2, subprojectID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e){
            System.out.println("Failed to check admin subproject relation="+e.getMessage());
        }
        return false;
    }

    //JOHN
    public boolean checkAdminTaskRelation(int adminID, int taskID){
        String selectStatement =
                "SELECT t.* FROM projects pr " +
                        "JOIN subprojects s on pr.project_id = s.project_id " +
                        "JOIN tasks t on s.subproject_id = t.subproject_id " +
                        "WHERE admin_user_id = ? AND task_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, adminID);
            preparedStatement.setInt(2, taskID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e){
            System.out.println("Failed to check admin task relation="+e.getMessage());
        }
        return false;
    }

    //JOHN
    public boolean checkAdminSubtaskRelation(int adminID, int subtaskID){
        String selectStatement =
                "SELECT t.* FROM projects pr " +
                        "JOIN subprojects s on pr.project_id = s.project_id " +
                        "JOIN tasks t on s.subproject_id = t.subproject_id " +
                        "JOIN subtasks s2 on t.task_id = s2.task_id " +
                        "WHERE admin_user_id = ? AND subtask_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, adminID);
            preparedStatement.setInt(2, subtaskID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e){
            System.out.println("Failed to check admin subtask relation="+e.getMessage());
        }
        return false;
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
