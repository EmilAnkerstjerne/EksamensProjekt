package com.example.demo.Models;

public class EmployeeSkill {

    private int employeeSkillID;
    private int employeeID;
    private String value;

    public EmployeeSkill(int employeeSkillID, int employeeID, String value) {
        this.employeeSkillID = employeeSkillID;
        this.employeeID = employeeID;
        this.value = value;
    }

    public int getEmployeeSkillID() {
        return employeeSkillID;
    }

    public void setEmployeeSkillID(int employeeSkillID) {
        this.employeeSkillID = employeeSkillID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EmployeeSkill{" +
                "employeeSkillID=" + employeeSkillID +
                ", employeeID=" + employeeID +
                ", value='" + value + '\'' +
                '}';
    }
}
