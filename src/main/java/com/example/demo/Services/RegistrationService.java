package com.example.demo.Services;

import com.example.demo.Repositories.ProcessRepository;

//JOHN
public class RegistrationService {

    ProcessRepository rep = new ProcessRepository();

    public RegistrationService() {
        rep.setConnection();
    }

    //JOHN
    public boolean checkIfUsernameIsTaken(String username){
        //TODO: Missing body
        return true;
    }

    //JOHN
    public int createProfile(String username, String password){
        //TODO: Missing body
        return -1;
    }
}
