package com.example.demo.Controllers;


import com.example.demo.Models.Profile;
import com.example.demo.Repositories.ProfileRepository;
import com.example.demo.Services.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ControllerOne {


    //TODO: create /login endpoint
    @GetMapping("/")
    public String login(){
        //TODO: add cookieverifier here
        //should redirect to login page if cookies is not verified.
        return "login-page";
    }
    //EMIL
    @GetMapping("/verLogin")
    public String verLogin(WebRequest dataFromForm, @CookieValue(value = "user", defaultValue = "") String cookie,
                        HttpServletResponse response){
        //Checks for cookie, if cookie exists - logging in
        if(Login.verifyCookie(cookie)){
            int profileID = Login.getProfileIDFromCookie(cookie);
            System.out.println(profileID);
            System.out.println("cookie verified, logging in");//return "logged in"-side
            return "redirect:/startside";
        }else{
            //UN and PW entered in HTML form
            String enteredUsername = dataFromForm.getParameter("username");
            String enteredPassword = dataFromForm.getParameter("password");

            //Checks if entered UN & PW matches DB UN & PW
            if(Login.verifyLogin(enteredUsername, enteredPassword)){
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


        if(Login.verifyCookie(cookie)){
            model.addAttribute("user",Login.getProfileIDFromCookie(cookie));
            return "startside";
        }

        return "redirect:/";
    }

}
