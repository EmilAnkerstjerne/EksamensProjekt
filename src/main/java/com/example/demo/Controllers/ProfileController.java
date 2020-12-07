package com.example.demo.Controllers;

import com.example.demo.Services.Login;
import com.example.demo.Services.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ProfileController {

    ProfileService profileService = new ProfileService();

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
    @GetMapping("/profil")
    public String profile(@CookieValue(value = "user", defaultValue = "") String cookie, ModelMap modelMap){
        int profileID = Login.verifyCookie(cookie);
        if(profileID == -1){
            return "redirect:/login";
        }
        modelMap.addAttribute("profile", profileService.getProfile(profileID));
        modelMap.addAttribute("invitations", profileService.getInvitations(profileID));
        return "profile-page";
    }
}
