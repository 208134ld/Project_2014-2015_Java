/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author SAMUEL
 */
@Entity(name = "Students")
@Table(name = "Students")
public class Student implements Serializable {

    //Naam-Voornaam-Klas-Leerjaar-Graad

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StudentID")
    private int studentId;

    @Column(name = "Achternaam")
    private String lastName;

    @Column(name = "Voornaam")
    private String firtsName;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private ClassGroup classGroup;

    /*private SchoolYear schoolYear;
    
     private Grade grade;*/
    
    public Student() {
    }

    public Student(String lastName, String firtsName) {
        this.lastName = lastName;
        this.firtsName = firtsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirtsName() {
        return firtsName;
    }

    public void setFirtsName(String firtsName) {
        this.firtsName = firtsName;
    }

    public ClassGroup getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
    }
    /*

     public SchoolYear getSchoolYear() {
     return schoolYear;
     }

     public void setSchoolYear(SchoolYear schoolYear) {
     this.schoolYear = schoolYear;
     }

     public Grade getGrade() {
     return grade;
     }

     public void setGrade(Grade grade) {
     this.grade = grade;
     }*/

}
