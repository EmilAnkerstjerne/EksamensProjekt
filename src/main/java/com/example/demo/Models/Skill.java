package com.example.demo.Models;

public class Skill {

    private int ownerID;
    private int skillID;
    private String value;

    public Skill(int ownerID, int skillID, String value) {
        this.ownerID = ownerID;
        this.skillID = skillID;
        this.value = value;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getSkillID() {
        return skillID;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "ownerID=" + ownerID +
                ", skillID=" + skillID +
                ", value='" + value + '\'' +
                '}';
    }
}
