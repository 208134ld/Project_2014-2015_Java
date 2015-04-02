package domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ContinentBeheerder {

    public final String PERSISTENCE_UNIT_NAME = "HOGENT1415_11";
    private EntityManager em;
    private EntityManagerFactory emf;
    //private Map<String, Vervoermiddel> vervoerMap = new HashMap<>();

    public ContinentBeheerder() {
        initializePersistentie();
    }

    private void initializePersistentie() {
        openPersistentie();
        //GarageData od = new GarageData(this);
        //od.populeerData();
    }

    private void openPersistentie() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager(); //Vragen om een EntityManager te krijgen
    }

    public void closePersistentie() {
        em.close();
        emf.close();
    }

    public List<Continent> getAllContinentsJPA() throws SQLException {

        List<Continent> continents = new ArrayList<>();
        
        List<Object[]> tuples = (List<Object[]>) em.createNativeQuery("SELECT * FROM Continents").getResultList();

        
        for (Object[] tuple : tuples) 
        {
            System.out.println((int)tuple[0]);
            continents.add(new Continent((String)tuple[1], (int)tuple[0]));
//            Continent c = new Continent();
//            c.setId((int)tuple[0]);
//            c.setName((String)tuple[1]);
//            continents.add(c);
        }
        
        
        return continents;
        //return em.createNativeQuery("SELECT * FROM Continents").getResultList();
        //return em.createNamedQuery("SELECT * FROM Continents").getResultList();
    }
}
