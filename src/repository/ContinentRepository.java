/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository;

import domain.Continent;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.TreeItem;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;
import util.MyNode;

/**
 *
 * @author bremme windows
 */
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
