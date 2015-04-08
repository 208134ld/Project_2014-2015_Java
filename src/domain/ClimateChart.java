/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.*;
import static javafx.beans.property.DoubleProperty.doubleProperty;
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
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author bremme windows
 */
@Entity(name="ClimateCharts")
@Table(name = "ClimateCharts")
@NamedQueries({
    @NamedQuery(name="ClimateChart.findByCountry",
                query="SELECT c FROM ClimateCharts c WHERE c.country.countryId = :countryID"),
    @NamedQuery(name="ClimateChart.findById",
                query="SELECT c FROM ClimateCharts c WHERE c.climateChartId = :chartId")
}) 
public class ClimateChart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClimateChartID")
    private int climateChartId;

    
    private String location;
    @Transient
    private final IntegerProperty beginperiod = new SimpleIntegerProperty();
    @Transient
    private final IntegerProperty endperiod = new SimpleIntegerProperty();
    @Transient
    private final DoubleProperty latitude = new SimpleDoubleProperty();
    @Transient
    private final DoubleProperty longitude = new SimpleDoubleProperty();
    @Transient
    private final StringProperty LCord = new SimpleStringProperty();
    @Transient 
    private final StringProperty BCord = new SimpleStringProperty();
    private boolean aboveEquator;
    private List<Months> months;
    @ManyToOne
    @JoinColumn(name = "CountryID")
    private Country country;
   

    public ClimateChart() {
    }

    public ClimateChart(int id, String loc, int begin, int end, int[] temp, int[] sed, double latitude, double longitude,String BCord,String LCord) {
        setLocation(loc);
        setClimateChartId(id);
        setBeginperiod(begin);
        setEndperiod(end);
        setLatitude(latitude);
        setLongitude(longitude);
        setLCord(LCord);
        setBCord(BCord);
        months = new ArrayList<Months>();
        setMonthList(sed, temp);
    }
    
    public ClimateChart(int id, String loc, int begin, int end, boolean equator, double latitude, double longitude,String BCord,String LCord, int countryId) {
        setLocation(loc);
        setClimateChartId(id);
        setBeginperiod(begin);
        setEndperiod(end);
        setLatitude(latitude);
        setLongitude(longitude);
        setLCord(LCord);
        setBCord(BCord);
        months = new ArrayList<Months>();
//        this.countryId = countryId;
    }

    public ClimateChart(String location, int id) {
        setLocation(location);
        this.climateChartId = id;
    }
    public Country getCountry()
    {
        return country;
    }

     public String getLocation() {
        return location;
    }

    public void setLocation(String value) {
        location = value;
    }
    @Access(AccessType.PROPERTY)
    public int getBeginperiod() {
        return beginperiod.get();
    }

    public void setBeginperiod(int value) {
        beginperiod.set(value);
    }

    public IntegerProperty beginperiodProperty() {
        return beginperiod;
    }

    @Access(AccessType.PROPERTY)
    public int getEndperiod() {
        return endperiod.get();
    }

    public void setEndperiod(int value) {
        endperiod.set(value);
    }

    public IntegerProperty endperiodProperty() {
        return endperiod;
    }

    @Access(AccessType.PROPERTY)
    public double getLatitude() {
        return latitude.get();
    }

    public void setLatitude(double value) {
        latitude.set(value);
    }

    public DoubleProperty latitudeProperty() {
        return latitude;
    }

    @Access(AccessType.PROPERTY)
    public double getLongitude() {
        return longitude.get();
    }
    @Access(AccessType.PROPERTY)
    public String getLCord()
    {
        return LCord.get();
    }
    @Access(AccessType.PROPERTY)
    public String getBCord()
    {
        return BCord.get();
    }
    public void setLCord(String v)
    {
        LCord.set(v);
    }
    public void setBCord(String v)
    {
        BCord.set(v);
    }
    public StringProperty LCordProperty()
    {
        return LCord;
    }
    public StringProperty BCordProperty()
    {
        return BCord;
    }
    public void setLongitude(double value) {
        longitude.set(value);
    }

    public DoubleProperty longitudeProperty() {
        return longitude;
    }

    public void setClimateChartId(int climateChartId) {
        this.climateChartId = climateChartId;
    }

    public List<Months> getMonths() {
        return months;
    }

    public void setMonths(List<Months> months) {
        this.months = months;
    }

    public boolean isAboveEquator() {
        return aboveEquator;
    }

    public void setAboveEquator(boolean aboveEquator) {
        this.aboveEquator = aboveEquator;
    }

    public Integer getId() {
        return climateChartId;
    }

    private void setMonthList(int[] sediments, int[] temperature) {
        if (temperature.length != 12 || sediments.length != 12) {
            throw new IllegalArgumentException("Er zijn meer dan 12 maanden in deze lijst");
        }
        int counter = 0;
        for (MonthOfTheYear m : MonthOfTheYear.values()) {
            months.add(new Months(sediments[counter], temperature[counter], m));
            counter++;
        }
    }
    public String giveCords(int degree,int minutes,int seconds)
    {
        if(degree<0||minutes<0||seconds<0)
            throw new IllegalArgumentException("Waarden moeten positief zijn");
        if(minutes>60||seconds>60)
            throw new IllegalArgumentException("minuten en seconden moeten kleiner zijn dan 60");
        return degree+"° "+minutes+"' "+seconds+"\" ";
    }
    public double calcDecimals(int degree,int min,int sec,String par)
    {
       double val;
       float f;
       f=min;
       f=f/60;
       val = degree+f;
       f=sec;
       f=f/3600;
       val = val+f;
       System.out.println(val);
        if(par.equalsIgnoreCase("zb")||par.equalsIgnoreCase("wl"))
            val *=-1;
        return Math.round (val * 1000000.0) / 1000000.0;
    }

}
