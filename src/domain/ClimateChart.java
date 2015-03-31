/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    private boolean aboveEquator;
    private DoubleProperty latitude,longitude;
    public ClimateChart(){}
    private List<Months> months;
    public void setClimateChartId(int climateChartId) {
        this.climateChartId = climateChartId;
    }

    public List<Months> getMonths() {
        return months;
    }

    public void setMonths(List<Months> months) {
        this.months = months;
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
    
    public ClimateChart(String location)
    {
        this.location = new SimpleStringProperty(location);
       
        
    }
    public ClimateChart(String loc,int begin,int end,int[]temp,int[]sed,double latidude,double longitude,int id)
    {
        this.location = new SimpleStringProperty(loc);
        setClimateChartId(id);
        this.endperiod = new SimpleIntegerProperty(end); 
        this.beginperiod = new SimpleIntegerProperty(begin);
        
        this.latitude = new SimpleDoubleProperty(latidude);
        this.longitude = new SimpleDoubleProperty(longitude);
        months = new ArrayList<Months>();
        setMonthList(sed,temp);
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
        return latitude.get();
    }

    public void setLatidude(double latidude) {
        this.latitude.set(latidude);
    }

    public double getLongitude() {
        return longitude.get();
    }

    public void setLongitude(double longitude) {
        this.longitude.set(longitude);
    }
    private void setMonthList(int[]sediments, int[]temperature)
    {
        if(temperature.length !=12||sediments.length!=12)
            throw new IllegalArgumentException("Er zijn meer dan 12 maanden in deze lijst");
        int counter=0;
        for(MonthOfTheYear m : MonthOfTheYear.values())
        {
            months.add(new Months(sediments[counter],temperature[counter],m));
            counter++;
        }
    }
    
}
