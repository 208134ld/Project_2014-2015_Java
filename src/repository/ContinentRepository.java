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
public class ContinentRepository {
    
    private EntityManager em;
    
    public ContinentRepository() throws SQLException{
        this.em = JPAUtil.getEntityManager();
    }
    
    public List<Continent> getAllContinents() throws SQLException
    {
        List<Continent> continents = new ArrayList<>();
        
        List<Object[]> tuples = (List<Object[]>) em.createNativeQuery("SELECT * FROM Continents").getResultList();

        for (Object[] tuple : tuples) 
        {
            continents.add(new Continent((String)tuple[1], (int)tuple[0]));
        }
        return continents;
    }


//    public void insertContinent(String name) throws SQLException{
//        String sql = "INSERT INTO Continents (Name) VALUES ('"+name+"')";
//        statement.executeUpdate(sql);
//    }
    
}
