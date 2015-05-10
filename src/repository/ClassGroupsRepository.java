package repository;

import domain.ClassGroup;
import domain.Grade;
import domain.SchoolYear;
import domain.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

public class ClassGroupsRepository {

    private EntityManager em;

    public ClassGroupsRepository() {
        this.em = JPAUtil.getEntityManager();
    }

    public List<Grade> getAllGrades() {
        TypedQuery<Grade> query = em.createNamedQuery("Grade.AllGrades", Grade.class);
        return query.getResultList();
    }
    
    public Grade getGradeWithName(String graad){
        TypedQuery<Grade> query = em.createNamedQuery("SelectedGrade", Grade.class).setParameter("graad", Integer.parseInt(graad));
        return query.getSingleResult();
    }
    
    public SchoolYear getSchoolYearWithName(String sy) {
        TypedQuery<SchoolYear> query = em.createNamedQuery("SchoolYearWithName", SchoolYear.class).setParameter("sy", Integer.parseInt(sy));
        return query.getSingleResult();
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
    
    public ClassGroup getClassGroupWithName(String name){
        TypedQuery<ClassGroup> query = em.createNamedQuery("ClassGroupWithName", ClassGroup.class).setParameter("name", name);
        return query.getSingleResult();
    }

    public List<Student> getAllStudents() {
        TypedQuery<Student> query = em.createNamedQuery("AllStudents", Student.class);
        return query.getResultList();
    }

    public List<Student> getAllStudentsOfClassGroup(ClassGroup cg) {
        TypedQuery<Student> query = em.createNamedQuery("StudentsOfClassGroup", Student.class).setParameter("cg", cg);
        return query.getResultList();
    }
    
    public List<Student> getStudentsWithFirstName(String fname){
        TypedQuery<Student> query = em.createNamedQuery("StudentsWithFirstName", Student.class).setParameter("fname", fname);
        return query.getResultList();
    }

    //Update, insert methodes
    public void insertStudent(Student s) { //name + ClassGroup
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
    }

    public void removeStudent(Student s) {
        em.getTransaction().begin();
        em.remove(s);
        em.getTransaction().commit();
    }

    public void insertClassGroup(ClassGroup cg) { //groupName + SchoolYear
        em.getTransaction().begin();
        em.persist(cg);
        em.getTransaction().commit();
    }

    public void removeClassGroup(ClassGroup cg) {
        em.getTransaction().begin();
        em.remove(cg);
        em.getTransaction().commit();
    }
    
    public ClassGroup findById(int id)
    {
        TypedQuery<ClassGroup> query = em.createNamedQuery("ClassGroup.findById", ClassGroup.class).setParameter("id", id);
        return query.getSingleResult();
    }
}
