package repository;

import domain.ClimateChart;
import domain.MonthOfTheYear;
import domain.Months;
import java.sql.SQLException;
import java.util.ArrayList;
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
        
        return query.setParameter("countryID", countryId).getResultList();
    }

    public ClimateChart getClimateChartByClimateChartID(int chartId) {
        TypedQuery<ClimateChart> query = em.createNamedQuery("ClimateChart.findById", ClimateChart.class);
        ClimateChart c = query.setParameter("chartId", chartId).getSingleResult();
        String sql = "select m.MonthID, MonthProp,AverTemp,Sediment from ClimateCharts join ClimateChartMonth on ClimateCharts.ClimateChartID = ClimateChartMonth.ClimateChartId join Months m on ClimateChartMonth.MonthId = m.MonthID where ClimateCharts.ClimateChartID =" + chartId;
        List<Object[]> tuples = (List<Object[]>) em.createNativeQuery(sql).getResultList();
        List<Months> monthList = new ArrayList<>();
        for (Object[] tuple : tuples) {
            
            int id = (int) tuple[0];
            MonthOfTheYear m = MonthOfTheYear.values()[(int) tuple[1]];
            double temp = (double) tuple[2];
            int sed = (int) tuple[3];
            monthList.add(new Months(id,m,temp,sed));
        }
        c.setMonths(monthList);
        return c;
    }
    
    public void updateClimateChart()
    {
        em.getTransaction().begin();
        em.getTransaction().commit();
    }
    public void updateTemp(int id,double temp)
    {
      em.getTransaction().begin();
     em.createNativeQuery("UPDATE Months SET AverTemp="+temp+" WHERE MonthID="+id).executeUpdate();
      
        em.getTransaction().commit();
    }

    void updateSed(int id, int sed) {
         em.getTransaction().begin();
   em.createNativeQuery("UPDATE Months SET Sediment="+sed+" WHERE MonthID="+id).executeUpdate();
        em.getTransaction().commit();
    }

    void insertClimateChart(ClimateChart c) {
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
         }
}
