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

    //TODO: Invitation/profile-project
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

    //TODO: Invitation/profile-project
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

    //TODO: Invitation/profile-project
    //JOHN
    @GetMapping("/brugerAdministration")
    public String administrateUsers(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request, ModelMap modelMap){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        //TODO: check if admin/user
        modelMap.addAttribute("users", profileService.getUserProfiles(projectID));
        modelMap.addAttribute("project", projectService.getProject(projectID));
        return "test-administer-users-page";
    }
}
