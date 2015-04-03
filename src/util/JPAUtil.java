package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author BremmeWindows
 */
public class JPAUtil 
{
    private static final String PERSISTENCE_UNIT_NAME = "HOGENT1415_11";
    private static EntityManagerFactory entityManagerFactory;
    private static ThreadLocal<EntityManager> manager = new ThreadLocal<EntityManager>();
    
    public static synchronized EntityManager getEntityManager()
    {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        
        EntityManager em = manager.get();
        
        if (em == null || !em.isOpen()) {
            em = entityManagerFactory.createEntityManager();
            manager.set(em);
        }
        return em;
    }

    public static void closeEntityManager() {
        EntityManager em = manager.get();
        if (em != null) {
            EntityTransaction tx = em.getTransaction();
            if (tx.isActive()) { 
                    tx.commit();
            }
            em.close();
            manager.set(null);
        }
    }

    public static void closeEntityManagerFactory(){
        closeEntityManager();
        entityManagerFactory.close();
    }

    public JPAUtil() {}
    
}