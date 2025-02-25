package repository;

import domain.ClimateChart;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

public class ClimateChartRepository {

    private EntityManager em;

    public ClimateChartRepository() {
        this.em = JPAUtil.getEntityManager();
    }

    public List<ClimateChart> getClimateChartsOfCountry(int countryId) {
        TypedQuery<ClimateChart> query = em.createNamedQuery("ClimateChart.findByCountry", ClimateChart.class);
        List<ClimateChart> lijst = query.setParameter("countryID", countryId).getResultList();
        Collections.sort(lijst);
        return lijst;
    }

    public ClimateChart getClimateChartByClimateChartID(int chartId) {
        TypedQuery<ClimateChart> query = em.createNamedQuery("ClimateChart.findById", ClimateChart.class);
        return query.setParameter("chartId", chartId).getSingleResult();
    }

    public void updateClimateChart() {
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    public void updateTemp(int id, double temp) {
        em.getTransaction().begin();
        em.createNativeQuery("UPDATE Months SET AverTemp=" + temp + " WHERE MonthID=" + id).executeUpdate();

        em.getTransaction().commit();
    }

    void updateSed(int id, int sed) {
        em.getTransaction().begin();
        em.createNativeQuery("UPDATE Months SET Sediment=" + sed + " WHERE MonthID=" + id).executeUpdate();
        em.getTransaction().commit();
    }

    public List<ClimateChart> findAll() {
        TypedQuery<ClimateChart> query = em.createNamedQuery("ClimateChart.findAll", ClimateChart.class);
        return query.getResultList();
    }

    void insertClimateChart(ClimateChart c) {
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }

    void deleteClimatechart(int climatechartId) {
        em.getTransaction().begin();
        em.remove(this.getClimateChartByClimateChartID(climatechartId));
        em.getTransaction().commit();
    }
    
    public ClimateChart findByName(String name) {
        TypedQuery<ClimateChart> query = em.createNamedQuery("ClimateChart.findByName", ClimateChart.class);
        return query.setParameter("climateChartName", name).getSingleResult();
    }

}
