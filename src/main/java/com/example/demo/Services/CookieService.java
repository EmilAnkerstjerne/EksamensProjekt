package com.example.demo.Services;

import com.example.demo.Models.CookieModel;
import com.example.demo.Repositories.ProfileRepository;

import java.util.ArrayList;

public class CookieService {

    public CookieService() {
    }

    public static boolean deleteOldCookies(){
        ProfileRepository profileRepository = new ProfileRepository();
        ArrayList<CookieModel> cookies = profileRepository.getAllCookies();
        return true;
    }

    public boolean deleteCookie(){

        return true;
    }
}
