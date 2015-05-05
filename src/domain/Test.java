/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 *
 * @author Logan Dupont
 */
public class Test {
    private String Title;
    private String description;   
    private GregorianCalendar startDate;
    private GregorianCalendar endDate;
    private List<Exercise> exercises;

    public Test(String Title, String description, GregorianCalendar startDate, GregorianCalendar endDate, List<Exercise> exercises) {
        this.Title = Title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.exercises = exercises;
        
    }

    public Test()
    {
        exercises = new ArrayList<>();
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GregorianCalendar getStartDate() {
        return startDate;
    }

    public void setStartDate(GregorianCalendar startDate) {
        this.startDate = startDate;
    }

    public GregorianCalendar getEndDate() {
        return endDate;
    }

    public void setEndDate(GregorianCalendar endDate) {
        this.endDate = endDate;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
    
}
