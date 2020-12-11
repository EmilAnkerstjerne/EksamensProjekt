package com.example.demo.Controllers;

import com.example.demo.Services.Login;
import com.example.demo.Services.ProfileService;
import com.example.demo.Services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ControllerOne {

    ProfileService profileService = new ProfileService();
    ProjectService projectService = new ProjectService();

    //Test of template TODO: REMOVE
    @GetMapping("/test")
    public String test(){
        return "project-overview";
    }

    //Test page TODO: REMOVE
    @GetMapping("/test2")
    public String test2(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request, ModelMap modelMap){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        modelMap.addAttribute("project", projectService.getProject(1));
        return "test-project-summary-page";
    }

    //Test TODO: REMOVE
    @GetMapping("/formTest")
    public String formTest(){
        System.out.println("Testen virkede");
        return "redirect:/startside";
    }

    //Test of profile-page TODO: REMOVE
    @GetMapping("/profilT")
    public String profile2(@CookieValue(value = "user", defaultValue = "") String cookie, ModelMap modelMap){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        modelMap.addAttribute("profile", profileService.getProfile(profileID));
        return "profile-page";
    }

    //Testing TODO: REMOVE
    @GetMapping("/startside2")
    public String startsideTwo(ModelMap modelMap, @CookieValue(value = "user", defaultValue = "") String cookie){
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
}
