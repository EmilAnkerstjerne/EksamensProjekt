package com.example.demo.Services;

import com.example.demo.Models.CookieModel;
import com.example.demo.Repositories.ProfileRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class CookieService {

    static ProfileRepository profileRepository = new ProfileRepository();

    //Emil
    public CookieService() {
    }

    public static boolean deleteOldCookies(){
        ArrayList<CookieModel> cookies = profileRepository.getAllCookies();

        int maxDaysOld = 14; //Change this to change the max age of a cookie.
        Date cookieCreatedAt;
        Date now = new Date(System.currentTimeMillis());
        long daysBetween;

        for (CookieModel cookie: cookies) {
            cookieCreatedAt = cookie.getCreatedAt();
            daysBetween = ((now.getTime()-cookieCreatedAt.getTime())/(1000*60*60*24));

            if(daysBetween > maxDaysOld){
                profileRepository.deleteCookie(cookie.getCookieValue());
            }
        }
        return true;
    }

    public static boolean deleteCookie(String cookieValue){
        profileRepository.deleteCookie(cookieValue);
        return true;
    }
}
