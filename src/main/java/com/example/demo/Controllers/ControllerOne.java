package com.example.demo.Controllers;


import com.example.demo.Models.Profile;
import com.example.demo.Repositories.ProfileRepository;
import com.example.demo.Services.Login;
import com.example.demo.Services.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


                return "index";
            }else{
                //TODO: ErrorMessage.
                return "redirect:/";
            }
        }
    }

    @GetMapping("/startside")
    public String startside(Model model, @CookieValue(value = "user", defaultValue = "") String cookie){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        model.addAttribute("user",profileID);
        return "startside";
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
}
