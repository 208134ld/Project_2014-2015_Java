package repository;

import domain.ClassGroup;
import domain.Exercise;
import domain.Test;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

public class ExerciseRepository {

    private EntityManager em;

    public ExerciseRepository() {
        this.em = JPAUtil.getEntityManager();
    }

    public List<Exercise> getAllExercises() {
        TypedQuery<Exercise> query = em.createNamedQuery("Exercise.findAllExercises", Exercise.class);
        return query.getResultList();
    }

    public void insertExercise(Exercise e) {
        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();
    }

    public Exercise findExerciseById(int id) {
        TypedQuery<Exercise> query = em.createNamedQuery("Exercise.findById", Exercise.class);
        return query.setParameter("exerciseId", id).getSingleResult();
    }
    
    public List<Exercise> findExercisesByTest(Test test){
        TypedQuery<Exercise> query = em.createNamedQuery("Exercise.findByTest", Exercise.class);
        return query.setParameter("test", test).getResultList();
    }
}
