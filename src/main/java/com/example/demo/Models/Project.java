package com.example.demo.Models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
    public HashMap<String, Integer> getTasksSkills(){ //Gets all tasks as keys to map and occurrences as value
        HashMap<String, Integer> map = new HashMap<>();

        for (Subproject subproject : subprojects){
            for (Task task : subproject.getTasks()){
                for (Subtask subtask : task.getSubtasks()){
                    for (Skill skill : subtask.getSkills()){
                        String value = skill.getValue();
                        if (map.containsKey(value)){
                            Integer count = map.get(value);
                            count = count.intValue() + 1;
                            map.put(value, count);
                        }
                        else {
                            map.put(value, 1);
                        }
                    }
                }
            }
        }
        return map;
    }

    //JOHN
    public ArrayList<String> getTaskAnalysis(){
        ArrayList<String> list = new ArrayList<>();
        int totalSkills = getNumberOfSubtasks();

        HashMap<String, Integer> map = getTasksSkills();

        for (String key : map.keySet()){
            double p = totalSkills;
            double value = map.get(key);
            int perc = (int) (value / p * 100);
            String percentage = perc + "%";
            if (percentage.length() < 3){
                percentage = "0" + percentage;
            }
            String result = percentage + " - " + key + " : " + map.get(key);
            list.add(result);
        }

        Collections.sort(list);
        Collections.reverse(list);

        return list;
    }

    //JOHN
    public ArrayList<String> getAllSubtaskSkills(){
        HashMap<String, Integer> map = getTasksSkills();
        ArrayList<String> list = new ArrayList<>();

        for (String key : map.keySet()){
            list.add(key);
        }
        Collections.sort(list);
        return list;
    }

    // Tobias
    public String getAllSubtaskSkillsToString(){

        ArrayList<String> allSubtaskSkills = getAllSubtaskSkills();
        String skillsString="";
        String tmp;

        for(int i=0; i<allSubtaskSkills.size();i++){
            tmp=allSubtaskSkills.get(i);
            skillsString = skillsString + tmp;
            if (allSubtaskSkills.size()!=1 && allSubtaskSkills.size()-1!=i) {
                skillsString = skillsString + ", ";
            }
        }
        return skillsString;
    }

    //JOHN
    public int calculateHoursWeekEmployeeNeeded(){
        double totalDays = getTotalDays();
        if (totalDays < 0){
            return -1;
        }
        double t = getTotalSubprojectsTime();
        double e = employees;
        int hours = (int) Math.ceil((t / e) / (totalDays / 7));
        return hours;
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
        long diffInMillieSeconds = Math.abs(endDate.getTime() - firstDate.getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillieSeconds, TimeUnit.MILLISECONDS);

        return diffInDays;
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
        Date deadline = new Date(startDate.getTime() + (days * 86400000L));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(deadline);
    }

    //JOHN
    public int getNumberOfSubtasks(){
        int count = 0;
        for (Subproject subproject : subprojects){
            count += subproject.getNumberOfSubtasks();
        }
        return count;
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

    public String formStartDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(startDate);
    }

    public boolean formNullStartDate(){
        return startDate == null;
    }

    public String formDeadline(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(deadline);
    }

    public boolean formNullDeadline(){
        return deadline == null;
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
