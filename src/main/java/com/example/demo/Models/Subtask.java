package com.example.demo.Models;

//JOHN
public class Subtask {
    private int subtaskID;
    private int taskID;
    private String name;
    private int timeEstimate;

    public Subtask(int subtaskID, int taskID, String name, int timeEstimate) {
        this.subtaskID = subtaskID;
        this.taskID = taskID;
        this.name = name;
        this.timeEstimate = timeEstimate;
    }

    public int getSubtaskID() {
        return subtaskID;
    }

    public void setSubtaskID(int subtaskID) {
        this.subtaskID = subtaskID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeEstimate() {
        return timeEstimate;
    }

    public void setTimeEstimate(int timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "subtaskID=" + subtaskID +
                ", taskID=" + taskID +
                ", name='" + name + '\'' +
                ", timeEstimate=" + timeEstimate +
                '}';
    }
}
