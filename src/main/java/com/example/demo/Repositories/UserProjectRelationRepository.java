package com.example.demo.Repositories;

import com.example.demo.Models.Employee;
import com.example.demo.Models.Invitation;
import com.example.demo.Models.Profile;

import java.sql.*;
import java.util.ArrayList;

//JOHN
public class UserProjectRelationRepository extends Repository{
    public UserProjectRelationRepository() {
        setConnection();
    }

    //JOHN
    public ArrayList<Invitation> getInvitations(int userID){
        String selectStatement = "SELECT ii.*, pr.name, uu.username FROM invitations ii " +
                "JOIN projects pr ON ii.project_id = pr.project_id " +
                "JOIN users uu ON pr.admin_user_id = uu.user_id " +
                "WHERE ii.user_id = ?";
        ArrayList<Invitation> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int invitationID = resultSet.getInt("invitation_id");
                int projectID = resultSet.getInt("project_id");
                String projectName = resultSet.getString("name");
                String adminUsername = resultSet.getString("username");
                Invitation invitation = new Invitation(invitationID, userID, projectID, projectName, adminUsername);
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
                "SELECT ii.*, pr.name, uu.username FROM invitations ii " +
                "JOIN projects pr ON ii.project_id = pr.project_id " +
                "JOIN users uu ON pr.admin_user_id = uu.user_id " +
                "WHERE ii.invitation_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, invitationID);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int userID = resultSet.getInt("user_id");
            int projectID = resultSet.getInt("project_id");
            String projectName = resultSet.getString("name");
            String adminUsername = resultSet.getString("username");

            return new Invitation(invitationID,userID,projectID, projectName, adminUsername);
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
    public boolean deleteInvitation(int invitationID, int profileID){
        String updateStatement =
                "DELETE FROM invitations " +
                "WHERE invitation_id = ? AND user_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
            preparedStatement.setInt(1, invitationID);
            preparedStatement.setInt(2, profileID);
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

    //JOHN
    public boolean deleteUserProjectRelation(int userID, int projectID){
        String updateStatement =
                "DELETE FROM user_project_relations " +
                "WHERE user_id = ? AND project_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, projectID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to delete user project relation="+e.getMessage());
            return false;
        }
    }

    //JOHN
    public ArrayList<Profile> getUserProfiles(int projectID){
        String selectStatement =
                "SELECT u.* FROM user_project_relations up " +
                "JOIN users u on up.user_id = u.user_id " +
                "WHERE project_id = ? " +
                "ORDER BY u.username";
        ArrayList<Profile> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, projectID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                int profileID = resultSet.getInt("user_id");

                Profile profile = new Profile(username, password, profileID);
                list.add(profile);
            }
        }
        catch (SQLException e){
            System.out.println("Failed to get user profiles="+e.getMessage());
            return null;
        }
        return list;
    }

    //JOHN
    public ArrayList<Employee> getEmployees(int projectID){
        String selectStatement =
                "SELECT * FROM employees " +
                "WHERE project_id = ? " +
                "ORDER BY name";
        ArrayList<Employee> list = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, projectID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int employeeID = resultSet.getInt("employee_id");
                String name = resultSet.getString("name");
                Employee employee = new Employee(employeeID, projectID, name);
                list.add(employee);
            }
        }
        catch (SQLException e){
            System.out.println("Failed to get employees="+e.getMessage());
            return null;
        }
        return list;
    }

    //JOHN
    public int createEmployee(int projectID, String name){
        String insertStatement = "INSERT INTO employees (project_id, name) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, projectID);
            preparedStatement.setString(2, name);
            preparedStatement.execute();

            return getLastCreatedID();
        }
        catch (SQLException e){
            System.out.println("Failed to create employee="+e.getMessage());
            return -1;
        }
    }

    //JOHN
    public boolean deleteEmployee(int employeeID){
        String updateStatement =
                "DELETE FROM employees " +
                "WHERE employee_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
            preparedStatement.setInt(1, employeeID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to delete employee="+e.getMessage());
            return false;
        }
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
