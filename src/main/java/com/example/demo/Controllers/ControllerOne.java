package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerOne {

    //Test of template
    @GetMapping("/test")
    public String test(){
        return "project-overview";
    }
}
