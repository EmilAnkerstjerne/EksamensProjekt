package com.example.demo.Repositories;

import com.example.demo.Models.CookieModel;
import com.example.demo.Models.Profile;

import java.sql.*;
import java.util.ArrayList;

public class ProfileRepository extends Repository{
    public ProfileRepository() {
        setConnection();
    }

    //EMIL
    public Profile getProfileData(String username){
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
    public Profile getProfileData(int profileID){
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

    public ArrayList<CookieModel> getAllCookies(){
        String selectStatement = "SELECT * FROM cookies";
        ArrayList<CookieModel> cookies = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                cookies.add(new CookieModel(resultSet.getInt("cookie_id"),
                        resultSet.getString("cookie_value"),
                        resultSet.getDate("datetime")));
            }

            return cookies;

        }catch (SQLException e){
            System.out.println("CookieGetAll: " + e);
            return null;
        }
    }

    //EMIL
    public int getProfileIDFromCookie(String cookie){
        String selectStatement = "SELECT user_id FROM cookies WHERE cookie_value = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setString(1, cookie);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return resultSet.getInt("user_id");
            }
        }catch (SQLException e){
            System.out.println("Failed to get profileID from cookie=" + e);
        }
        return -1;
    }

    //EMIL
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

    //EMIL
    public boolean deleteCookie(String cookieValue){
        String insertStatement = "DELETE FROM cookies WHERE cookie_value = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setString(1, cookieValue);
            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            System.out.println("CookieDelete: " + e);
            return false;
        }
    }

    //JOHN
    public boolean checkUsername(String username){
        String selectStatement =
                "SELECT * FROM users " +
                "WHERE username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        }
        catch (SQLException e){
            System.out.println("Failed to check username="+e.getMessage());
            return true;
        }
    }

    //JOHN
    public int createProfile(String username, String password){
        String insertStatement = "INSERT INTO users (username, password) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.execute();

            return getLastCreatedID();
        }
        catch (SQLException e){
            System.out.println("Failed to create profile="+e.getMessage());
            return -1;
        }
    }

    //JOHN
    public boolean changePassword(int profileID, String password){
        String updateStatement =
                "UPDATE users SET password = ? " +
                "WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatement);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, profileID);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Failed to update password="+e.getMessage());
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
