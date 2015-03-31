/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author bremme windows
 */
public class Months {
    private IntegerProperty sed;
    private IntegerProperty temp;
    private MonthOfTheYear month;

    public MonthOfTheYear getMonth() {
        return month;
    }

    public void setMonth(MonthOfTheYear month) {
        this.month = month;
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
        this.month = month;
    }
}
