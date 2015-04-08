package repository;

import domain.ClimateChart;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

public class ClimateChartRepository {

    private EntityManager em;

    public ClimateChartRepository() throws SQLException {
        this.em = JPAUtil.getEntityManager();
    }

    public List<ClimateChart> getClimateChartsOfCountry(int countryId) throws SQLException {
        TypedQuery<ClimateChart> query = em.createNamedQuery("ClimateChart.findByCountry", ClimateChart.class);
        return query.setParameter("countryID", countryId).getResultList();
    }

    public ClimateChart getClimateChartByClimateChartID(int chartId) {
        TypedQuery<ClimateChart> query = em.createNamedQuery("ClimateChart.findById", ClimateChart.class);
        return query.setParameter("chartId", chartId).getSingleResult();
    }
public void updateClimateChart(int id,String LCord,String BCord,int bP,int eP,double longi,double lat)
{
        em.getTransaction().begin();
        em.getTransaction().commit();
}
    public void updateLatitude(Integer id, double value) {
        em.getTransaction().begin();
        getClimateChartByClimateChartID(id).setLatitude(value);
        em.getTransaction().commit();
    }

    public void updateLongitude(int id, double value) {
        em.getTransaction().begin();
        getClimateChartByClimateChartID(id).setLongitude(value);
        em.getTransaction().commit();
    }
}
