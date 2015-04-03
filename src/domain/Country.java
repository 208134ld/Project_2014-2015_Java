/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author bremme windows
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Country.findByName",
                query= "select c from Country c where c.name = :countryNaam")
})
public class Country implements Serializable {
    @Column(name="Name")
    private StringProperty name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CountryID")
    private IntegerProperty countryId ;
    @OneToMany(mappedBy="country")
    private ObservableList<ClimateChart> climateCharts;
    @ManyToOne
    private Continent continent;

    public Country(){}
    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public Country(String name, int id) {
        this.name = new SimpleStringProperty(name);
        countryId = new SimpleIntegerProperty(id);
        climateCharts = FXCollections.observableArrayList();
    }
    
    public Country(String name, int id, int continentId) {
        this.name = new SimpleStringProperty(name);
        countryId = new SimpleIntegerProperty(id);
        climateCharts = FXCollections.observableArrayList();
    }
    
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public Integer getId() {
        return countryId.get();
    }
    
    public final void SetId(int id){
        this.countryId.set(id);
    }

    public StringProperty getNameProp() {
        return name;
    }

    public IntegerProperty getIdProp() {
        return countryId;
    }
    
    public ObservableList<ClimateChart> getClimateCharts(){
        return climateCharts;
    }
    
    public ClimateChart getClimateChart(int climateChartId){
         return climateCharts.stream().filter(e->e.getId() == climateChartId).findFirst().get();
    }
}
