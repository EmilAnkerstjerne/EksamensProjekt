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

//JOHN
@Controller
public class ProjectController {

    ProfileService profileService = new ProfileService();
    ProjectService projectService = new ProjectService();

    //Tobias
    @GetMapping("/startside")
    public String startside(ModelMap modelMap, @CookieValue(value = "user", defaultValue = "") String cookie){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        modelMap.addAttribute("user",profileID);
        projectService.getAdminProjects(profileID, modelMap,false);
        projectService.getOtherProjects(profileID, modelMap, false);
        modelMap.addAttribute("invitations", profileService.getInvitations(profileID));
        return "project-overview";
    }

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
        modelMap.addAttribute("invitations", profileService.getInvitations(profileID));
        return "project-archive";
    }

    //Tobias
    @GetMapping("/projektOverblik")
    public String project(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request, ModelMap modelMap){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        //Checks if user has access to project
        if (projectService.hasAccess(profileID,projectID)){
            modelMap.addAttribute("invitations", profileService.getInvitations(profileID));
            modelMap.addAttribute("profile", profileService.getProfile(profileID));
            modelMap.addAttribute("project", projectService.getProject(projectID));
            modelMap.addAttribute("employeeSkills",projectService.getAllEmployeeSkills(projectID));

            return "project-summary-page";
        }
        return "redirect:/startside";
    }

    //Tobias
    @GetMapping("/opretProjekt")
    public String createProjektPage(@CookieValue(value = "user", defaultValue = "") String cookie, ModelMap modelMap){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        modelMap.addAttribute("invitations", profileService.getInvitations(profileID));
        modelMap.addAttribute("profile", profileService.getProfile(profileID));
        return "create-project";
    }

    //JOHN, TOBIAS
    @PostMapping("/opretProjektF")
    public String createProject(@CookieValue(value = "user", defaultValue = "") String cookie, ModelMap modelMap, WebRequest dataFromForm){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        String projectName = dataFromForm.getParameter("projectname");
        int projectID = projectService.createProject(profileID, projectName);
        projectService.getProject(projectID).setName(projectName);
        return "redirect:/projektOverblik?projectID=" + projectID;
    }

    //JOHN (Just a bit)
    @GetMapping("/")
    public String index(@CookieValue(value = "user", defaultValue = "") String cookie){
        int profileID = Login.verifyCookie(cookie);
        if (profileID == -1){
            return "redirect:/login";
        }
        return "redirect:/startside";
    }

    //JOHN, TOBIAS
    @GetMapping("/projektVedligeholdelse")
    public String projectMaintenance(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request, ModelMap modelMap){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        //Checks if user has access to project
        if (projectService.isAdmin(profileID,projectID)){
            modelMap.addAttribute("profile", profileService.getProfile(profileID));
            modelMap.addAttribute("project", projectService.getProject(projectID));
            modelMap.addAttribute("invitations", profileService.getInvitations(profileID));
            modelMap.addAttribute("employees", projectService.getEmployees(projectID));
            return "maintenance-page";
        }
        return "redirect:/startside";
    }

    //JOHN
    @GetMapping("/arkivStatus")
    public String archivedStatus(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.isAdmin(profileID,projectID)){
            boolean change = request.getParameter("status").equals("1");
            projectService.changeArchived(projectID, change);
        }
        return "redirect:/projektVedligeholdelse?projectID=" + projectID;
    }

}
