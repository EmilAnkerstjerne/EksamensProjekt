package com.example.demo.Models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    private int employees;
    private boolean archived;

    private ArrayList<Subproject> subprojects = new ArrayList<>();

    public Project(int projectID, String name, int adminUserID, Date deadline, Date startDate, int weeklyHours, int weeklyDays, int daysOff, boolean archived, int employess) {
        this.projectID = projectID;
        this.name = name;
        this.adminUserID = adminUserID;
        this.deadline = deadline;
        this.startDate = startDate;
        this.weeklyHours = weeklyHours;
        this.weeklyDays = weeklyDays;
        this.daysOff = daysOff;
        this.archived = archived;
        this.employees = employess;
    }

    //JOHN
    public int getTotalHoursAvailable(){
        int days = 0;
        //No dates
        if (deadline == null && startDate == null){
            return -1;
        }
        //Has a deadline (Current date)
        else if (startDate == null){
            days = (int) getTotalDays(new Date(), deadline);
        }
        //No deadline
        else if (deadline == null){
            return -1;
        }
        //Has deadline and startDate
        else{
            days = (int) getTotalDays(startDate, deadline);
        }
        return ((days - daysOff) * weeklyHours * employees) / 7;
    }

    //JOHN
    public long getTotalDays(Date startDate, Date endDate){
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diff;
    }

    //JOHN
    public long getTotalDays(){
        Date firstDate = startDate;
        Date endDate = deadline;
        if (firstDate == null && endDate == null){
            return -1;
        }
        else if (startDate == null){
            firstDate = new Date();
        }
        else if(deadline == null){
            return -1;
        }
        long diffInMillies = Math.abs(endDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diff;
    }

    //JOHN
    public String getCurrentDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    //JOHN
    public int getTotalSubprojectsTime(){
        int sum = 0;
        for(Subproject subproject : subprojects){
            sum += subproject.getTotalTasksTime();
        }
        return sum;
    }

    //JOHN
    public String printDeadline(){
        if (deadline == null){
            return "00/00/0000";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(deadline);
    }

    //JOHN
    public String calculateDeadline(){
        Date startDate = this.startDate;
        if (startDate == null){
            startDate = new Date();
        }
        double d = daysOff;
        double e = employees;
        double h = getTotalSubprojectsTime();
        double w = weeklyHours;
        int days = (int) Math.ceil(d / e + h / (w * e / 7));
        //Milliseconds in a day: 86400000
        Date deadline = new Date(startDate.getTime() + days * 86400000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(deadline);
    }

    //JOHN
    public String printStartDate(){
        if (startDate == null){
            return getCurrentDate();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(startDate);
    }

    //JOHN
    public String printName(){
        if (name.equals("")){
            return "Ikke navngivet";
        }
        return name;
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

    public int getEmployees() {
        return employees;
    }

    public void setEmployees(int employees) {
        this.employees = employees;
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
                ", employees=" + employees +
                ", archived=" + archived +
                ", subprojects=" + subprojects +
                '}';
    }
}
