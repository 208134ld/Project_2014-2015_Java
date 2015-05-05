package domain;

import repository.ClassGroupsRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

/**
 *
 * @author SAMUEL
 */
public class ClassListController extends Observable{
    
    private ClassGroupsRepository clm = new ClassGroupsRepository();
    
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
        setChanged();
        notifyObservers();
    }
    
    public void removeStudent(Student s){
        clm.removeStudent(s);
        setChanged();
        notifyObservers();
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
    
    public SchoolYear giveSchoolYearWithName(String naam){
        return clm.getSchoolYearWithName(naam);
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
        setChanged();
        notifyObservers();
    }
    
    public void removeClassGroup(ClassGroup cg){
        clm.removeClassGroup(cg);
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
        List<Student> li = clm.getAllStudentsOfClassGroup(cg);
        ObservableList<Student> liObs = FXCollections.observableArrayList(li);
        return new SortedList<>(liObs).sorted(sortOrder);
    }
    
    private final Comparator<Student> byFirstName = (p1, p2) -> p1.getFirtsName().compareToIgnoreCase(p2.getFirtsName());

    private final Comparator<Student> byLastName = (p1, p2) -> p1.getLastName().compareToIgnoreCase(p2.getLastName());
    
    private final Comparator<Student> sortOrder = byLastName.thenComparing(byFirstName);
    
}
