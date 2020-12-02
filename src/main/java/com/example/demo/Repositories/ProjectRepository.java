package com.example.demo.Repositories;

import java.sql.*;
import java.util.ArrayList;

public class ProjectRepository {

    private Connection connection;

    public boolean setConnection(){
        boolean bres = false;
        String url = "jdbc:mysql://localhost:3306/skidegodt?serverTimezone=UTC";
        try{
            connection = DriverManager.getConnection(url,"SkideGodt","SkideGodt");
            bres = true;
        }
        catch (SQLException e){
            System.out.println("No connection to sever="+e.getMessage());
        }
        return bres;
    }

    //TEST
    public ArrayList<String> getAllUsers(){
        String selectStatement = "SELECT username FROM users;";
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

}
