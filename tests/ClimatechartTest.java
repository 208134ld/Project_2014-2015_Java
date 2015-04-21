/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import domain.ClimateChart;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author bremme windows
 */
public class ClimatechartTest {
    private ClimateChart climateChart;
    
    public ClimatechartTest() {
    }
    
    @Before
    public void setUp() {
        double temp[] = {5,5,14,0,8,12,5.5,5,5,14,0,8};
        int sed[] = {5,5,14,0,8,12,55,5,5,14,0,8};
        climateChart = new ClimateChart(1,"Gent",1990,1995,temp,sed,3.456,3.5635,"","");
    }

@Test
public void giveChordsGivesTheCorrectCoordinates()
{
   String cord = climateChart.giveCords(23, 23, 50);
   Assert.assertEquals("23° 23' 50\"",cord);
}
@Test(expected=IllegalArgumentException.class)
public void giveChordsThrowsErrorWhenDegreeIsNegatif()
{
     String cord = climateChart.giveCords(-23, 23, 50);
}
@Test(expected=IllegalArgumentException.class)
public void giveChordsThrowsErrorWhenMinutesAreNegatif()
{
     String cord = climateChart.giveCords(23, -23, 50);
}
@Test(expected=IllegalArgumentException.class)
public void giveChordsThrowsErrorWhenSecondsAreNegatif()
{
     String cord = climateChart.giveCords(23, 23, -50);
}
@Test(expected=IllegalArgumentException.class)
public void giveChordsThrowsExceptionWhenMinutesAreGreaterThan60()
{
     String cord = climateChart.giveCords(23, 73, 50);
}
@Test(expected=IllegalArgumentException.class)
public void giveChordsThrowsExceptionWhenSecondsAreGreaterThan60()
{
     String cord = climateChart.giveCords(23, 53, 70);
}
@Test
public void calcDecimalsGivesCorrectDecimals(){
    double dec = climateChart.calcDecimals(15, 15, 15,"nb");
    Assert.assertEquals(15.254166666666666,dec,5);
}
@Test
public void calcDecimalsGivesCorrectDecimals2()
{
    double dec = climateChart.calcDecimals(15, 15, 15,"zb");
    Assert.assertEquals(-15.254166666666666,dec,5);
}
@Test
public void calcDecimalsGivesCorrectDecimals3()
{
    double dec = climateChart.calcDecimals(15, 15, 15,"Zb");
    Assert.assertEquals(-15.254166666666666,dec,5);
}
@Test
public void calcDecimalsGivesCorrectDecimals4()
{
    double dec = climateChart.calcDecimals(15, 15, 15,"WL");
    Assert.assertEquals(-15.254166666666666,dec,5);
}
@Test(expected=IllegalArgumentException.class)
public void constructorThrowsExceptionWhenMonthDataIsNETo12()
{
     double temp[] = {5,5,14,0,8,12,5.5,5,5,14,0,8,12};
        int sed[] = {5,5,14,0,8,12,55,5,5,14,0,8};
        climateChart = new ClimateChart(1,"Gent",1990,1995,temp,sed,3.456,3.5635,"","");
   
}
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
