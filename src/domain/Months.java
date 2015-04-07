
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
import javax.persistence.Transient;

@Entity
public class Months implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MonthID")
    private int monthId;
    @Transient
    private final SimpleIntegerProperty sed = new SimpleIntegerProperty();
    @Transient
    private final SimpleDoubleProperty temp = new SimpleDoubleProperty();
    @Transient
    private final ObjectProperty<MonthOfTheYear> month  = new SimpleObjectProperty<>();
    
    public Months(){}
    
    public Months(int sed,int temp,MonthOfTheYear month)
    {
        setSed(sed);
        setTemp(temp);
        setMonth(month);
    }
    
    public Months(int sed,int temp,MonthOfTheYear month, int id)
    {
        setMonthId(id);
        setSed(sed);
        setTemp(temp);
        setMonth(month);
    }
    
    public int getMonthId() {
        return monthId;
    }

    public void setMonthId(int value) {
        monthId = value;
    }
    
    @Access(AccessType.PROPERTY)
    public int getSed() {
        return sed.get();
    }

    public void setSed(int value) {
        sed.set(value);
    }

    public IntegerProperty sedProperty() {
        return sed;
    }
    
    @Access(AccessType.PROPERTY)
    public double getTemp() {
        return temp.get();
    }

    public void setTemp(int value) {
        temp.set(value);
    }

    public DoubleProperty tempProperty() {
        return temp;
    }

    @Access(AccessType.PROPERTY)
    public MonthOfTheYear getMonth() {
        return month.get();
    }

    public void setMonth(MonthOfTheYear value) {
        month.set(value);
    }

    public ObjectProperty monthProperty() {
        return month;
    }

    
    
}
