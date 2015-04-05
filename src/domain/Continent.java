
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
@NamedQuery(name="Continent.findAllContinents",query= "select c from Continents c")
public class Continent implements Serializable {

//    @Transient
//    private final StringProperty name = new SimpleStringProperty();
    
    private String name;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContinentID")
    private int continentId;
    
    @OneToMany(mappedBy="continent")
    List<Country> countries;
    
    
    public Continent(String naam) throws SQLException {
        setName(naam);
    }
    
    protected Continent()
    {}
    
    public Continent(String naam,int id) throws SQLException
    {
        setName(naam);
        setId(id);
        
    }
    
//    @Access(AccessType.PROPERTY)
//    public String getName() {
//        return name.get();
//    }
//
//    public void setName(String value) {
//        name.set(value);
//    }
//
//    public StringProperty nameProperty() {
//        return name;
//    }
    
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
        return getName();
    }
    
    
}
