package com.example.demo.Models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    //TOBIAS
    @Test
    void calculateDeadlineTest() {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date startdate = null;
        try {
           startdate = simpleDateFormat.parse("21/12/2020");
        } catch (ParseException | NullPointerException e) {
            System.out.println(e);
        }

        Project p1 = new Project(1,"Test projekt",1, null,startdate,
                10,1,0,false,1);

        Subproject sp1 = new Subproject(1,1,"test Sub1");
        p1.addSubproject(sp1);

        Task t1 = new Task(1,1,"test task1");
        sp1.addTask(t1);

        Subtask st1 = new Subtask(1,1,"test subtask1",20);
        t1.addSubtask(st1);

        String result = "04/01/2021";
        Assertions.assertEquals(result,p1.calculateDeadline());
    }


    @Test
    void getTotalHoursAvailableTest() {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date startdate = null;
        Date deadline = null;
        try {
            startdate = simpleDateFormat.parse("21/12/2020");
            deadline = simpleDateFormat.parse("28/12/2020");
        } catch (ParseException | NullPointerException e) {
            System.out.println(e.getMessage());
        }

        Project p1 = new Project(1,"Test projekt",1, deadline,startdate,
                10,1,0,false,1);

        int result = 10;
        Assertions.assertEquals(result,p1.getTotalHoursAvailable());

    }
}
