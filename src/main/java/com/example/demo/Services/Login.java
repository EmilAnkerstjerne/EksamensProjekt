package com.example.demo.Services;

import com.example.demo.Repositories.UserRepository;
import org.apache.catalina.User;

import java.util.Random;

public class Login {



    //EMIL
    public Login() {
    }

    //EMIL
    //Checks if entered UN & PW matches DB UN &PW
    public static boolean verifyLogin(String enteredUsername, String enteredPassword){
        UserRepository userRepository = new UserRepository();
        //Checks if entered UN is in list of UN's
        //Checks if entered PW matches UN corresponding PW
        return userRepository.getListOfUsernames().contains(enteredUsername) &&
                userRepository.getUserData(enteredUsername).getPassword().equals(enteredPassword);
    }

    //EMIL
    //Checks if harvested cookie exists in DB list of UN/Cookies
    public static boolean verifyCookie(String cookie){
        UserRepository userRepository = new UserRepository();
        //Temporary uses list of usernames. When done, uses list of cookies
        return userRepository.getListOfUsernames().contains(cookie);
    }

    public static String generateCookie(int cookieSize){
        String characters = "0123456789aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ"; //String of characters used to generate a random cookie
        String generatedCookie = ""; //Empty string to save random generated characters in

        //For-loop saves random character from 'characters', adds to 'generatedCookie'
        for(int i = 0; i < cookieSize; i++){
            generatedCookie += characters.charAt(new Random().nextInt(characters.length()));
        }
        return generatedCookie;
    }
}
