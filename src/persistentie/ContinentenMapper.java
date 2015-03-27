/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domain.Continent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Stijn
 */
public class ContinentenMapper {

    private static String[] continenten = {"Europa","Afrika","Azië"};
	
    public List<String> geefContinenten()
    {
            return new ArrayList<>(Arrays.asList(continenten));
    }
}
