package com.example.demo.Services;

import com.example.demo.Models.Profile;
import com.example.demo.Repositories.ProfileRepository;

import java.util.Random;

public class Login {



    //EMIL
    public Login() {

    }



    //EMIL
    //Checks if entered UN & PW matches DB UN &PW
    public static boolean verifyLogin(String enteredUsername, String enteredPassword){
        ProfileRepository profileRepository = new ProfileRepository();
        profileRepository.setConnection();

        //Checks if entered UN is in list of UN's
        //Checks if entered PW matches UN corresponding PW
        return profileRepository.getAllProfiles().contains(enteredUsername) &&
                profileRepository.getUserData(enteredUsername).getPassword().equals(enteredPassword);
    }

    //EMIL
    //Checks if harvested cookie exists in DB list of UN/Cookies
    public static boolean verifyCookie(String cookie){
        ProfileRepository profileRepository = new ProfileRepository();
        profileRepository.setConnection();
        return profileRepository.getListOfCookies().contains(cookie);
    }

    //EMIL
    //Gets a profileID from the harvested cookie
    public static int getProfileIDFromCookie(String cookie){
        ProfileRepository profileRepository = new ProfileRepository();
        profileRepository.setConnection();
        return profileRepository.getProfileIDFromCookie(cookie);
    }

    //EMIL&JOHN
    //Generates random cookie and inserts it to DB
    public static String generateCookie(int cookieSize, String username){
        ProfileRepository profileRepository = new ProfileRepository();
        profileRepository.setConnection();
        String characters = "0123456789aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ"; //String of characters used to generate a random cookie
        String generatedCookie = ""; //Empty string to save random generated characters in

        //For-loop saves random character from 'characters', adds to 'generatedCookie'
        for(int i = 0; i < cookieSize; i++){
            generatedCookie += characters.charAt(new Random().nextInt(characters.length()));
        }

        profileRepository.insertCookie(profileRepository.getUserData(username).getProfileID(), generatedCookie);

        return generatedCookie;
    }
}
