/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistentie;

import javax.persistence.EntityManager;
import util.JPAUtil;

/**
 *
 * @author bremme windows
 */
public abstract class Repository {
    protected EntityManager em;
    
    public EntityManager getEm()
    {
        if(em==null)
        {
            
           return JPAUtil.getEntityManager();
        }
        return em;
    }
}
