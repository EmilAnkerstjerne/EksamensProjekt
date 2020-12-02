package com.example.demo.Repositories;

import com.example.demo.Models.Profile;

import java.sql.*;
import java.util.ArrayList;

public class ProfileRepository {

    private Connection connection;
    public ProfileRepository() {
    }

    public boolean setConnection(){
        boolean bres = false;
        String url = "jdbc:mysql://localhost:3306/skidegodt?serverTimezone=UTC";
        try{
            connection = DriverManager.getConnection(url, "SkideGodt", "SkideGodt");
            bres = true;
        }catch (SQLException e){
            System.out.println("No connection to server=" + e.getMessage());
        }
        return bres;
    }

    //EMIL
    public Profile getUserData(String username){
        String selectStatement = "SELECT * FROM users WHERE username = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int profileID = resultSet.getInt("user_id");
            String password = resultSet.getString("password");

            return new Profile(username, password, profileID);

        }catch (SQLException e){
            System.out.println(e);
            return new Profile("", "", -1);
        }
    }

    //EMIL
    public ArrayList<String> getAllProfiles(){
        //Creates an ArrayList of all usernames from DB
        String selectStatement = "SELECT username FROM users";
        ArrayList<String> profilesList = new ArrayList();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                String username = resultSet.getString("username");
                profilesList.add(username);
            }
        }catch(SQLException e){
            return null;
        }
        return profilesList;
    }


    //EMIL
    public ArrayList<String> getListOfCookies(){
        //Creates an arraylist of cookies from DB
        String selectStatement = "SELECT cookie_value FROM cookies";
        ArrayList<String> cookiesList = new ArrayList();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                String cookie = resultSet.getString("cookie_value");
                cookiesList.add(cookie);
            }
        }catch(SQLException e){
            return null;
        }
        return cookiesList;
    }

    public int getProfileIDFromCookie(String cookie){
        String selectStatement = "SELECT user_id FROM cookies WHERE cookie_value = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setString(1, cookie);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int profileID = resultSet.getInt("user_id");

            return profileID;

        }catch (SQLException e){
            System.out.println(e);
            return -1;
        }
    }

    public boolean insertCookie(int profileID, String cookie){
        String insertStatement = "INSERT INTO cookies (user_id, cookie_value)" + " values (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setInt(1, profileID);
            preparedStatement.setString(2, cookie);
            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            System.out.println(e);
            return false;
        }
    }
}
