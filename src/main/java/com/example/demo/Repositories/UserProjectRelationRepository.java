package com.example.demo.Repositories;

import com.example.demo.Models.Invitation;

import java.sql.*;
import java.util.ArrayList;

//JOHN
public class UserProjectRelationRepository {
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
    public ArrayList<Invitation> getInvitations(int userID){
        String selectStatement = "SELECT * FROM invitations WHERE user_id = ?";
        ArrayList<Invitation> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int invitationID = resultSet.getInt("invitation_id");
                int projectID = resultSet.getInt("project_id");
                Invitation invitation = new Invitation(invitationID, userID, projectID);
                list.add(invitation);
            }
        }
        catch (SQLException e) {
            System.out.println("Failed to get invitations="+e.getMessage());
            return null;
        }
        return list;
    }

    //JOHN
    public Invitation getInvitation(int invitationID){
        String selectStatement =
                "SELECT * FROM invitations " +
                "WHERE invitation_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, invitationID);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int userID = resultSet.getInt("user_id");
            int projectID = resultSet.getInt("project_id");

            return new Invitation(invitationID,userID,projectID);
        }
        catch (SQLException e){
            System.out.println("Failed to get invitation="+e.getMessage());
            return null;
        }
    }

    //JOHN
    public boolean createInvitation(int userID, int projectID){
        String insertStatement = "INSERT INTO invitations (user_id, project_id) VALUES (?, ?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, projectID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to create invitation="+e.getMessage());
            return false;
        }
    }

    //JOHN
    public boolean deleteInvitation(int invitationID){
        String updateStatement =
                "DELETE FROM invitations " +
                "WHERE invitation_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
            preparedStatement.setInt(1, invitationID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to delete invitation="+e.getMessage());
            return false;
        }
    }

    //JOHN
    public boolean createUserProjectRelation(int userID, int projectID){
        String insertStatement = "INSERT INTO user_project_relations (user_id, project_id) VALUES (?, ?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, projectID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to create user project relation="+e.getMessage());
            return false;
        }
    }


}
