/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository;

import domain.Continent;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
        TypedQuery<Continent> query = em.createNamedQuery("Continent.findAllContinents", Continent.class);
        return query.getResultList();
    }


//    public void insertContinent(String name) throws SQLException{
//        String sql = "INSERT INTO Continents (Name) VALUES ('"+name+"')";
//        statement.executeUpdate(sql);
//    }
    
}
