package com.example.demo.Repositories;

import java.sql.*;

//JOHN
public class ProfileProjectRelationRepository { //For utility methods used to identify relation between user_id and other elements

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


    //JOHN
    public boolean checkIfAdminOnProject(int userID, int projectID){
        String selectStatement = "SELECT * FROM projects WHERE admin_user_id = ? AND project_id = ?";
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
            System.out.println("Failed to check if admin="+e.getMessage());
        }
        return false;
    }

    //JOHN
    public boolean checkIfAssociatedUser(int userID, int projectID){
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

}
