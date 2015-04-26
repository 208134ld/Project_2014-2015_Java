package domain;

import java.util.List;

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
    
    public void addStudent(Student s){
        clm.insertStudent(s);
    }
    
    public void removeStudent(Student s){
        clm.removeStudent(s);
    }
    
    public List<SchoolYear> giveSchoolYearsOfGrade(Grade g){
        return clm.getAllSchoolYearsOfGrade(g);
    }
    
    public List<ClassGroup> giveClassGroupOfSchoolYear(SchoolYear sy){
        return clm.getAllClassGroupsOfSchoolYear(sy);
    }

    public List<Student> giveStudentsOfClassGroup(ClassGroup cg) {
        return clm.getAllStudentsOfClassGroup(cg);
    }
    
    public ClassGroup giveClassGroupWithName(String name){
        return clm.getClassGroupWithName(name);
    }
    
    public Grade giveGradeWithName(String naam){
        return clm.getGradeWithName(naam);
    }
    
    
    public String giveGradeInfo(ClassGroup cg){
        StringBuilder sb = new StringBuilder();
        
        SchoolYear sy = cg.getSchoolYear();
        Grade g = sy.getGrade();
        
        sb.append("Graad: ").append(g.getGrade());
        sb.append("\t Leerjaar: ").append(sy.getSchoolYearString());
        sb.append("\t Klas: ").append(cg.getGroupName());
        
        return sb.toString();
    }
    
    public void addClassGroup(ClassGroup cg){
        clm.insertClassGroup(cg);
    }
    
    public void removeClassGroup(ClassGroup cg){
        clm.removeClassGroup(cg);
    }
    
}
