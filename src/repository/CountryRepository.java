
package repository;

import domain.Country;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

public class CountryRepository {
    
    private EntityManager em;
    
    public CountryRepository() throws SQLException{
        this.em = JPAUtil.getEntityManager();
    }
    
    public List<Country> getAllCountries() throws SQLException{
        TypedQuery<Country> query = em.createNamedQuery("Country.findAll", Country.class);
        return query.getResultList();
    }
    
    public List<Country> getCountriesOfContinent(int continentId) throws SQLException{
        TypedQuery<Country> query = em.createNamedQuery("Country.findCountriesByContinent", Country.class);
        return query.setParameter("continentID", continentId).getResultList();
        
    }
    
    public void insertCountry(Country c){
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }
    
    public Country findCountryById(int id){
        TypedQuery<Country> query = em.createNamedQuery("Country.findById", Country.class);
        return query.setParameter("countryID", id).getSingleResult();
    }

    
}
