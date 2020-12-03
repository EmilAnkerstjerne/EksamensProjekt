package com.example.demo.Services;

import com.example.demo.Models.Profile;
import com.example.demo.Repositories.ProfileRepository;

import java.util.Random;

public class Login {



    //EMIL
    public Login() {

    }



    //EMIL
    //Checks if entered UN & PW matches DB UN &PW. RETURNS the profileID if verified
    //verifyLogin(UN, PW) > 0 //Login verified
    //verifyLogin(UN, PW) = -1 //Login not verified
    public static int verifyLogin(String enteredUsername, String enteredPassword){
        ProfileRepository profileRepository = new ProfileRepository();
        Profile profile = profileRepository.getProfileDataFromUsername(enteredUsername);
        //Checks if entered UN is in list of UN's
        //Checks if entered PW matches UN corresponding PW
        if(profile.getProfileID() > 0 && profile.getPassword().equals(enteredPassword)){
            return profile.getProfileID();
        }else{
            return -1;
        }
    }

    //EMIL
    //Checks if harvested cookie exists in DB list of UN/Cookies
    //Returns -1 if cookies is not fount, returns profileID if cookie is found
    //verifyCookie(cookie) > 0 //Cookie verified
    //verifyCookie(cookie) = -1 //Cookie not verified
    public static int verifyCookie(String cookie){
        ProfileRepository profileRepository = new ProfileRepository();
        return profileRepository.getProfileIDFromCookie(cookie);
    }

    //EMIL&JOHN
    //Generates random cookie and inserts it to DB
    public static String generateCookie(int cookieSize, String username){
        ProfileRepository profileRepository = new ProfileRepository();
        String characters = "0123456789aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ"; //String of characters used to generate a random cookie
        String generatedCookie = ""; //Empty string to save random generated characters in

        //For-loop saves random character from 'characters', adds to 'generatedCookie'
        for(int i = 0; i < cookieSize; i++){
            generatedCookie += characters.charAt(new Random().nextInt(characters.length()));
        }

        profileRepository.insertCookie(profileRepository.getProfileDataFromUsername(username).getProfileID(), generatedCookie);

        return generatedCookie;
    }
}
