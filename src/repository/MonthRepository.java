/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domain.Country;
import domain.Months;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

/**
 *
 * @author Logan Dupont
 */
public class MonthRepository {
     private EntityManager em;
    
    public MonthRepository(){
        this.em = JPAUtil.getEntityManager();
    }
    
    public List<Months> getMonthsOfClimateChart(int climateChartId){
        TypedQuery<Months> query = em.createNamedQuery("Months.findMonthsByClimateChart", Months.class);
        return query.setParameter("climateChartID", climateChartId).getResultList();
        
    }
    
    public void insertMonth(Months m){
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
    }
    
    public Months findMonthById(int id){
        TypedQuery<Months> query = em.createNamedQuery("Months.findById", Months.class);
        return query.setParameter("monthID", id).getSingleResult();
    }
}
