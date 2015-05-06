package domain;

import java.io.Serializable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
    private MonthOfTheYear month;
    @Column(name="AverTemp")
    private double temp;
    @Column(name="Sediment")
    private int sed;

    @Transient
    private SimpleIntegerProperty sedProp;
    @Transient
    private SimpleDoubleProperty tempProp;
    @Transient
    private SimpleStringProperty monthProp;

    @JoinColumn(name = "ClimateChartID")
    private ClimateChart climateChart;

    public Months() {
    }

    public Months(int sed, double temp, MonthOfTheYear month) {
        setSediment(sed);
        setAverTemp(temp);
        setMonth(month);
        
    }

    public Months(int id, MonthOfTheYear month, double temp, int sed) {
        setMonthId(id);
        setSediment(sed);
        setAverTemp(temp);
        setMonth(month);
    }

    public ClimateChart getClimateChart() {
        return climateChart;
    }

    public void setClimateChart(ClimateChart climateChart) {
        this.climateChart = climateChart;
    }

    public int getMonthId() {
        return monthId;
    }

    public void setMonthId(int value) {
        monthId = value;
    }
    
    
    public MonthOfTheYear getMonth() {
        return month;
    }

    public void setMonth(MonthOfTheYear month) {
        this.month = month;
    }
    
    public double getAverTemp() {
        return temp;
    }

    public void setAverTemp(double temp) {
        this.temp = temp;
    }

    public int getSediment() {
        return sed;
    }

    public void setSediment(int sediment) {
        this.sed = sediment;
    }
    
    public ObservableValue<String> monthProperty() { 
        
        this.monthProp = new SimpleStringProperty(month.name());
        return monthProp;
    }

    public ObservableValue<Number> temperatureProperty() {
        this.tempProp = new SimpleDoubleProperty(temp);
        return tempProp;
    }
    
    public ObservableValue<Number> sedimentProperty() {
        this.sedProp = new SimpleIntegerProperty(sed);
        return sedProp;
    }
}
