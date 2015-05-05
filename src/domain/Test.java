/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 *
 * @author Logan Dupont
 */
public class Test {
    private String Title;
    private String description;   
    private DateTime startDate;
    private DateTime endDate;
    private List<Exercise> exercises;

    public Test(String Title, String description, DateTime startDate, DateTime endDate, List<Exercise> exercises) {
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

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
    
}
