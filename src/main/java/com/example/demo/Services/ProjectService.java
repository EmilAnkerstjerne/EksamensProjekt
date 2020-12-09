package com.example.demo.Services;

import com.example.demo.Models.Project;
import com.example.demo.Models.Subproject;
import com.example.demo.Models.Subtask;
import com.example.demo.Models.Task;
import com.example.demo.Repositories.ProcessRepository;
import com.example.demo.Repositories.ProfileProjectRelationRepository;
import com.example.demo.Repositories.ProjectRepository;
import org.springframework.ui.ModelMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//JOHN
public class ProjectService {

    ProjectRepository projectRep = new ProjectRepository();
    ProcessRepository processRep = new ProcessRepository();
    ProfileProjectRelationRepository relationRepository = new ProfileProjectRelationRepository();

    public ProjectService(){

    }

    //TODO: Consider if structure of "downstream" is ok
    //JOHN
    public Project getProject(int projectID){
        //Gets project objects
        Project project = projectRep.getProject(projectID);
        //Avoids further nullpointer
        if (project == null){
            return project;
        }
        ArrayList<Subproject> subprojects = projectRep.getSubprojects(projectID);
        ArrayList<Task> tasks = projectRep.getTasks(projectID);
        ArrayList<Subtask> subtasks = projectRep.getSubtasks(projectID);
        //Adds subprojects
        project.setSubprojects(subprojects);
        //Adds tasks
        for(Task t : tasks){
            for(Subproject sp : subprojects){
                if(t.getSubprojectID() == sp.getSubprojectID()){
                    sp.addTask(t);
                    break;
                }
            }
        }
        //Adds subtasks
        for(Subtask st : subtasks){
            for(Task t : tasks){
                if(st.getTaskID() == t.getTaskID()){
                    t.addSubtask(st);
                    break;
                }
            }
        }
        return project;
    }

    //JOHN TODO: ModelMap parameter? Return project object?
    public int createProject(int userID, String projectName){
        int projectID = processRep.createProject(userID,projectName);
        return projectID;
    }

    //JOHN TODO: Change model name, refactor modelmap scope?
    public void getAdminProjects(int userID, ModelMap modelMap, boolean archived){
        modelMap.addAttribute("adminProjects", projectRep.getAdminProjects(userID, archived));
    }

    //JOHN TODO: Change model name, refactor modelmap scope?
    public void getOtherProjects(int userID, ModelMap modelMap, boolean archived){
        modelMap.addAttribute("otherProjects", projectRep.getOtherProjects(userID, archived));
    }

    //JOHN
    public boolean hasAccess(int profileID, int projectID){
        boolean admin = relationRepository.checkIfAdminOnProject(profileID,projectID);
        boolean user = relationRepository.checkIfAssociatedUserProject(profileID,projectID);

        return admin || user;
    }

    //JOHN
    public boolean isAdmin(int profileID, int projectID){
        return relationRepository.checkIfAdminOnProject(profileID,projectID);
    }

    //JOHN
    public boolean canBeAdded(int profileID, int projectID){ //If able to create invitation: true
        boolean admin = relationRepository.checkIfAdminOnProject(profileID,projectID);
        boolean user = relationRepository.checkIfAssociatedUserProject(profileID,projectID);
        boolean invitation = relationRepository.checkIfInvited(profileID,projectID);
        return !admin && !user && !invitation;
    }

    //JOHN
    public boolean changeEssentialInformation(int projectID, String deadline, String startDate, int weeklyHours, int weeklyDays, int daysOff){
        Date sd;
        Date dl;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (startDate == null){
            sd = null;
        }
        else {
            try {
                sd = sdf.parse(startDate);
            }
            catch (ParseException e) {
                sd = null;
            }
        }
        if (deadline == null){
            dl = null;
        }
        else {
            try {
                dl = sdf.parse(deadline);
            }
            catch (ParseException e) {
                dl = null;
            }
        }
        return processRep.changeEssentialInformation(projectID,dl,sd,weeklyHours,weeklyDays,daysOff);
    }

    //JOHN TODO: add rep
    public boolean changeArchived(){
        return false;
    }
}
