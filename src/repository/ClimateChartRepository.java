package repository;

import domain.ClimateChart;
import domain.Country;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

public class ClimateChartRepository {

    private EntityManager em;

    public ClimateChartRepository() throws SQLException {
        this.em = JPAUtil.getEntityManager();
    }

    public List<ClimateChart> getClimateChartsOfCountry(int countryId) throws SQLException {
        List<ClimateChart> climateCharts = new ArrayList<>();

        List<Object[]> tuples = (List<Object[]>) em.createNativeQuery("SELECT * FROM ClimateCharts WHERE CountryID = " + countryId).getResultList();

        for (Object[] tuple : tuples) {
            climateCharts.add(new ClimateChart((int) tuple[0], (String) tuple[1], (int) tuple[2], (int) tuple[3], (boolean) tuple[4], (double) tuple[5], (double) tuple[6],(String) tuple[7],(String) tuple[8], (int) tuple[9]));
        }

        return climateCharts;
    }

    public ClimateChart getClimateChartByClimateChartID(int cID) {
        TypedQuery<ClimateChart> q = em.createNamedQuery("FindByCID",ClimateChart.class);
        return q.getSingleResult();
//        String loc = "";
//        int begin = 0;
//        int einde = 0;
//        int countryID = 0;
//        double longi = 0;
//        double lat = 0;
//        boolean aboveEq = false;
//        Country c = null;
//
//        List<Object[]> tuples = (List<Object[]>) em.createNativeQuery("SELECT * FROM ClimateCharts WHERE ClimateChartID = " + cID).getResultList();
//
//        for (Object[] tuple : tuples) {
//            loc = (String) tuple[1];
//            begin = (int) tuple[2];
//            einde = (int) tuple[3];
//            aboveEq = (boolean) tuple[4];
//            lat = (double) tuple[5];
//            longi = (double) tuple[6];
//            countryID = (int) tuple[7];
//        }
//
//        List<Object[]> tuples2 = (List<Object[]>) em.createNativeQuery("select AverTemp, Sediment from ClimateCharts join ClimateChartMonth on ClimateCharts.ClimateChartID = ClimateChartMonth.ClimateChartId join Months on ClimateChartMonth.MonthId = Months.MonthID where ClimateCharts.ClimateChartID =" + cID).getResultList();
//
//        for (Object[] tuple : tuples2) {
//            //System.out.println(tuple.length);
//            //System.out.println(tuple[1]);
//        }
////        try {
////            result = statement.executeQuery("select * from Countries where CountryID=" + countryID);
////            while (result.next()) {
////                c = new Country(result.getString("Name"), result.getInt("CountryID"));
////            }
////        } catch (SQLException ex) {
////            Logger.getLogger(ClimateChartRepository.class.getName()).log(Level.SEVERE, null, ex);
////        }
////        int[] temp = new int[12];
////        int[] sed = new int[12];
////        int counter = 0;
////        sql = "select AverTemp, Sediment from ClimateCharts join ClimateChartMonth on ClimateCharts.ClimateChartID = ClimateChartMonth.ClimateChartId join Months on ClimateChartMonth.MonthId = Months.MonthID where ClimateCharts.ClimateChartID =" + cID;
////        try {
////            result = statement.executeQuery(sql);
////            while (result.next()) {
////                temp[counter] = result.getInt("AverTemp");
////                sed[counter] = result.getInt("Sediment");
////                counter++;
////
////            }
////        } catch (SQLException ex) {
////            Logger.getLogger(ClimateChartRepository.class.getName()).log(Level.SEVERE, null, ex);
////        }
//        //ClimateChart climate = new ClimateChart(loc, begin, einde, temp, sed, lat, longi, countryID);
//
//        //return climate;
      
    }

    public void updateLatitude(Integer id, double antw) {
//         try {
//            statement.execute("update ClimateCharts set Latitude ="+antw+" where ClimateChartID = "+id);
//        } catch (SQLException ex) {
//           JOptionPane.showMessageDialog(null,"Kon de waarde niet updaten in de database", url,JOptionPane.ERROR_MESSAGE);
//        }
    }

    public void updateLongitude(int cID, double longi) {
//        try {
//            statement.execute("update ClimateCharts set Longitude ="+longi+" where ClimateChartID = "+cID);
//        } catch (SQLException ex) {
//           JOptionPane.showMessageDialog(null,"Kon de waarde niet updaten in de database", url,JOptionPane.ERROR_MESSAGE);
//        }
    }

}
