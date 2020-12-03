package com.example.demo.Services;

import com.example.demo.Models.Profile;
import com.example.demo.Repositories.ProfileRepository;

//JOHN
public class ProfileService {
    private ProfileRepository rep = new ProfileRepository();

    //JOHN
    public Profile getProfile(int profileID){
        return rep.getProfileDataFromProfileID(profileID);
    }

    //JOHN
    public boolean changePassword(int profileID, String password){
        return rep.changePassword(profileID,password);
    }

    //JOHN
    public boolean checkPassword(int profileID, String password){ //Correct password/match = true
        Profile profile = rep.getProfileDataFromProfileID(profileID);
        return profile.getPassword().equals(password);
    }
}
