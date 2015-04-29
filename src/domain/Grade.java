/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
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

/**
 *
 * @author SAMUEL
 */
@Entity(name="Grades")
@Table(name = "Grades")
@NamedQueries({
    @NamedQuery(name="Grade.AllGrades",
                query="SELECT g FROM Grades g"),
    @NamedQuery(name="SelectedGrade",
                query="SELECT g FROM Grades g where g.grade = :graad"),
    @NamedQuery(name="Grade.findByDeterminateTableId",
                query="SELECT g FROM Grades g where g.DeterminateTableId = :determinateTableId")
}) 
public class Grade implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GradeId")
    private int grade;
    
    @OneToMany(mappedBy = "grade")
    private List<SchoolYear> schoolYears;
    
    @ManyToOne
    @JoinColumn(name = "DeterminateTableId")
    private DeterminateTable DeterminateTableId;

    //CTOR
    public Grade() {
    }

    public Grade(int grade) {
        this.grade = grade;
    }
    
//    public Grade(int gradeId, int determinateTableId){
//        this.grade = gradeId;
//        this.DeterminateTableId = determinateTableId;
//    }
    
    //GET SET
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public List<SchoolYear> getSchoolYears() {
        return schoolYears;
    }
    
    public DeterminateTable getDeterminateTableId() {
        return DeterminateTableId;
    }
    
    public void setDeterminateTableId(DeterminateTable DeterminateTableId) {
        this.DeterminateTableId = DeterminateTableId;
    }

    public void setSchoolYears(List<SchoolYear> schoolYears) {
        this.schoolYears = schoolYears;
    }
    
    public String getGradeString(){
        return Integer.toString(grade);
    }
    
    
    
}
