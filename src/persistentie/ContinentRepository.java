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
            climateCharts.add(new ClimateChart(result.getString("Location")));
        }
        
        return climateCharts;
    }
    
}
