package com.example.demo.Controllers;


import com.example.demo.Models.Profile;
import com.example.demo.Repositories.ProfileRepository;
import com.example.demo.Services.Login;
import com.example.demo.Services.ProfileService;
import com.example.demo.Services.ProjectService;
import com.example.demo.Services.RegistrationService;
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

    //JOHN (Just a bit)
    //TODO: create /login endpoint
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
        ProjectService ser = new ProjectService();
        ser.getAdminProjects(profileID,modelMap,false);
        ser.getOtherProjects(profileID, modelMap, false);
        ProfileService ser2 = new ProfileService();
        modelMap.addAttribute("invitations", ser2.getInvitations(profileID));
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
        ProjectService ser = new ProjectService();
        ser.getAdminProjects(profileID,modelMap,true);
        ser.getOtherProjects(profileID, modelMap, true);
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
        ProjectService ser = new ProjectService();
        modelMap.addAttribute("project", ser.getProject(projectID));
        return "test-project-summary-page";
    }

    //JOHN
    @GetMapping("/opretProjekt")
    public String createProject(@CookieValue(value = "user", defaultValue = "") String cookie){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        ProjectService ser = new ProjectService();
        int projectID = ser.createProject(profileID);
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
        ProfileService ser = new ProfileService();
        int projectID = ser.getProjectIDFromInvitationID(invitationID);
        if (ser.deleteInvitation(invitationID, profileID)){
            ser.createUserProjectRelation(profileID,projectID);
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
        ProfileService ser = new ProfileService();
        ser.deleteInvitation(invitationID, profileID);
        return "redirect:/startside";
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
            RegistrationService reg = new RegistrationService();
            if (reg.checkIfUsernameIsTaken(username)){
                return "register-page";
            }
            reg.createProfile(username,password);
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
        ProfileService ser = new ProfileService();
        modelMap.addAttribute("profile", ser.getProfile(profileID));
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

        ProfileService ser = new ProfileService();

        if (ser.checkPassword(profileID,oldPassword)){
            ser.changePassword(profileID, password);
        }
        return "redirect:/profil";
    }

    //JOHN TODO: temporary
    @GetMapping("/logud")
    public String logout(@CookieValue(value = "user", defaultValue = "") String cookie, HttpServletResponse response){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        //Temporary: changes cookie on users side
        Cookie userCook = new Cookie("user", "");
        response.addCookie(userCook);
        return "redirect:/login";
    }
}
