package com.example.demo.Controllers;

import com.example.demo.Services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectController {

    ProjectService ser = new ProjectService();

    //TEST of Project creation
    @GetMapping("/Pro")
    public String projectTest(){
        ser.projectTest();
        return "index";
    }

}
