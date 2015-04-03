/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.io.Serializable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *
 * @author bremme windows
 */
@Entity
public class Months implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MonthID")
    private int monthId;
    @Transient
    private final SimpleIntegerProperty sed = new SimpleIntegerProperty();
    @Transient
    private final SimpleIntegerProperty temp = new SimpleIntegerProperty();
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
    public int getTemp() {
        return temp.get();
    }

    public void setTemp(int value) {
        temp.set(value);
    }

    public IntegerProperty tempProperty() {
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
