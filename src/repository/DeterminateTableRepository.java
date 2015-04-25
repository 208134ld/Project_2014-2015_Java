
package repository;

import domain.Clause;
import domain.ClauseComponent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

public class DeterminateTableRepository {
    
    private EntityManager em;
    
    public DeterminateTableRepository(){
        this.em = JPAUtil.getEntityManager();
    }
    
    public List<ClauseComponent> getAllClauseComponentsOfDeterminateTable(int determinateTableId)
    {
        TypedQuery<ClauseComponent> query = em.createNamedQuery("ClauseComponent.findClauseComponentsByDeterminateTableId", ClauseComponent.class);
        return query.setParameter("determinateTableId", determinateTableId).getResultList();
    }
    
    public List<ClauseComponent> getAllClausesOfDeterminateTable(int determinateTableId){
        TypedQuery<ClauseComponent> query = em.createNamedQuery("Clause.findClausesByDeterminateTableId", ClauseComponent.class);
        return query.setParameter("determinateTableId", determinateTableId).setParameter("discriminator", "Clause").getResultList();
    }
    
    
    public ClauseComponent findClauseById(int clauseId){
        TypedQuery<ClauseComponent> query = em.createNamedQuery("Clause.findClauseById", ClauseComponent.class);
        return query.setParameter("clauseId", clauseId).getSingleResult();
    }
    
}
