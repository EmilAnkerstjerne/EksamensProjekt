package com.example.demo.Models;

public class Invitation {
    private int invitationID;
    private int userID;
    private int projectID;

    public Invitation(int invitationID, int userID, int projectID) {
        this.invitationID = invitationID;
        this.userID = userID;
        this.projectID = projectID;
    }

    public int getInvitationID() {
        return invitationID;
    }

    public void setInvitationID(int invitationID) {
        this.invitationID = invitationID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "invitationID=" + invitationID +
                ", userID=" + userID +
                ", projectID=" + projectID +
                '}';
    }
}
