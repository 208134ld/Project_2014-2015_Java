/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import javafx.beans.property.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



/**
 *
 * @author bremme windows
 */
@Entity
public class ClimateChart {

    @Column(name="Location")
    private StringProperty location;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClimateChartID")
    private IntegerProperty climateChartId;
    @ManyToOne
    private Country country;

    public ClimateChart(){}
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    
    public ClimateChart(String location)
    {
        this.location = new SimpleStringProperty(location);
        climateChartId = new SimpleIntegerProperty();
        
    }

    public StringProperty getLocationProp() {
        return location;
    }

    public IntegerProperty getClimateChartIdProp() {
        return climateChartId;
    }

    public String getLocation() {
        return location.get();
    }

    public Integer getId() {
        return climateChartId.get();
    }

    
}
