/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import javafx.beans.property.*;
import static javafx.beans.property.DoubleProperty.doubleProperty;
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
    private int climateChartId;
    @ManyToOne
    private Country country;
    private IntegerProperty beginperiod, endperiod;
    private int[] sedimentArray,TempArray;
    private boolean aboveEquator;
    private DoubleProperty latidude,longitude;
    public ClimateChart(){}

    public void setClimateChartId(int climateChartId) {
        this.climateChartId = climateChartId;
    }
    
    public void setBeginperiod(int b)
    {
        this.beginperiod.set(b);
    }
    public  int getBeginperiod()
    {
        return this.beginperiod.get();
    }
    public void setEndperiod(int e)
    {
        this.endperiod.set(e);
    }
    public  int getEndperiod()
    {
        return this.endperiod.get();
    }

    public int[] getSedimentArray() {
        return sedimentArray;
    }

    public void setSedimentArray(int[] sedimentArray) {
        this.sedimentArray = sedimentArray;
    }

    public int[] getTempArray() {
        return TempArray;
    }

    public void setTempArray(int[] TempArray) {
        this.TempArray = TempArray;
    }

    public boolean isAboveEquator() {
        return aboveEquator;
    }

    public void setAboveEquator(boolean aboveEquator) {
        this.aboveEquator = aboveEquator;
    }
    
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    
    public void ClimateChart(String location)
    {
        this.location = new SimpleStringProperty(location);
       
        
    }
    public void ClimateChart(String loc,int begin,int end,int[]temp,int[]sed,double latidude,double longitude,int id)
    {
        this.location = new SimpleStringProperty(loc);
        setClimateChartId(id);
        this.endperiod = new SimpleIntegerProperty(end); 
        this.beginperiod = new SimpleIntegerProperty(begin);
        setTempArray(temp);
        setSedimentArray(sed);
        this.latidude = new SimpleDoubleProperty(latidude);
        this.longitude = new SimpleDoubleProperty(longitude);
        
    }
    public StringProperty getLocationProp() {
        return location;
    }


    public String getLocation() {
        return location.get();
    }

    public Integer getId() {
        return climateChartId;
    }

    public double getLatidude() {
        return latidude.get();
    }

    public void setLatidude(double latidude) {
        this.latidude.set(latidude);
    }

    public double getLongitude() {
        return longitude.get();
    }

    public void setLongitude(double longitude) {
        this.longitude.set(longitude);
    }

    
}
