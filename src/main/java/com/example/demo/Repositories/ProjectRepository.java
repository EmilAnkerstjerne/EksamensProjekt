package com.example.demo.Repositories;

import com.example.demo.Models.Project;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public Project getProject(int projectID){
        String selectStatement = "SELECT * FROM projects WHERE project_id = ?";
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

}
