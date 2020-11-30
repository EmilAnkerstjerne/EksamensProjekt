package com.example.demo.Services;

import com.example.demo.Models.User;
import com.example.demo.Repositories.UserRepository;

public class Login {

    //EMIL
    public Login() {
    }

    //EMIL
    //Checks if entered UN & PW matches DB UN &PW
    public static boolean verifyLogin(String enteredUsername, String enteredPassword){
        //Checks if entered UN is in list of UN's
        //Checks if entered PW matches UN corresponding PW
        if(UserRepository.getListOfUsernames().contains(enteredUsername) &&
                UserRepository.getUserData(enteredUsername).getPassword().equals(enteredPassword)){
            return true;
        }else{
            return false;
        }
    }

    //EMIL
    //Checks if harvested cookie exists in DB list of UN/Cookies
    public static boolean verifyCookie(String cookie){
        //Temporary uses list of usernames. When done, uses list of cookies
        return UserRepository.getListOfUsernames().contains(cookie);
    }
}
