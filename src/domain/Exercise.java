package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "Exercises")
@Table(name = "Exercises")
@NamedQueries({
    @NamedQuery(name = "Exercise.findAllExercises", query = "select e from Exercises e"),
    @NamedQuery(name = "Exercise.findById",query = "SELECT e FROM Exercises e WHERE e.exerciseId = :exerciseId"),
    @NamedQuery(name = "Exercise.findByTest",query = "SELECT e FROM Exercises e WHERE e.test = :test")
})
public class Exercise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int exerciseId;
    private String naam;
    
    @ManyToOne
    private ClimateChart climateChart;

    
    @ManyToOne
    private DeterminateTable detTable;
    private Double punten;
    
    @ManyToOne
    private Test test;

    public Exercise(){
        
    }
    
    public Exercise(String naam) {
        //questions = new ArrayList<>();
        this.naam = naam;
    }

    public Exercise(String name, double quotation, ClimateChart chart, DeterminateTable dTable, Test test) {
        this.naam = name;
        this.punten = quotation;
        this.test = test;
        this.climateChart = chart;
        this.detTable = dTable;
    }

    public ClimateChart getClimateChart() {
        return climateChart;
    }

    public void setClimateChart(ClimateChart climateChart) {
        this.climateChart = climateChart;
    }

    public DeterminateTable getDetTable() {
        return detTable;
    }

    public void setDetTable(DeterminateTable detTable) {
        this.detTable = detTable;
    }

    public void addPunt(String key, double num) {

    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Double getPunten() {
        return punten;
    }

    public void setPunten(Double punten) {
        this.punten = punten;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
    
    @Override
    public String toString() {
        return this.getNaam();
    }
}
