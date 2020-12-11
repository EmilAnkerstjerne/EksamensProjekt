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
public class InsightController {

    ProjectService projectService = new ProjectService();
    ProfileService profileService = new ProfileService();

    //JOHN (Link to extended Insight page)
    @GetMapping("/udvidetInsight")
    public String extendedInfo(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request, ModelMap modelMap){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));
        if (projectService.hasAccess(profileID,projectID)){
            modelMap.addAttribute("project", projectService.getProject(projectID));
            modelMap.addAttribute("profile", profileService.getProfile(profileID));
            return "test-udvidet-insight-page";
        }
        return "redirect:/startside";
    }

    //JOHN
    @PostMapping("/grundoplysninger")
    public String essentialInformation(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest request){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        int projectID = Integer.parseInt(request.getParameter("projectID"));

        if (projectService.isAdmin(profileID,projectID)){
            int weeklyHours = Integer.parseInt(request.getParameter("weeklyHours"));
            int weeklyDays = Integer.parseInt(request.getParameter("weeklyDays"));
            int daysOff = Integer.parseInt(request.getParameter("daysOff"));

            boolean startDateRemove = request.getParameter("removeStartDate") != null;
            boolean deadlineRemove = request.getParameter("removeDeadline") != null;

            String startDate = request.getParameter("startDate");
            String deadline = request.getParameter("deadline");
            if (startDateRemove){
                startDate = null;
            }

            if (deadlineRemove){
                deadline = null;
            }

            projectService.changeEssentialInformation(projectID, deadline, startDate, weeklyHours, weeklyDays, daysOff);
        }

        return "redirect:/udvidetInsight?projectID=" + projectID;
    }
}
