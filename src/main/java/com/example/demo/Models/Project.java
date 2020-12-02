package com.example.demo.Models;

import java.util.Date;

public class Project {

    int projectID;
    String name;
    int adminUserID;
    Date deadline;
    Date startDate;
    int weeklyHours;
    int weeklyDays;
    int daysOff;
    boolean archived;

    public Project(int projectID, String name, int adminUserID, Date deadline, Date startDate, int weeklyHours, int weeklyDays, int daysOff, boolean archived) {
        this.projectID = projectID;
        this.name = name;
        this.adminUserID = adminUserID;
        this.deadline = deadline;
        this.startDate = startDate;
        this.weeklyHours = weeklyHours;
        this.weeklyDays = weeklyDays;
        this.daysOff = daysOff;
        this.archived = archived;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdminUserID() {
        return adminUserID;
    }

    public void setAdminUserID(int adminUserID) {
        this.adminUserID = adminUserID;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public int getWeeklyDays() {
        return weeklyDays;
    }

    public void setWeeklyDays(int weeklyDays) {
        this.weeklyDays = weeklyDays;
    }

    public int getDaysOff() {
        return daysOff;
    }

    public void setDaysOff(int daysOff) {
        this.daysOff = daysOff;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectID=" + projectID +
                ", name='" + name + '\'' +
                ", adminUserID=" + adminUserID +
                ", deadline=" + deadline +
                ", startDate=" + startDate +
                ", weeklyHours=" + weeklyHours +
                ", weeklyDays=" + weeklyDays +
                ", daysOff=" + daysOff +
                ", archived=" + archived +
                '}';
    }
}
