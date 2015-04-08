
package domain;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 *
 * @author Samsung & CrazyB
 */
@Entity(name="Continents")
@Table(name = "Continents")
@NamedQueries({
    @NamedQuery(name="Continent.findAllContinents",query= "select c from Continents c"),
    @NamedQuery(name="Continent.findById",
                query="SELECT c FROM Continents c WHERE c.continentId = :continentID")
}) 
public class Continent implements Serializable {

    private String name;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContinentID")
    private int continentId;
    
    @OneToMany(mappedBy="continent")
    List<Country> countries;
    
    
    public Continent(String naam){
        setName(naam);
    }
    
    protected Continent()
    {}
    
    public Continent(String naam,int id) throws SQLException
    {
        setName(naam);
        setId(id);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }
    
    public int getId() {
        return continentId;
    }
    
    public void setId(int id)
    {
        continentId = id;
    }
    
    public List<Country> getCountries()
    {
        return countries;
    }
    
    public Country getCountry(int countryId)
    {
        return countries.stream().filter(e->e.getId() == countryId).findFirst().get();
    }
    
    @Override
    public String toString()
    {
        return name;
    }

    
    
}
