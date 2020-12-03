package com.example.demo.Models;

//JOHN
public class Employee {
    private int employeeID;
    private int projectID;
    private String name;

    public Employee(int employeeID, int projectID, String name) {
        this.employeeID = employeeID;
        this.projectID = projectID;
        this.name = name;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
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
        return "Employee{" +
                "employeeID=" + employeeID +
                ", projectID=" + projectID +
                ", name='" + name + '\'' +
                '}';
    }
}
