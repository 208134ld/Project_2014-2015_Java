package controllers;

import domain.ClassGroup;
import domain.Grade;
import domain.SchoolYear;
import domain.Student;
import repository.ClassGroupsRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

public class ClassListController extends Observable{
    
    private ClassGroupsRepository classRepo = new ClassGroupsRepository();
    
    public List<Grade> giveAllGrades(){
        return classRepo.getAllGrades();
    }
    
    public List<SchoolYear> giveAllSchoolYears(){
        return classRepo.getAllSchoolYears();
    }
    
    public List<ClassGroup> giveAllClassGroups(){
        return classRepo.getAllClassGroups();
    }
    
    public List<Student> giveAllStudents(){
        return classRepo.getAllStudents();
    }
    
    public void addStudent(Student s){
        classRepo.insertStudent(s);
        setChanged();
        notifyObservers();
    }
    
    public void removeStudent(Student s){
        classRepo.removeStudent(s);
        setChanged();
        notifyObservers();
    }
    
    public List<SchoolYear> giveSchoolYearsOfGrade(Grade g){
        return classRepo.getAllSchoolYearsOfGrade(g);
    }
    
    public List<ClassGroup> giveClassGroupOfSchoolYear(SchoolYear sy){
        return classRepo.getAllClassGroupsOfSchoolYear(sy);
    }

    public List<Student> giveStudentsOfClassGroup(ClassGroup cg) {
        return classRepo.getAllStudentsOfClassGroup(cg);
    }
    
    public List<Student> giveStudentsWithFirstName(String fname){
        return classRepo.getStudentsWithFirstName(fname);
    }
    
    public ClassGroup giveClassGroupWithName(String name){
        return classRepo.getClassGroupWithName(name);
    }
    
    public Grade giveGradeWithName(String naam){
        return classRepo.getGradeWithName(naam);
    }
    
    public SchoolYear giveSchoolYearWithName(String naam){
        return classRepo.getSchoolYearWithName(naam);
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
        classRepo.insertClassGroup(cg);
        setChanged();
        notifyObservers();
    }
    
    public void removeClassGroup(ClassGroup cg){
        classRepo.removeClassGroup(cg);
        setChanged();
        notifyObservers();
    }
    
    @Override
    public void addObserver(Observer observer)
    {
        super.addObserver(observer);
        observer.update(this, this);
    }

    public SortedList<Student> giveStudentsOfClassGroupSorted(ClassGroup cg) {
        List<Student> li = classRepo.getAllStudentsOfClassGroup(cg);
        ObservableList<Student> liObs = FXCollections.observableArrayList(li);
        return new SortedList<>(liObs).sorted(sortOrder);
    }
    
    private final Comparator<Student> byFirstName = (p1, p2) -> p1.getFirtsName().compareToIgnoreCase(p2.getFirtsName());

    private final Comparator<Student> byLastName = (p1, p2) -> p1.getLastName().compareToIgnoreCase(p2.getLastName());
    
    private final Comparator<Student> sortOrder = byLastName.thenComparing(byFirstName);
    
}
