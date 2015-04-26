
package repository;

import domain.ClauseComponent;
import domain.Grade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

public class GradeRepository {
    
    private EntityManager em;
    
    public GradeRepository(){
        this.em = JPAUtil.getEntityManager();
    }
    
    public Grade findById(int gradeId)
    {
        TypedQuery<Grade> query = em.createNamedQuery("SelectedGrade", Grade.class);
        return query.setParameter("graad", gradeId).getSingleResult();
    }
}
