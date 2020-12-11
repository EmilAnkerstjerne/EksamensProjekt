package com.example.demo.Models;

import java.util.ArrayList;

//JOHN
public class Subproject {
    private int subprojectID;
    private int projectID;
    private String name;

    private ArrayList<Task> tasks = new ArrayList<>();

    public Subproject(int subprojectID, int projectID, String name) {
        this.subprojectID = subprojectID;
        this.projectID = projectID;
        this.name = name;
    }

    //JOHN
    public int getTotalTasksTime(){
        int sum = 0;
        for(Task task : tasks){
            sum += task.getTotalSubtasksTime();
        }
        return sum;
    }

    //JOHN
    public int getNumberOfSubtasks(){
        int count = 0;
        for (Task task : tasks){
            count += task.getNumberOfSubtasks();
        }
        return count;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public void setSubprojectID(int subprojectID) {
        this.subprojectID = subprojectID;
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

    @Override
    public String toString() {
        return "Subproject{" +
                "subprojectID=" + subprojectID +
                ", projectID=" + projectID +
                ", name='" + name + '\'' +
                '}';
    }
}
