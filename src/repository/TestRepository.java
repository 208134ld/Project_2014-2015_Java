package repository;

import domain.ClassGroup;
import domain.Test;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

public class TestRepository {

    private EntityManager em;

    public TestRepository() {
        this.em = JPAUtil.getEntityManager();
    }

    public List<Test> getAllTests() {
        TypedQuery<Test> query = em.createNamedQuery("Test.findAllTests", Test.class);
        return query.getResultList();
    }

    public void insertTest(Test t) {
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
    }

    public Test findTestById(int id) {
        TypedQuery<Test> query = em.createNamedQuery("Test.findById", Test.class);
        return query.setParameter("testId", id).getSingleResult();
    }
    
    public List<Test> findTestsByClassGroup(ClassGroup classGroup){
        TypedQuery<Test> query = em.createNamedQuery("Test.findByClassGroup", Test.class);
        return query.setParameter("classGroup", classGroup).getResultList();
    }
    
    public void removeTest(Test t){
        em.getTransaction().begin();
        em.remove(t);
        em.getTransaction().commit();
    }
}
