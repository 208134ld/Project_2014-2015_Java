
package repository;

import domain.Continent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

public class ContinentRepository {
    
    private EntityManager em;
    
    public ContinentRepository(){
        this.em = JPAUtil.getEntityManager();
    }
    
    public List<Continent> getAllContinents()
    {
        TypedQuery<Continent> query = em.createNamedQuery("Continent.findAllContinents", Continent.class);
        return query.getResultList();
    }

    public void insertContinent(Continent c){
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }
    
    public Continent findContinentById(int id){
        TypedQuery<Continent> query = em.createNamedQuery("Continent.findById", Continent.class);
        return query.setParameter("continentID", id).getSingleResult();
    }
    
}
