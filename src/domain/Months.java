/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author bremme windows
 */
public class Months {
    private SimpleIntegerProperty sed;
    private SimpleIntegerProperty temp;
    private ObjectProperty<MonthOfTheYear> month  = new SimpleObjectProperty<>();
    public SimpleIntegerProperty sedProperty(){return sed;}
    public SimpleIntegerProperty tempProperty(){return temp;}
    public ObjectProperty<MonthOfTheYear> monthProperty(){return month;}
    public MonthOfTheYear getMonth() {
        return month.get();
    }

    public void setMonth(MonthOfTheYear month) {
        this.month.set(month);
    }
    
    public int getSed()
    {
        return sed.get();
    }
    public int getTemp()
    {
        return temp.get();
    }
    public void setSed(int sediment)
    {
      sed.set(sediment);
    }
    public void setTemp(int temperature)
    {
        temp.set(temperature);
    }
    public Months(int sed,int temp,MonthOfTheYear month)
    {
        this.sed = new SimpleIntegerProperty(sed);
        this.temp = new SimpleIntegerProperty(temp);
        this.month.set(month);
       
    }
    
}
