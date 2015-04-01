/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistentie;

import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bremme windows
 */
public class ContinentRepository extends Repository {
    
    private Connection connection;
    String url = "jdbc:sqlserver://localhost:1433;databaseName=HOGENT1415_11";
    String user = "sa";
    String password = "root";
    Statement statement;
    
    public ContinentRepository() throws SQLException{
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }
    
    public List<Continent> getAllContinents() throws SQLException
    {
//        getEm().getTransaction().begin();
//        List<Continent> continents = getEm().createQuery("SELECT * from continents").getResultList();
//        getEm().getTransaction().commit();
//        getEm().close();
        
        List<Continent> continents = new ArrayList<>();
        String sql = "select * from Continents";
        ResultSet result = statement.executeQuery(sql);
        
        while(result.next()) {
            continents.add(new Continent(result.getString("name"),result.getInt("ContinentID")));
        }
        
        return continents;
    }
    
    public List<Country> getCountriesOfContinent(int continentId) throws SQLException{
        List<Country> countries = new ArrayList<>();
        String sql = "select * from countries where ContinentID = "+continentId;
        ResultSet result = statement.executeQuery(sql);
        
        while(result.next()) {
            countries.add(new Country(result.getString("Name"), result.getInt("CountryID")));
        }
//        getEm().getTransaction().begin();
//        List<Country> countries = getEm().createQuery("SELECT * from countries where ContinentID = "+continentId).getResultList();
//        getEm().getTransaction().commit();
//        getEm().close();
        return countries;
    }
    
    public List<ClimateChart> getClimateChartsOfCountry(int countryId) throws SQLException{
        List<ClimateChart> climateCharts = new ArrayList<>();
        String sql = "select * from ClimateCharts where CountryID = "+countryId;
        ResultSet result = statement.executeQuery(sql);
        
        while(result.next()) {
            climateCharts.add(new ClimateChart(result.getString("Location"), result.getInt("ClimateChartID")));
        }
        
        return climateCharts;
    }
    
    public ClimateChart getClimateChartByClimateChartID(int cID)
    {
        String loc="";
        int begin=0;
        int einde=0;
        int countryID=0;
        double longi=0;
        double lat = 0;
        boolean aboveEq=false;
        Country c = null;
        
        String sql = "select * from ClimateCharts where ClimateChartID ="+cID;
        ResultSet result;
        try {
            result = statement.executeQuery(sql);
            while(result.next()){
            loc = result.getString("Location");
            begin = result.getInt("BeginPeriod");
            einde = result.getInt("EndPeriod");
            aboveEq = result.getBoolean("AboveEquator");
            lat = result.getDouble("Latitude");
            longi = result.getDouble("Longitude");
            countryID = result.getInt("CountryID");
        }
        } catch (SQLException ex) {
            Logger.getLogger(ContinentRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            
            result = statement.executeQuery("select * from Countries where CountryID="+countryID);
            while(result.next()){
                c = new Country(result.getString("Name"),result.getInt("CountryID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContinentRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
       int[] temp =new int[12];
       int[] sed = new int[12];
       int counter = 0;
        sql = "select AverTemp, Sediment from ClimateCharts join ClimateChartMonth on ClimateCharts.ClimateChartID = ClimateChartMonth.ClimateChartId join Months on ClimateChartMonth.MonthId = Months.MonthID where ClimateCharts.ClimateChartID ="+cID;
        try {
            result = statement.executeQuery(sql);
            while(result.next()){
            temp[counter] = result.getInt("AverTemp");
            sed[counter] = result.getInt("Sediment");
            counter++;
            
        }
        } catch (SQLException ex) {
            Logger.getLogger(ContinentRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        ClimateChart climate = new ClimateChart(loc,begin,einde,temp,sed,lat,longi,countryID);
        
        climate.setCountry(c);
        return climate;
    }
    
//    public void insertContinent(String name) throws SQLException{
//        String sql = "INSERT INTO Continents (Name) VALUES ('"+name+"')";
//        statement.executeUpdate(sql);
//    }
    
}
