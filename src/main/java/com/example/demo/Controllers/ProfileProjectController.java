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
public class ProfileProjectController {

    ProfileService profileService = new ProfileService();
    ProjectService projectService = new ProjectService();

    //JOHN
    @GetMapping("/acceptInvitation")
    public String acceptInvitation(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int invitationID = Integer.parseInt(request.getParameter("invID"));
        int projectID = profileService.getProjectIDFromInvitationID(invitationID);
        if (profileService.deleteInvitation(invitationID, profileID)){
            profileService.createUserProjectRelation(profileID,projectID);
        }
        return "redirect:/startside";
    }

    //JOHN
    @GetMapping("/declineInvitation")
    public String declineInvitation(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int invitationID = Integer.parseInt(request.getParameter("invID"));
        profileService.deleteInvitation(invitationID, profileID);
        return "redirect:/startside";
    }

    //JOHN
    @GetMapping("/brugerAdministration")
    public String administrateUsers(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request, ModelMap modelMap){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        //Checks if user has access to project TODO: Decide if user or only admin
        if (projectService.hasAccess(profileID,projectID)){
            modelMap.addAttribute("users", profileService.getUserProfiles(projectID));
            modelMap.addAttribute("project", projectService.getProject(projectID));
            return "test-administer-users-page";
        }
        return "redirect:/startside";
    }

    //JOHN
    @GetMapping("/tilfojBruger")
    public String addUser(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        //Other user/external
        String username = request.getParameter("username");
        int userID = profileService.getProfileData(username).getProfileID();
        int projectID = Integer.parseInt(request.getParameter("projectID"));

        //Check if profileID is NOT admin in project
        if (!projectService.isAdmin(profileID,projectID)){
            return "redirect:/startside";
        }
        //Checks if there are any conflicting relations
        else if(projectService.canBeAdded(userID,projectID)){
            profileService.createInvitation(userID,projectID);
        }
        return "redirect:/brugerAdministration?projectID=" + projectID;
    }
}
