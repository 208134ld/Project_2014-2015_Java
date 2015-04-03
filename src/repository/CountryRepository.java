/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository;

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
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import util.JPAUtil;

/**
 *
 * @author bremme windows
 */
public class CountryRepository {
    
//    private Connection connection;
//    String url = "jdbc:sqlserver://localhost:1433;databaseName=HOGENT1415_11";
//    String user = "sa";
//    String password = "root";
//    Statement statement;
    
    private EntityManager em;
    
    public CountryRepository() throws SQLException{
//        connection = DriverManager.getConnection(url, user, password);
//        statement = connection.createStatement();
        this.em = JPAUtil.getEntityManager();
    }
    
    public List<Country> getAllCountries() throws SQLException{
        List<Country> countries = new ArrayList<>();
        
        List<Object[]> tuples = (List<Object[]>) em.createNativeQuery("SELECT * FROM Countries").getResultList();

        for (Object[] tuple : tuples) 
        {
            countries.add(new Country((String)tuple[1], (int)tuple[0], (int)tuple[2]));
        }
        return countries;
    }
    
    public List<Country> getCountriesOfContinent(int continentId) throws SQLException{
        List<Country> countries = new ArrayList<>();
        
        List<Object[]> tuples = (List<Object[]>) em.createNativeQuery("SELECT * FROM Countries WHERE ContinentID = " + continentId).getResultList();
        
        
        for (Object[] tuple : tuples) 
        {
            countries.add(new Country((String)tuple[1], (int)tuple[0], (int)tuple[2]));
        }
//        String sql = "select * from countries where ContinentID = "+continentId;
//        ResultSet result = statement.executeQuery(sql);
//        
//        while(result.next()) {
//            countries.add(new Country(result.getString("Name"), result.getInt("CountryID")));
//        }
//        getEm().getTransaction().begin();
//        List<Country> countries = getEm().createQuery("SELECT * from countries where ContinentID = "+continentId).getResultList();
//        getEm().getTransaction().commit();
//        getEm().close();
        return countries;
    }

    
}
