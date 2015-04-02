
package domain;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.*;

/**
 *
 * @author Samsung & CrazyB
 */
@Entity
@Table
public class Continent implements Serializable {
    @Column(name = "Name")
    private StringProperty name;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContinentID")
    private IntegerProperty continentId;
    @OneToMany(mappedBy="continent")
    private Connection connection;
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
