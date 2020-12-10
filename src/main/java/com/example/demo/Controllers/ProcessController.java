package com.example.demo.Controllers;

import com.example.demo.Services.Login;
import com.example.demo.Services.ProcessService;
import com.example.demo.Services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ProcessController {
    //For process elements: change, delete, create

    private ProjectService projectService = new ProjectService();
    private ProcessService processService = new ProcessService();

    //JOHN
    @PostMapping("/projektNavn")
    public String changeProjectName(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            String name = request.getParameter("name");
            processService.changeProject(projectID, name);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }

    //JOHN
    @PostMapping("/nytUnderprojekt")
    public String newSubproject(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            String name = request.getParameter("name");
            processService.createSubproject(projectID, name);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }

    //JOHN
    @PostMapping("/aendreUnderprojekt")
    public String changeSubprojectName(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            String name = request.getParameter("name");
            int subprojectID = Integer.parseInt(request.getParameter("subprojectID"));
            processService.changeSubproject(profileID, subprojectID, name);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }

    //JOHN
    @GetMapping("/sletUnderprojekt")
    public String deleteSubproject(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            int subprojectID = Integer.parseInt(request.getParameter("subprojectID"));
            processService.deleteSubproject(profileID, subprojectID);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }

    //JOHN
    @PostMapping("/nyOpgave")
    public String newTask(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            String name = request.getParameter("name");
            int subprojectID = Integer.parseInt(request.getParameter("subprojectID"));
            processService.createTask(profileID, subprojectID, name);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }

    //JOHN
    @PostMapping("/aendreOpgave")
    public String changeTask(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            String name = request.getParameter("name");
            int taskID = Integer.parseInt(request.getParameter("taskID"));
            processService.changeTask(profileID, taskID, name);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }

    //JOHN
    @GetMapping("/sletOpgave")
    public String deleteTask(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            int taskID = Integer.parseInt(request.getParameter("taskID"));
            processService.deleteTask(profileID, taskID);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }

    //JOHN
    @PostMapping("/nyUnderopgave")
    public String newSubtask(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            String name = request.getParameter("name");
            int timeEstimation = Integer.parseInt(request.getParameter("timeEstimation"));
            int taskID = Integer.parseInt(request.getParameter("taskID"));
            processService.createSubtask(profileID, taskID, name, timeEstimation);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }

    //JOHN
    @PostMapping("/aendreUnderopgave")
    public String changeSubtask(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            String name = request.getParameter("name");
            int timeEstimation = Integer.parseInt(request.getParameter("timeEstimation"));
            int subtaskID = Integer.parseInt(request.getParameter("subtaskID"));
            processService.changeSubtask(profileID, subtaskID, name, timeEstimation);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }

    //JOHN
    @GetMapping("/sletUnderopgave")
    public String deleteSubtask(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            int subtaskID = Integer.parseInt(request.getParameter("subtaskID"));
            processService.deleteSubtask(profileID, subtaskID);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }

    //JOHN
    @PostMapping("/tilfojOpgSkill")
    public String addSubtaskSkill(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            String value = request.getParameter("skill");
            int subtaskID = Integer.parseInt(request.getParameter("subtaskID"));
            processService.createSubtaskSkill(profileID, subtaskID, value);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }

    //JOHN
    @GetMapping("/sletOpgSkill")
    public String deleteSubtaskSkill(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            int subtaskID = Integer.parseInt(request.getParameter("subtaskID"));
            int subtaskSkillID = Integer.parseInt(request.getParameter("skillID"));
            processService.deleteSubtaskSkill(profileID, subtaskID, subtaskSkillID);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }
}
