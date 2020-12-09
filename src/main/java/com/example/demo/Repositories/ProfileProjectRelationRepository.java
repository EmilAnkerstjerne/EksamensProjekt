package com.example.demo.Repositories;

import java.sql.*;

//JOHN
public class ProfileProjectRelationRepository extends Repository { //For utility methods used to identify relation between user_id and other elements
    public ProfileProjectRelationRepository() {
        setConnection();
    }

    //JOHN
    public boolean checkIfAdminOnProject(int userID, int projectID){
        String selectStatement =
                "SELECT * FROM projects " +
                "WHERE admin_user_id = ? AND project_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, projectID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e){
            System.out.println("Failed to check if admin on project="+e.getMessage());
        }
        return false;
    }

    //JOHN
    public boolean checkIfAdminOnSubproject(int userID, int subprojectID){
        String selectStatement =
                "SELECT * FROM projects " +
                        "JOIN subprojects using(project_id)" +
                        "WHERE admin_user_id = ? AND subproject_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, subprojectID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e){
            System.out.println("Failed to check if admin on subproject="+e.getMessage());
        }
        return false;
    }

    //JOHN
    public boolean checkIfAdminOnTask(int userID, int taskID){
        String selectStatement =
                "SELECT * FROM projects " +
                        "JOIN subprojects using(project_id) " +
                        "JOIN tasks using(subproject_id)" +
                        "WHERE admin_user_id = ? AND task_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, taskID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e){
            System.out.println("Failed to check if admin on task="+e.getMessage());
        }
        return false;
    }

    //JOHN
    public boolean checkIfAdminOnSubtask(int userID, int subtaskID){
        String selectStatement =
                "SELECT * FROM projects " +
                        "JOIN subprojects using(project_id) " +
                        "JOIN tasks using(subproject_id) " +
                        "JOIN subtasks using(task_id)" +
                        "WHERE admin_user_id = ? AND subtask_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, subtaskID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e){
            System.out.println("Failed to check if admin on subtask="+e.getMessage());
        }
        return false;
    }

    //JOHN
    public boolean checkIfAssociatedUserProject(int userID, int projectID){
        String selectStatement =
                "SELECT * FROM user_project_relations " +
                        "JOIN projects using(project_id) " +
                        "WHERE user_id = ? AND project_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, projectID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e){
            System.out.println("Failed to check if user is associated to project="+e.getMessage());
        }
        return false;
    }

    //JOHN TODO: Useless?
    public boolean checkIfAssociatedUserSubproject(int userID, int subprojectID){
        String selectStatement =
                "SELECT * FROM user_project_relations " +
                        "JOIN projects using(project_id) " +
                        "JOIN subprojects using(project_id)" +
                        "WHERE user_id = ? AND subproject_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, subprojectID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e){
            System.out.println("Failed to check if user is associated to subproject="+e.getMessage());
        }
        return false;
    }

    //JOHN TODO: Useless?
    public boolean checkIfAssociatedUserTask(int userID, int taskID){
        String selectStatement =
                "SELECT * FROM user_project_relations " +
                        "JOIN projects using(project_id) " +
                        "JOIN subprojects using(project_id) " +
                        "JOIN tasks using(subproject_id) " +
                        "WHERE user_id = ? AND task_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, taskID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e){
            System.out.println("Failed to check if user is associated to task="+e.getMessage());
        }
        return false;
    }

    //JOHN TODO: Useless?
    public boolean checkIfAssociatedUserSubtask(int userID, int subtaskID){
        String selectStatement =
                "SELECT * FROM user_project_relations " +
                        "JOIN projects using(project_id) " +
                        "JOIN subprojects using(project_id)" +
                        "JOIN tasks using(subproject_id) " +
                        "JOIN subtasks using(task_id)" +
                        "WHERE user_id = ? AND subtask_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, subtaskID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e){
            System.out.println("Failed to check if user is associated to subtask="+e.getMessage());
        }
        return false;
    }

    //JOHN
    public boolean checkIfInvited(int profileID, int projectID){
        String selectStatement = "SELECT * FROM invitations WHERE user_id = ? AND project_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, profileID);
            preparedStatement.setInt(2, projectID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return  true;
            }
        }
        catch (SQLException e){
            System.out.println("Failed to check if invited="+e.getMessage());
            //In case of failure: returns true as not to give misinformation about existing invitation
            return true;
        }
        return false;
    }

}
