/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.List;
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
    
    public ClassListManagement(){
        this.em = JPAUtil.getEntityManager();
    }
    
    //NamedQuerrys
    public List<Grade> getAllGrades()
    {
        TypedQuery<Grade> query = em.createNamedQuery("AllGrades", Grade.class);
        return query.getResultList();
    }
    
    public List<SchoolYear> getAllSchoolYears()
    {
        TypedQuery<SchoolYear> query = em.createNamedQuery("AllSchoolYears", SchoolYear.class);
        return query.getResultList();
    }
    
    public List<ClassGroup> getAllClassGroups()
    {
        TypedQuery<ClassGroup> query = em.createNamedQuery("AllClassGroups", ClassGroup.class);
        return query.getResultList();
    }
    
    public List<Student> getAllStudents()
    {
        TypedQuery<Student> query = em.createNamedQuery("AllStudents", Student.class);
        return query.getResultList();
    }
    
    //Update, insert methodes
    
}
