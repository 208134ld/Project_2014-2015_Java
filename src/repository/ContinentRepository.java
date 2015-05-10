package repository;

import domain.Continent;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

public class ContinentRepository {

    private EntityManager em;

    public ContinentRepository() {
        this.em = JPAUtil.getEntityManager();
    }

    public List<Continent> getAllContinents() {
        TypedQuery<Continent> query = em.createNamedQuery("Continent.findAllContinents", Continent.class);
        List<Continent> lijst = query.getResultList();
        Collections.sort(lijst);
        return lijst;
    }
    
    public Continent getEurope(){
        TypedQuery<Continent> query = em.createNamedQuery("Continent.findEurope", Continent.class);
        return query.getSingleResult();
    }

    public void insertContinent(Continent c) {
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }

    public Continent findContinentById(int id) {
        TypedQuery<Continent> query = em.createNamedQuery("Continent.findById", Continent.class);
        return query.setParameter("continentID", id).getSingleResult();
    }

    public void deleteContinent(int id) {
        em.getTransaction().begin();
        em.remove(this.findContinentById(id));
        em.getTransaction().commit();
    }

    public Continent findByName(String name) {
        TypedQuery<Continent> query = em.createNamedQuery("Continent.findByName", Continent.class);
        return query.setParameter("name", name).getSingleResult();
    }
}
