package com.example.demo.Services;

import com.example.demo.Repositories.ProjectRepository;

public class ProjectService {

    ProjectRepository rep = new ProjectRepository();

    public ProjectService(){
        System.out.println("Database started: " + rep.setConnection());
    }
}
