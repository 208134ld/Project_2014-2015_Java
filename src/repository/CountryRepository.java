package repository;

import domain.Country;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import util.JPAUtil;

public class CountryRepository {

    private EntityManager em;

    public CountryRepository() {
        this.em = JPAUtil.getEntityManager();
    }

    public List<Country> getAllCountries() {
        TypedQuery<Country> query = em.createNamedQuery("Country.findAll", Country.class);
        List<Country> lijst = query.getResultList();
        Collections.sort(lijst);
        return lijst;
    }

    public List<Country> getCountriesOfContinent(int continentId) {
        TypedQuery<Country> query = em.createNamedQuery("Country.findCountriesByContinent", Country.class);
        List<Country> lijst = query.setParameter("continentID", continentId).getResultList();
        Collections.sort(lijst);
        return lijst;
    }

    public void insertCountry(Country c) {
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }

    public void deleteCountry(int id) {
        em.getTransaction().begin();
        em.remove(findCountryById(id));
        em.getTransaction().commit();
    }

    public Country findCountryById(int id) {
        TypedQuery<Country> query = em.createNamedQuery("Country.findById", Country.class);
        return query.setParameter("countryID", id).getSingleResult();
    }

    public Country findCountryByName(String name) {
        TypedQuery<Country> query = em.createNamedQuery("Country.findByName", Country.class);
        return query.setParameter("countryName", name).getSingleResult();
    }
}
