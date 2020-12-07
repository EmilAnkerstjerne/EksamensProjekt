package com.example.demo.Controllers;

import com.example.demo.Services.Login;
import com.example.demo.Services.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerOne {

    ProfileService profileService = new ProfileService();

    //Test of template
    @GetMapping("/test")
    public String test(){
        return "project-overview";
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
}
