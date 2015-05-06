package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Exercise {

    private String naam;
    private ClimateChart climateChart;
    private List<String> questions;
    private DeterminateTable detTable;
    private HashMap<String, Double> punten;
    private int maxScore;

    public Exercise(String naam) {
        questions = new ArrayList<>();
        this.naam = naam;
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

    public void addPunt(String key, double num) {

    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public String toString() {
        return this.getNaam();
    }
}
