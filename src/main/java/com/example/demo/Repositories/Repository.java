package com.example.demo.Repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//EMIL
public class Repository {

    protected Connection connection; //Protected access modifier used to let only subclasses of this class use the attribute.

    public Connection setConnection(){
        String url = "jdbc:mysql://localhost:3306/skidegodt?serverTimezone=UTC";
        try{
            connection = DriverManager.getConnection(url, "SkideGodt", "SkideGodt");
        }catch (SQLException e){
            System.out.println("No connection to server=" + e.getMessage());
        }
        return connection;
    }
}
