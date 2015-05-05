/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author bremme windows
 */
public class Exercise {
    private ClimateChart climateChart;
    private List<String> questions;
    private DeterminateTable detTable;
    private HashMap<String,Double> punten;
    // puntenverdeling
    private int maxScore;
    public Exercise()
    {
        questions  = new ArrayList<>();
        
    }
    public Exercise(ClimateChart climateChart, List<String> questions, DeterminateTable detTable, int maxScore) {
        this.climateChart = climateChart;
        this.questions = questions;
        this.detTable = detTable;
        this.maxScore = maxScore;
    }
    public ClimateChart getClimateChart() {
        return climateChart;
    }
    public void setClimateChart(ClimateChart climateChart) {
        this.climateChart = climateChart;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public DeterminateTable getDetTable() {
        return detTable;
    }

    public void setDetTable(DeterminateTable detTable) {
        this.detTable = detTable;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
    public void addPunt(String key,double num)
    {
        
    }
    
}
