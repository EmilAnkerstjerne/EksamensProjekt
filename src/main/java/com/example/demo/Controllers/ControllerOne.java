package com.example.demo.Controllers;


import com.example.demo.Models.User;
import com.example.demo.Repositories.UserRepository;
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



    //EMIL
    @GetMapping("/")
    public String index(WebRequest dataFromForm, @CookieValue(value = "username", defaultValue = "") String cookie,
                        HttpServletResponse response){
        //Checks for cookie, if cookie exists - logging in
        if(Login.verifyCookie(cookie)){
            System.out.println("cookie verified, logging in");//return "logged in"-side
        }else{
            //UN and PW entered in HTML form
            String enteredUsername = "Emil123"; //dataFromForm.getParameter("username");
            String enteredPassword = "1234"; //dataFromForm.getParameter("password");

            //Checks if entered UN & PW matches DB UN & PW
            if(Login.verifyLogin(enteredUsername, enteredPassword)){
                //Creates cookie to users browser, to remember UN
                Cookie userCook = new Cookie("username", enteredUsername);
                response.addCookie(userCook);
                System.out.println("Username and password verified, logging in");//return "loggedin"-side
            }else{
                System.out.println("Username or password is incorrect");//return "wrong pw or un"-side
            }
        }
        return "index";
    }

    @GetMapping("/startside")
    public String startside(Model model, @CookieValue(value = "username", defaultValue = "") String cookie){
        User user = new User();
        if(Login.verifyCookie(cookie)){
            user = UserRepository.getUserData(cookie);
        }
        model.addAttribute("user", user.getUsername());
        return "startside";
    }

}
