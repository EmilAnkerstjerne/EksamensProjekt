package com.example.demo.Services;

import com.example.demo.Repositories.ProfileRepository;

//JOHN
public class RegistrationService {

    ProfileRepository rep = new ProfileRepository();

    public RegistrationService() {
        rep.setConnection();
    }

    //JOHN
    public boolean checkIfUsernameIsTaken(String username){ //If taken = true
        return rep.checkUsername(username);
    }

    //JOHN
    public int createProfile(String username, String password){
        return rep.createProfile(username,password);
    }
}
