package com.example.demo.Controllers;


import com.example.demo.Models.Profile;
import com.example.demo.Repositories.ProfileRepository;
import com.example.demo.Services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ControllerOne {

    ProfileService profileService = new ProfileService();
    ProjectService projectService = new ProjectService();
    RegistrationService registrationService = new RegistrationService();

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
    //EMIL
    @PostMapping("/verLogin")
    public String verLogin(WebRequest dataFromForm, @CookieValue(value = "user", defaultValue = "") String cookie,
                        HttpServletResponse response){
        //Checks for cookie, if cookie exists - logging in
        int profileID = Login.verifyCookie(cookie) ;
        if( profileID > 0){
            System.out.println(profileID);
            System.out.println("cookie verified, logging in");//return "logged in"-side
            return "redirect:/startside";
        }else{
            //UN and PW entered in HTML form
            String enteredUsername = dataFromForm.getParameter("username");
            String enteredPassword = dataFromForm.getParameter("password");

            //Checks if entered UN & PW matches DB UN & PW
            if(Login.verifyLogin(enteredUsername, enteredPassword) > 0){
                //Creates cookie to users browser
                Cookie userCook = new Cookie("user", Login.generateCookie(10, enteredUsername));
                response.addCookie(userCook);


                return "redirect:/startside";
            }else{
                //TODO: ErrorMessage.
                return "redirect:/";
            }
        }
    }

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
        //TODO: check if admin/user
        modelMap.addAttribute("users", profileService.getUserProfiles(projectID));
        modelMap.addAttribute("project", projectService.getProject(projectID));
        return "test-administer-users-page";
    }

    //JOHN
    @GetMapping("/login")
    public String login(@CookieValue(value = "user", defaultValue = "") String cookie){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "login-page";
        }
        return "redirect:/startside";
    }

    //JOHN
    @GetMapping("/opret")
    public String registration(@CookieValue(value = "user", defaultValue = "") String cookie){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "register-page";
        }
        return "redirect:/startside";
    }

    //JOHN
    @PostMapping("/verRegistration")
    public String registration(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest dataFromForm, HttpServletResponse response){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            String username = dataFromForm.getParameter("username");
            String password = dataFromForm.getParameter("password");
            if (registrationService.checkIfUsernameIsTaken(username)){
                return "register-page";
            }
            registrationService.createProfile(username,password);
            Cookie userCook = new Cookie("user", Login.generateCookie(10, username));
            response.addCookie(userCook);
            return "redirect:/startside";
        }

        return "redirect:/startside";
    }

    //JOHN
    @GetMapping("/profil")
    public String profile(@CookieValue(value = "user", defaultValue = "") String cookie, ModelMap modelMap){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        modelMap.addAttribute("profile", profileService.getProfile(profileID));
        return "test-profile-page";
    }

    //JOHN
    @PostMapping("/updatePassword")
    public String savePassword(@CookieValue(value = "user", defaultValue = "") String cookie, WebRequest dataFromForm){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        String oldPassword = dataFromForm.getParameter("old-password");
        String password = dataFromForm.getParameter("new-password");

        if (profileService.checkPassword(profileID,oldPassword)){
            profileService.changePassword(profileID, password);
        }
        return "redirect:/profil";
    }

    //JOHN
    @GetMapping("/logud")
    public String logout(@CookieValue(value = "user", defaultValue = "") String cookie, HttpServletResponse response){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        CookieService.deleteCookie(cookie);
        return "redirect:/login";
    }
}
