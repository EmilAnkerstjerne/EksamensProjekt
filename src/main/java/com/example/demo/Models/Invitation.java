package com.example.demo.Models;

//Emil
public class Invitation {
    private int invitationID;
    private int userID;
    private int projectID;
    private String projectName;
    private String adminUsername;

    public Invitation(int invitationID, int userID, int projectID, String projectName, String adminUsername) {
        this.invitationID = invitationID;
        this.userID = userID;
        this.projectID = projectID;
        this.projectName = projectName;
        this.adminUsername = adminUsername;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
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
                ", projectName='" + projectName + '\'' +
                ", adminUsername='" + adminUsername + '\'' +
                '}';
    }
}
