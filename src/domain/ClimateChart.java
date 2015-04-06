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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

/**
 *
 * @author bremme windows
 */
@Entity
@Access(AccessType.FIELD)
@NamedQuery(name="FindByCID",query="SELECT cl FROM ClimateChart Cl where cl.climateChartId = :CID")
public class ClimateChart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClimateChartID")
    private int climateChartId;

    @Transient
    private final StringProperty location = new SimpleStringProperty();
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
    private int countryId;
    private boolean aboveEquator;
    private List<Months> months;

    public ClimateChart() {
    }

    public ClimateChart(int id, String loc, int begin, int end, int[] temp, int[] sed, double latitude, double longitude,String LCord,String BCord) {
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
    
    public ClimateChart(int id, String loc, int begin, int end, boolean equator, double latitude, double longitude,String LCord,String BCord, int countryId) {
        setLocation(loc);
        setClimateChartId(id);
        setBeginperiod(begin);
        setEndperiod(end);
        setLatitude(latitude);
        setLongitude(longitude);
        setLCord(LCord);
        setBCord(BCord);
        months = new ArrayList<Months>();
        this.countryId = countryId;
    }

    public ClimateChart(String location, int id) {
        setLocation(location);
        this.climateChartId = id;
    }
    
    public int getCountryId(){
        return this.countryId;
    }

    @Access(AccessType.PROPERTY)
    public String getLocation() {
        return location.get();
    }

    public void setLocation(String value) {
        location.set(value);
    }

    public StringProperty locationProperty() {
        return location;
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

}
