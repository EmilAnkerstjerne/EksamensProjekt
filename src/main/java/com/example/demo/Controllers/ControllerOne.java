package com.example.demo.Controllers;

import com.example.demo.Services.Login;
import com.example.demo.Services.ProfileService;
import com.example.demo.Services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ControllerOne {

    ProfileService profileService = new ProfileService();
    ProjectService projectService = new ProjectService();

    //Test of template
    @GetMapping("/test")
    public String test(){
        return "project-overview";
    }

    @GetMapping("/formTest")
    public String formTest(){
        System.out.println("Testen virkede");
        return "redirect:/startside";
    }

    //Test of profile-page
    @GetMapping("/profilT")
    public String profile2(@CookieValue(value = "user", defaultValue = "") String cookie, ModelMap modelMap){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        modelMap.addAttribute("profile", profileService.getProfile(profileID));
        return "profile-page";
    }

    //Testing
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


    //JOHN (Link to extended Insight page) TODO: To be moved
    @GetMapping("/udvidetInsight")
    public String extendedInfo(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request, ModelMap modelMap){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.hasAccess(profileID,projectID)){
            modelMap.addAttribute("project", projectService.getProject(projectID));
            return "test-udvidet-insight-page";
        }
        return "redirect:/startside";
    }

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
