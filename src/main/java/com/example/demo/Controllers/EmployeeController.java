package com.example.demo.Controllers;

import com.example.demo.Services.Login;
import com.example.demo.Services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class EmployeeController {

    ProjectService projectService = new ProjectService();

    //JOHN
    @PostMapping("/tilfojMedarbejder")
    public String addEmployee(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));

        if (projectService.isAdmin(profileID,projectID)){
            String name = request.getParameter("name");
            projectService.addEmployee(projectID, name);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }

    //JOHN
    @GetMapping("/fjernMedarbejder")
    public String removeEmployee(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            int employeeID = Integer.parseInt(request.getParameter("employeeID"));
            projectService.deleteEmployee(projectID, employeeID);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }

    //JOHN
    @PostMapping("/tilfojMedSkill")
    public String addEmpSkill(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            int employeeID = Integer.parseInt(request.getParameter("employeeID"));
            String value = request.getParameter("skill");
            projectService.createEmployeeSkill(employeeID, value);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }

    //JOHN
    @GetMapping("/fjernMedSkill")
    public String removeEmpSkill(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            int employeeSkillID = Integer.parseInt(request.getParameter("employeeSkillID"));
            projectService.removeEmployeeSkill(employeeSkillID);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }
}
