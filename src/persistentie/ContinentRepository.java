/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistentie;

import domain.Continent;
import java.util.List;

/**
 *
 * @author bremme windows
 */
public class ContinentRepository extends Repository {
    public List<Continent> getAllContinents()
    {
        getEm().getTransaction().begin();
        List<Continent> continents = getEm().createQuery("SELECT * from continents").getResultList();
        getEm().getTransaction().commit();
        getEm().close();
        return continents;
    }
}
