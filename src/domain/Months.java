package domain;

import java.io.Serializable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name = "Months")
@Table(name = "Months")
@NamedQueries({
    @NamedQuery(name = "Months.findById", 
            query = "SELECT m FROM Months m WHERE m.monthId = :MonthID"),
    @NamedQuery(name = "Months.findMonthsByClimateChart",
            query = "SELECT m FROM Months m WHERE m.climateChart.climateChartId = :climateChartID")
})
public class Months implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MonthID")
    private int monthId;
    @Column(name="MonthProp")
    private MonthOfTheYear monthProp;
    @Column(name="AverTemp")
    private double averTemp;
    @Column(name="Sediment")
    private int sediment;
    

    @Transient
    private final SimpleIntegerProperty sed = new SimpleIntegerProperty();
    @Transient
    private final SimpleDoubleProperty temp = new SimpleDoubleProperty();
    @Transient
    private final ObjectProperty<MonthOfTheYear> month = new SimpleObjectProperty<>();

    @JoinColumn(name = "ClimateChartID")
    private ClimateChart climateChart;

    public Months() {
    }

    public Months(int sed, double temp, MonthOfTheYear month) {
        setSediment(sed);
        setAverTemp(temp);
        setMonthProp(month);
        
    }

    public Months(int id, MonthOfTheYear month, double temp, int sed) {
        setMonthId(id);
        setSediment(sed);
        setAverTemp(temp);
        setMonthProp(month);
    }

    public int getMonthId() {
        return monthId;
    }

    public void setMonthId(int value) {
        monthId = value;
    }
    
    
    public MonthOfTheYear getMonthProp() {
        return monthProp;
    }

    public void setMonthProp(MonthOfTheYear monthProp) {
        this.monthProp = monthProp;
    }
    
    public double getAverTemp() {
        return averTemp;
    }

    public void setAverTemp(double averTemp) {
        this.averTemp = averTemp;
    }

    public int getSediment() {
        return sediment;
    }

    public void setSediment(int sediment) {
        this.sediment = sediment;
    }
    
    public ClimateChart getClimateChart() {
        return climateChart;
    }

    public void setClimateChart(ClimateChart climateChart) {
        this.climateChart = climateChart;
    }

    @Access(AccessType.PROPERTY)
    public int getSed() {
        return sed.get();
    }
    
    public void setSed(){
        sed.set(sediment);
    }

    public IntegerProperty sedProperty() {
        return sed;
    }

    @Access(AccessType.PROPERTY)
    public double getTemp() {
        return temp.get();
    }
    
    public void setTemp(){
        temp.set(averTemp);
    }

    public DoubleProperty tempProperty() {
        return temp;
    }

    @Access(AccessType.PROPERTY)
    public MonthOfTheYear getMonth() {
        return month.get();
    }
    
    public void setMonth(){
        month.set(monthProp);
    }

    public ObjectProperty monthProperty() {
        return month;
    }

}
