/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author SAMUEL
 */
public class ClassListController {
    
    private ClassListManagement clm = new ClassListManagement();
    
    public List<Grade> giveAllGrades(){
        return /*List<Grade> li =*/ clm.getAllGrades();
        //return li.stream().map(Grade::getGradeString).collect(Collectors.toList());
    }
    
    public List<SchoolYear> giveAllSchoolYears(){
        return /*List<SchoolYear> li =*/ clm.getAllSchoolYears();
        //return li.stream().map(SchoolYear::getSchoolYearString).collect(Collectors.toList());
    }
    
    public List<ClassGroup> giveAllClassGroups(){
        return /*List<ClassGroup> li =*/ clm.getAllClassGroups();
        //return li.stream().map(ClassGroup::getGroupName).collect(Collectors.toList());
    }
    
    public List<Student> giveAllStudents(){
        return /*List<Student> li =*/ clm.getAllStudents();
        //return li.stream().map(Student::getFullName).collect(Collectors.toList());
    }
    
    //MOETEN WE VOOR EEN GET METHODE NAAR DE CONTROLLER???
    public List<SchoolYear> giveSchoolYearsOfGrade(Grade g){
        return g.getSchoolYears();
    }
    
    public List<ClassGroup> giveClassGroupOfSchoolYear(SchoolYear sy){
        return sy.getClassGroup();
    }

    public Iterable<Student> giveStudentsOfClassGroup(ClassGroup cg) {
        return cg.getStudents();
    }
    
}
