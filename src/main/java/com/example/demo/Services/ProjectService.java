package com.example.demo.Services;

import com.example.demo.Models.*;
import com.example.demo.Repositories.ProcessRepository;
import com.example.demo.Repositories.ProfileProjectRelationRepository;
import com.example.demo.Repositories.ProjectRepository;
import com.example.demo.Repositories.UserProjectRelationRepository;
import org.springframework.ui.ModelMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

//JOHN
public class ProjectService {

    ProjectRepository projectRep = new ProjectRepository();
    ProcessRepository processRep = new ProcessRepository();
    ProfileProjectRelationRepository relationRep = new ProfileProjectRelationRepository();
    UserProjectRelationRepository userRep = new UserProjectRelationRepository();

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
            //Adds subtask skills
            st.setSkills(projectRep.getSubtaskSkills(st.getSubtaskID()));
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
        boolean admin = relationRep.checkIfAdminOnProject(profileID,projectID);
        boolean user = relationRep.checkIfAssociatedUserProject(profileID,projectID);

        return admin || user;
    }

    //JOHN
    public boolean isAdmin(int profileID, int projectID){
        return relationRep.checkIfAdminOnProject(profileID,projectID);
    }

    //JOHN
    public boolean canBeAdded(int profileID, int projectID){ //If able to create invitation: true
        boolean admin = relationRep.checkIfAdminOnProject(profileID,projectID);
        boolean user = relationRep.checkIfAssociatedUserProject(profileID,projectID);
        boolean invitation = relationRep.checkIfInvited(profileID,projectID);
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
    public boolean changeArchived(int projectID, boolean status){
        return projectRep.changeArchived(projectID, status);
    }

    //JOHN
    public boolean addEmployee(int projectID, String name){
        return 0 < userRep.createEmployee(projectID,name);
    }

    //JOHN
    public boolean deleteEmployee(int projectID, int employeeID){
        return userRep.deleteEmployee(projectID, employeeID);
    }

    //JOHN
    public ArrayList<Employee> getEmployees(int projectID){
        ArrayList<Employee> employees = userRep.getEmployees(projectID);
        for (Employee e : employees){
            e.setSkills(userRep.getEmployeeSkills(e.getEmployeeID()));
        }
        return employees;
    }

    //EMIL
    public String getAllEmployeeSkills(int projectID){
        String skills = "";
        ArrayList<String> allSkills = userRep.getAllEmployeeSkills(projectID);
        //ArrayList without duplicates
        ArrayList<String> skillsNoDups = new ArrayList<>();

        for(int i = 0; i < allSkills.size(); i++){
            if (!skillsNoDups.contains(allSkills.get(i))){
                skillsNoDups.add(allSkills.get(i));
            }
        }

        Collections.sort(skillsNoDups);

        for(int i = 0; i < skillsNoDups.size(); i++){
            skills += skillsNoDups.get(i);
            if(skillsNoDups.size()-1!=i){
                skills+= ", ";
            }
        }
        return skills;
    }

    //JOHN
    public boolean createEmployeeSkill(int adminID, int employeeID, String value){
        if (userRep.checkAdminEmployeeRelation(adminID,employeeID)){
            return userRep.createEmployeeSkill(employeeID, value);
        }
        return false;
    }

    //JOHN
    public boolean removeEmployeeSkill(int adminID, int employeeSkillID){
        if (userRep.checkAdminEmployeeSkillRelation(adminID, employeeSkillID)){
            return userRep.deleteEmployeeSkill(employeeSkillID);
        }
        return false;
    }


}
