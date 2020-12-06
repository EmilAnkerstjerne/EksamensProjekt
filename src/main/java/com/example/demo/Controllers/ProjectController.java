package com.example.demo.Controllers;

import com.example.demo.Services.Login;
import com.example.demo.Services.ProfileService;
import com.example.demo.Services.ProjectService;
import com.example.demo.Services.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

//JOHN
@Controller
public class ProjectController {

    ProfileService profileService = new ProfileService();
    ProjectService projectService = new ProjectService();
    RegistrationService registrationService = new RegistrationService();

    //TEST of Project creation (JOHN)
    @GetMapping("/Pro")
    public String projectTest(){
        projectService.createProject(1);
        return "index";
    }

    //TODO: Overview/project
    @GetMapping("/startside")
    public String startside(ModelMap modelMap, @CookieValue(value = "user", defaultValue = "") String cookie){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        modelMap.addAttribute("user",profileID);
        projectService.getAdminProjects(profileID,modelMap,false);
        projectService.getOtherProjects(profileID, modelMap, false);
        modelMap.addAttribute("invitations", profileService.getInvitations(profileID));
        return "startside";
    }

    //TODO: Overview/Archive/project
    //JOHN
    @GetMapping("/projektArkiv")
    public String projectArchive(ModelMap modelMap, @CookieValue(value = "user", defaultValue = "") String cookie){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        modelMap.addAttribute("user",profileID);
        projectService.getAdminProjects(profileID,modelMap,true);
        projectService.getOtherProjects(profileID, modelMap, true);
        return "startside";
    }

    //TODO: summary/project
    //JOHN TODO: verify access to project
    @GetMapping("/projektOverblik")
    public String project(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request, ModelMap modelMap){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        modelMap.addAttribute("project", projectService.getProject(projectID));
        return "test-project-summary-page";
    }

    //TODO: Project
    //JOHN
    @GetMapping("/opretProjekt")
    public String createProject(@CookieValue(value = "user", defaultValue = "") String cookie){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = projectService.createProject(profileID);
        return "redirect:/projektOverblik?projectID=" + projectID;
    }

    //TODO: Overview/Project
    //JOHN (Just a bit)
    @GetMapping("/")
    public String index(@CookieValue(value = "user", defaultValue = "") String cookie){
        //TODO: add cookieverifier here
        //should redirect to login page if cookies is not verified.
        int profileID = Login.verifyCookie(cookie);
        if (profileID == -1){
            return "redirect:/login";
        }
        return "redirect:/startside";
    }

}
