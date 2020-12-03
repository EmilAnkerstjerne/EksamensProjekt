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
    public Profile getProfileDataFromUsername(String username){
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
            System.out.println("ProfileDataGet: " + e);
            return new Profile("", "", -1);
        }
    }

    //Emil
    public Profile getProfileDataFromProfileID(int profileID){
        String selectStatement = "SELECT * FROM users WHERE user_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, profileID);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");

            return new Profile(username, password, profileID);

        }catch (SQLException e){
            System.out.println("ProfileDataGet: " + e);
            return new Profile("", "", -1);
        }
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
            System.out.println("CookieGet: " + e);
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
            System.out.println("CookieInsert: " + e);
            return false;
        }
    }
}
