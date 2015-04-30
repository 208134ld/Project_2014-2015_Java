package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
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

/**
 *
 * @author bremme windows
 */
@Entity(name = "ClimateCharts")
@Table(name = "ClimateCharts")
@NamedQueries({
    @NamedQuery(name = "ClimateChart.findByCountry",
            query = "SELECT c FROM ClimateCharts c WHERE c.country.countryId = :countryID"),
    @NamedQuery(name = "ClimateChart.findById",
            query = "SELECT c FROM ClimateCharts c WHERE c.climateChartId = :chartId")
})
public class ClimateChart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClimateChartID")
    private int climateChartId;

    private String location;
    @Column(name = "BeginPeriod")
    private int beginperiod;
    @Column(name = "EndPeriod")
    private int endperiod;
    @Column(name = "Latitude")
    private double latitude;
    @Column(name = "Longitude")
    private double longitude;
    @Column(name = "LCord")
    private String LCord;
    @Column(name = "BCord")
    private String BCord;
    @Column(name = "AboveEquator")
    private boolean aboveEquator;
    @OneToMany(mappedBy = "climateChart",cascade = CascadeType.PERSIST)
    private List<Months> months;
    @ManyToOne
    @JoinColumn(name = "CountryID")
    private Country country;

    public ClimateChart() {
        months = new ArrayList<>();
    }

    public ClimateChart(int id, String loc, int begin, int end, double[] temp, int[] sed, double latitude, double longitude,String BCord,String LCord) {
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
    public ClimateChart(Country country, String loc, int begin, int end, String BC, String LC, List<Months> ms) {
        months = new ArrayList<>();
        setCountry(country);
        setLocation(loc);
        setBeginperiod(begin);
        setBCord(BC);
        setLCord(LC);
        setEndperiod(end);
        setMonths(ms);
    }

//    public ClimateChart(int id, String loc, int begin, int end, boolean equator, double latitude, double longitude,String BCord,String LCord, int countryId) {
//        setLocation(loc);
//        setClimateChartId(id);
//        setBeginperiod(begin);
//        setEndperiod(end);
//        setLatitude(latitude);
//        setLongitude(longitude);
//        setLCord(LCord);
//        setBCord(BCord);
//        months = new ArrayList<Months>();
////        this.countryId = countryId;
//    }
    public ClimateChart(String location, int id) {
        setLocation(location);
        this.climateChartId = id;
    }
    
    public void setCountry(Country c) {
        this.country = c;
    }

    public Country getCountry() {
        return country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String value) {
        location = value;
    }

//    public int getBeginperiod() {
//        return beginperiod.get();
//    }
//
//    public void setBeginperiod(int value) {
//        beginperiod.set(value);
//    }
//
//    public IntegerProperty beginperiodProperty() {
//        return beginperiod;
//    }
//   public int getEndperiod() {
//        return endperiod.get();
//    }
//    public void setEndperiod(int value) {
//        endperiod.set(value);
//    }
//
//    public IntegerProperty endperiodProperty() {
//        return endperiod;
//    }
//    @Access(AccessType.PROPERTY)
//    public double getLatitude() {
//        return latitude.get();
//    }
//
//    public void setLatitude(double value) {
//        latitude.set(value);
//    }
//
//    public DoubleProperty latitudeProperty() {
//        return latitude;
//    }
//    public void setLongitude(double value) {
//        longitude.set(value);
//    }
//
//    public DoubleProperty longitudeProperty() {
//        return longitude;
//    }
//    @Access(AccessType.PROPERTY)
//    public double getLongitude() {
//        return longitude.get();
//    }
//    @Access(AccessType.PROPERTY)
//    public String getLCord()
//    {
//        return LCord.get();
//    }
    //    public void setLCord(String v)
//    {
//        LCord.set(v);
//    }
    //    public StringProperty LCordProperty()
//    {
//        return LCord;
//    }
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getBeginperiod() {
        return beginperiod;
    }

    public void setBeginperiod(int beginperiod) {
        this.beginperiod = beginperiod;
    }

    public int getEndperiod() {

        return endperiod;
    }

    public void setEndperiod(int endperiod) {
        if (endperiod < this.beginperiod) {
            throw new IllegalArgumentException("Eind periode kan niet kleiner zijn dan begin");
        }
        this.endperiod = endperiod;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLCord() {
        return LCord;
    }

    public void setLCord(String LCord) {
        this.LCord = LCord;
    }

    public String getBCord() {
        return BCord;
    }

    public void setBCord(String BCord) {
        this.BCord = BCord;
    }

//    @Access(AccessType.PROPERTY)
//    public String getBCord()
//    {
//        return BCord.get();
//    }
//
//    public void setBCord(String v)
//    {
//        BCord.set(v);
//    }
//
//    public StringProperty BCordProperty()
//    {
//        return BCord;
//    }
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

    private void setMonthList(int[] sediments, double[] temperature) {
        if (temperature.length != 12 || sediments.length != 12) {
            throw new IllegalArgumentException("Er zijn meer dan 12 maanden in deze lijst");
        }
        int counter = 0;
        for (MonthOfTheYear m : MonthOfTheYear.values()) {
            months.add(new Months(sediments[counter], temperature[counter], m));
            counter++;
        }
    }

    public String giveCords(int degree, int minutes, int seconds) {
        if (degree < 0 || minutes < 0 || seconds < 0) {
            throw new IllegalArgumentException("Coordinaatwaarden moeten positief zijn");
        }
        if (minutes > 60 || seconds > 60) {
            throw new IllegalArgumentException("minuten en seconden moeten kleiner zijn dan 60");
        }
        return degree + "° " + minutes + "' " + seconds + "\" ";
    }

    public double calcDecimals(int degree, int min, int sec, String par) {
        double val;
        float f;
        f = min;
        f = f / 60;
        val = degree + f;
        f = sec;
        f = f / 3600;
        val = val + f;
        if (par.equalsIgnoreCase("zb") || par.equalsIgnoreCase("wl")) {
            val *= -1;
        }
        return Math.round(val * 1000000.0) / 1000000.0;
    }
    
    public OptionalDouble calcAverageYearTemp()
    {
        return months.stream().mapToDouble(m -> m.getAverTemp()).average();       
    }
    
    public int calcSedimentYear()
    {
        return months.stream().mapToInt(m -> m.getSediment()).sum();       
    }
}
