
package domain;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.*;

/**
 *
 * @author Samsung & CrazyB
 */
@Entity
public class Continent {
    @Column(name = "Name")
    private StringProperty name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContinentID")
    private IntegerProperty continentId;
    @OneToMany(mappedBy="continent")
    private ObservableList<Country> countries;

    public Continent(){};
    public Continent(String naam) {
        name = new SimpleStringProperty(naam);
        continentId = new SimpleIntegerProperty();
        countries = FXCollections.observableArrayList();
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
    public final void SetId(int id)
    {
        this.continentId.set(id);
    }

    public StringProperty getNameProp() {
        return name;
    }

    public IntegerProperty getIdProp() {
        return continentId;
    }
    public ObservableList<Country> getCountries()
    {
        return countries;
    }
    public Country getCountry(int countryId)
    {
        return countries.stream().filter(e->e.getId() == countryId).findFirst().get();
    }
   
    
    
}
