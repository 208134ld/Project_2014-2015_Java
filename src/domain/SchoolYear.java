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
@Entity(name="SchoolYears")
@Table(name = "SchoolYears")
@NamedQueries({
    @NamedQuery(name="AllSchoolYears",
                query="SELECT s FROM SchoolYears s")
}) 
public class SchoolYear implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Year")
    private int schoolYear;
    
    @OneToMany(mappedBy = "schoolYear")
    private List<ClassGroup> classGroup;
    
    @ManyToOne
    @JoinColumn(name = "GradeId")
    private Grade grade;

    public SchoolYear() {
    }

    public SchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public List<ClassGroup> getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(List<ClassGroup> classGroup) {
        this.classGroup = classGroup;
    }
    
    public String getSchoolYearString(){
        return Integer.toString(getSchoolYear());
    }

}
