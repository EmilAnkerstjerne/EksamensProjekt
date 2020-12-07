package com.example.demo.Models;

import java.util.ArrayList;
import java.util.Date;

//JOHN
public class Project {

    private int projectID;
    private String name;
    private int adminUserID;
    private Date deadline;
    private Date startDate;
    private int weeklyHours;
    private int weeklyDays;
    private int daysOff;
    private boolean archived;

    private ArrayList<Subproject> subprojects = new ArrayList<>();

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

    public void addSubproject(Subproject subproject){
        subprojects.add(subproject);
    }

    public ArrayList<Subproject> getSubprojects(){
        return subprojects;
    }

    public void setSubprojects(ArrayList<Subproject> subprojects) {
        this.subprojects = subprojects;
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

    //JOHN (Alt. toString for structure of lists)
    public void printProjectStructure(){ //Prints out a project in structure
        Project project = this;
        System.out.println(project);
        for(Subproject sp : project.getSubprojects()){
            System.out.println(sp);
            for(Task t : sp.getTasks()){
                System.out.println(t);
                for(Subtask st : t.getSubtasks()){
                    System.out.println(st);
                }
            }
        }
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
