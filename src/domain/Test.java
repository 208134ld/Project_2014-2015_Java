package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity(name = "Tests")
@Table(name = "Tests")
@NamedQueries({
    @NamedQuery(name = "Test.findAllTests", query = "select t from Tests t"),
    @NamedQuery(name = "Test.findById",query = "SELECT t FROM Tests t WHERE t.testId = :testId"),
    @NamedQuery(name = "Test.findByClassGroup",query = "SELECT t FROM Tests t WHERE t.classGroup = :classGroup"),
    
})
public class Test implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int testId;

    private String Title;
    private String description;
    private Date startDate;
    private Date endDate;
    private List<Exercise> exercises;
    
    @ManyToOne
    @JoinColumn(name = "ClassGroupId")
    private ClassGroup classGroup;

    public Test(String Title, String description, Date startDate, Date endDate, ClassGroup classGroup) {
        this.Title = Title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.classGroup = classGroup;
        this.exercises = new ArrayList<>();
    }

    public Test() {
        
    }
    
    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public ClassGroup getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        if(endDate.before(startDate)){
            throw new IllegalArgumentException("De einddatum moet voorbij de begindatum liggen.");
        }
        this.endDate = endDate;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
    
    public String toString(){
        return this.Title;
    }

}
