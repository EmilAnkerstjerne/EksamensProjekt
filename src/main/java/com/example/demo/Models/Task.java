package com.example.demo.Models;

import java.util.ArrayList;

//JOHN
public class Task {
    private int taskID;
    private int subprojectID;
    private String name;

    private ArrayList<Subtask> subtasks = new ArrayList<>();

    public Task(int taskID, int subprojectID, String name) {
        this.taskID = taskID;
        this.subprojectID = subprojectID;
        this.name = name;
    }

    public void addSubtask(Subtask subtask){
        subtasks.add(subtask);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getSubprojectID() {
        return subprojectID;
    }

    public void setSubprojectID(int subprojectID) {
        this.subprojectID = subprojectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskID=" + taskID +
                ", subprojectID=" + subprojectID +
                ", name='" + name + '\'' +
                '}';
    }
}
