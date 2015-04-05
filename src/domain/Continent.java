
package domain;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 *
 * @author Samsung & CrazyB
 */
@Entity(name="Continents")
@Table(name = "Continents")
@NamedQuery(name="Continent.findAllContinents",query= "select c from Continents c")
public class Continent implements Serializable {
    @Column(name = "Name")
    private StringProperty name;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContinentID")
    private IntegerProperty continentId;
    @OneToMany(mappedBy="continent")
    List<Country> countries;
    
    
    public Continent(String naam) throws SQLException {
        name = new SimpleStringProperty(naam);
        continentId = new SimpleIntegerProperty();
    }
    
    protected Continent()
    {}
    
    public Continent(String naam,int id) throws SQLException
    {
        this(naam);
        setId(id);
    }
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
        
    }
    
    public Integer getId() {
        return continentId.get();
    }
    
    public final void setId(int id)
    {
        this.continentId.set(id);
    }

    public StringProperty getNameProp() {
        return name;
    }

    public IntegerProperty getIdProp() {
        return continentId;
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
        return getName();
    }
    
    
}
