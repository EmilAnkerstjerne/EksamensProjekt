package com.example.demo.Services;

import com.example.demo.Models.Project;
import com.example.demo.Models.Subproject;
import com.example.demo.Models.Subtask;
import com.example.demo.Models.Task;
import com.example.demo.Repositories.ProjectRepository;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;

//JOHN
public class ProjectService {

    ProjectRepository rep = new ProjectRepository();

    public ProjectService(){
        if (rep.setConnection()){
            System.out.println("Database connection established: failed");
        }
    }

    //TEST (JOHN)
    public void projectTest(int projectID){
        Project project = getProject(projectID);
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

    //TODO: Consider if structure of "downstream" is ok
    //JOHN
    public Project getProject(int projectID){
        //Gets project objects
        Project project = rep.getProject(projectID);
        ArrayList<Subproject> subprojects = rep.getSubprojects(projectID);
        ArrayList<Task> tasks = rep.getTasks(projectID);
        ArrayList<Subtask> subtasks = rep.getSubtasks(projectID);
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
    public boolean createProject(int userID){
        int projectID = rep.createProject(userID);

        if (projectID == -1){
            return false;
        }

        return true;
    }

    //JOHN TODO: Change model name, refactor modelmap scope?
    public void getAdminProjects(int userID, ModelMap modelMap, boolean archived){
        modelMap.addAttribute("Test1", rep.getAdminProjects(userID, archived));
    }

    //JOHN TODO: Change model name, refactor modelmap scope?
    public void getOtherProjects(int userID, ModelMap modelMap, boolean archived){
        modelMap.addAttribute("Test2", rep.getOtherProjects(userID, archived));
    }
}
