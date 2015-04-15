/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

/**
 *
 * @author SAMUEL
 */
public class ClassListManagement {
    //1 MANAGER KLASSE IPV 3 APARTE REPOSITORY KLASSES

    private EntityManager em;
    
    //TESTING
    //List<SchoolYear> syList = new ArrayList<>(getAllSchoolYears());
    
    public ClassListManagement() {
        this.em = JPAUtil.getEntityManager();
        //TESTING
        /*for (SchoolYear sy : syList) {
            ClassGroup cg = new ClassGroup( sy.getSchoolYearString() + "A", sy);
            insertClassGroup(cg);
        }*/
    }

    //NamedQuerrys
    public List<Grade> getAllGrades() {
        TypedQuery<Grade> query = em.createNamedQuery("AllGrades", Grade.class);
        return query.getResultList();
    }

    public List<SchoolYear> getAllSchoolYears() {
        TypedQuery<SchoolYear> query = em.createNamedQuery("AllSchoolYears", SchoolYear.class);
        return query.getResultList();
    }

    public List<SchoolYear> getAllSchoolYearsOfGrade(Grade g) {
        TypedQuery<SchoolYear> query = em.createNamedQuery("SchoolYearsOfGrade", SchoolYear.class).setParameter("g", g);
        return query.getResultList();
    }

    public List<ClassGroup> getAllClassGroups() {
        TypedQuery<ClassGroup> query = em.createNamedQuery("AllClassGroups", ClassGroup.class);
        return query.getResultList();
    }

    public List<ClassGroup> getAllClassGroupsOfSchoolYear(SchoolYear sy) {
        TypedQuery<ClassGroup> query = em.createNamedQuery("ClassGroupsOfSchoolYear", ClassGroup.class).setParameter("sy", sy);
        return query.getResultList();
    }

    public List<Student> getAllStudents() {
        TypedQuery<Student> query = em.createNamedQuery("AllStudents", Student.class);
        return query.getResultList();
    }

    public List<Student> getAllStudentsOfClassGroup(ClassGroup cg) {
        TypedQuery<Student> query = em.createNamedQuery("StudentsOfClassGroup", Student.class).setParameter("cg", cg);
        return query.getResultList();
    }

    //Update, insert methodes
    public void insertStudent(Student s) { //name + ClassGroup
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
    }

    void removeStudent(Student s) {
        em.getTransaction().begin();
        em.remove(s);
        em.getTransaction().commit();
    }

    public void insertClassGroup(ClassGroup cg) { //groupName + SchoolYear
        em.getTransaction().begin();
        em.persist(cg);
        em.getTransaction().commit();
    }

    void removeClassGroup(ClassGroup cg) {
        em.getTransaction().begin();
        em.remove(cg);
        em.getTransaction().commit();
    }

}
