package com.example.demo.Repositories;

import com.example.demo.Models.User;

import java.util.ArrayList;

public class UserRepository {

    public UserRepository() {
    }

    //EMIL
    public static User getUserData(String username){
        String passwordFromDB = "1234"; //SELECT PASSWORD FROM "USERNAME"

        return new User(username, passwordFromDB, 1);
    }

    //EMIL
    public static ArrayList getListOfUsernames(){
        //Creates an ArrayList of all usernames from DB
        //SELECT * FROM USERNAMES
        ArrayList<String> usernames = new ArrayList();

        usernames.add("Emil123");

        return usernames;
    }


    //EMIL
    //Maybe needed, maybe not
    public static ArrayList getListOfCookies(){
        ArrayList<String> cookies = new ArrayList<>();
        return cookies;
    }
}
