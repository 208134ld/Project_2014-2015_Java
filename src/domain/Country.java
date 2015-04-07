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
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author bremme windows
 */
@Entity(name="Countries")
@Table(name = "Countries")
@NamedQueries({
    @NamedQuery(name="Country.findAll",
                query="SELECT c FROM Countries c"),
    @NamedQuery(name="Country.findCountriesByContinent",
                query="SELECT c FROM Countries c WHERE c.continent.continentId = :continentID")
}) 
public class Country implements Serializable {
    
    
//    @Transient
//    private final String name = new SimpleStringProperty();
    
    private String name;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CountryID")
    private int countryId ;
    
    
//    @OneToMany(mappedBy="country")
//    private ObservableList<ClimateChart> climateCharts;
//    
    
    @ManyToOne
    @JoinColumn(name = "ContinentID")
    private Continent continent;

    public Country(){}
    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public Country(String name, int id) {
        setName(name);
        setId(id);
        //climateCharts = FXCollections.observableArrayList();
    }
    
    public Country(String name, Continent c) {
        setName(name);
        setContinent(c);
        //climateCharts = FXCollections.observableArrayList();
    }
    
    public Country(String name, int id, int continentId) {
        setName(name);
        setId(id);
        //climateCharts = FXCollections.observableArrayList();
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
        return countryId;
    }
    
    public void setId(int id){
        this.countryId=id;
    }
    
//    public ObservableList<ClimateChart> getClimateCharts(){
//        return climateCharts;
//    }
//    
//    public ClimateChart getClimateChart(int climateChartId){
//         return climateCharts.stream().filter(e->e.getId() == climateChartId).findFirst().get();
//    }
}
