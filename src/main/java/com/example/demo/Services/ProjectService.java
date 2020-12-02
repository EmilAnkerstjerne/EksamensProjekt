package com.example.demo.Services;

import com.example.demo.Repositories.ProjectRepository;

import java.util.ArrayList;

public class ProjectService {

    ProjectRepository rep = new ProjectRepository();

    public ProjectService(){
        System.out.println("Database started successfully: " + rep.setConnection());

        //TEST
        ArrayList<String> list = rep.getAllUsers();
        System.out.println("Database data test: "+list);


    }

    public void projectTest(){
        System.out.println(rep.getProject(1));
    }
}
