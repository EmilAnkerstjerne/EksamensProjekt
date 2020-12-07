package com.example.demo.Services;

import com.example.demo.Models.Invitation;
import com.example.demo.Models.Profile;
import com.example.demo.Repositories.ProfileRepository;
import com.example.demo.Repositories.UserProjectRelationRepository;

import java.util.ArrayList;

//JOHN
public class ProfileService {
    private ProfileRepository rep = new ProfileRepository();
    private UserProjectRelationRepository userProjectRelationRepository = new UserProjectRelationRepository();

    //JOHN
    public Profile getProfile(int profileID){
        return rep.getProfileData(profileID);
    }

    //JOHN
    public boolean changePassword(int profileID, String password){
        return rep.changePassword(profileID,password);
    }

    //JOHN
    public boolean checkPassword(int profileID, String password){ //Correct password/match = true
        Profile profile = rep.getProfileData(profileID);
        return profile.getPassword().equals(password);
    }

    //JOHN
    public ArrayList<Invitation> getInvitations(int profileID){
        return userProjectRelationRepository.getInvitations(profileID);
    }

    //JOHN
    public boolean deleteInvitation(int invitationID, int profileID){
        return userProjectRelationRepository.deleteInvitation(invitationID, profileID);
    }

    //JOHN
    public int getProjectIDFromInvitationID(int invitationID){
        Invitation invitation = userProjectRelationRepository.getInvitation(invitationID);
        if (invitation == null){
            return -1;
        }
        int projectID = invitation.getProjectID();
        return projectID;
    }

    //JOHN
    public boolean createUserProjectRelation(int profileID, int projectID){
        return userProjectRelationRepository.createUserProjectRelation(profileID,projectID);
    }

    //JOHN
    public ArrayList<Profile> getUserProfiles(int projectID){
        return userProjectRelationRepository.getUserProfiles(projectID);
    }
}
