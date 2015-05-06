package repository;

import domain.Grade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

public class GradeRepository {

    private EntityManager em;

    public GradeRepository() {
        this.em = JPAUtil.getEntityManager();
    }

    public Grade findById(int gradeId) {
        TypedQuery<Grade> query = em.createNamedQuery("SelectedGrade", Grade.class);
        return query.setParameter("graad", gradeId).getSingleResult();
    }

    public List<Grade> findByDeterminateTableId(int determinateTableId) {
        TypedQuery<Grade> query = em.createNamedQuery("Grade.findByDeterminateTableId", Grade.class);
        return query.setParameter("determinateTableId", determinateTableId).getResultList();
    }

    public List<Grade> getAllGrades() {
        TypedQuery<Grade> query = em.createNamedQuery("Grade.AllGrades", Grade.class);
        return query.getResultList();
    }

}
