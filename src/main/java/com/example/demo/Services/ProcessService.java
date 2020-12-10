package com.example.demo.Services;

import com.example.demo.Repositories.ProcessRepository;

//JOHN
public class ProcessService {

    private ProcessRepository processRep = new ProcessRepository();

    //JOHN
    public boolean changeProject(int projectID, String name){
        return processRep.changeProject(projectID, name);
    }

    //JOHN
    public boolean createSubproject(int projectID, String name){
        return processRep.createSubproject(projectID, name);
    }

    //JOHN
    public boolean changeSubproject(int adminID, int subprojectID, String name){
        if (processRep.checkAdminSubprojectRelation(adminID, subprojectID)){
            return processRep.changeSubproject(subprojectID, name);
        }
        return false;
    }

    //JOHN
    public boolean deleteSubproject(int adminID, int subprojectID){
        if (processRep.checkAdminSubprojectRelation(adminID, subprojectID)){
            return processRep.deleteSubproject(subprojectID);
        }
        return false;
    }

    //JOHN
    public boolean createTask(int adminID, int subprojectID, String name){
        if (processRep.checkAdminSubprojectRelation(adminID, subprojectID)){
            return processRep.createTask(subprojectID, name);
        }
        return false;
    }

    //JOHN
    public boolean changeTask(int adminID, int taskID, String name){
        if (processRep.checkAdminTaskRelation(adminID, taskID)){
            return processRep.changeTask(taskID, name);
        }
        return false;
    }

    //JOHN
    public boolean deleteTask(int adminID, int taskID){
        if (processRep.checkAdminTaskRelation(adminID, taskID)){
            return processRep.deleteTask(taskID);
        }
        return false;
    }

    //JOHN
    public boolean createSubtask(int adminID, int taskID, String name, int timeEstimation){
        if (processRep.checkAdminTaskRelation(adminID, taskID)){
            return processRep.createSubtask(taskID, name, timeEstimation);
        }
        return false;
    }

    //JOHN
    public boolean changeSubtask(int adminID, int subtaskID, String name, int timeEstimation){
        if (processRep.checkAdminSubtaskRelation(adminID, subtaskID)){
            return processRep.changeSubtask(subtaskID, name, timeEstimation);
        }
        return false;
    }

    //JOHN
    public boolean deleteSubtask(int adminID, int subtaskID){
        if (processRep.checkAdminSubtaskRelation(adminID, subtaskID)){
            return processRep.deleteSubtask(subtaskID);
        }
        return false;
    }

    //JOHN
    public boolean createSubtaskSkill(int adminID, int subtaskID, String value){
        if (processRep.checkAdminSubtaskRelation(adminID, subtaskID)){
            return processRep.createSubtaskSkill(subtaskID, value);
        }
        return false;
    }

    //JOHN
    public boolean deleteSubtaskSkill(int adminID, int subtaskID, int subtaskSkillID){
        if (processRep.checkAdminSubtaskRelation(adminID, subtaskID)){
            return processRep.deleteSubtaskSkill(subtaskSkillID, subtaskID);
        }
        return false;
    }
}
