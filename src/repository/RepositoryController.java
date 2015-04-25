
package repository;

import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import domain.MonthOfTheYear;
import domain.Months;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class RepositoryController {
    
    private ContinentRepository continentRepo;
    private CountryRepository countryRepo;
    private ClimateChartRepository chartRepo;
    private MonthRepository monthRepo;
    
    public RepositoryController(){
        this.continentRepo = new ContinentRepository();
        this.countryRepo = new CountryRepository();
        this.chartRepo = new ClimateChartRepository();
        this.monthRepo = new MonthRepository();
    }
    
    public List<Continent> getAllContinents(){
        return continentRepo.getAllContinents();
    }
    
    public void insertContinent(Continent c){
        continentRepo.insertContinent(c);
    }
    
    public Continent findContinentById(int id){
        return continentRepo.findContinentById(id);
    }
    
    public List<Country> getAllCountries(){
        return countryRepo.getAllCountries();
    }
    
    public List<Country> getCountriesOfContinent(int continentId){
        return countryRepo.getCountriesOfContinent(continentId);
    }
    
    public List<ClimateChart> getClimateChartsOfCountry(int countryId){
        return chartRepo.getClimateChartsOfCountry(countryId);
    }
    public void updateClimateChart(int id,String LCord,String BCord,int bP,int eP,double longi,double lat)
    {
        chartRepo.updateClimateChart();
    }
    public ClimateChart getClimateChartByClimateChartID(int chartId){
        return chartRepo.getClimateChartByClimateChartID(chartId);
    }
    
    public void insertCountry(Country c){
        countryRepo.insertCountry(c);
    }
    
    public Country findCountryById(int id){
        return countryRepo.findCountryById(id);
    }
    public void updateTemp(int id,double temp){
        chartRepo.updateTemp(id, temp);
    }
    public void updateSed(int id,int sed)
    {
        chartRepo.updateSed(id,sed);
    }
    public void InsertClimatechart(ClimateChart c)
    {
        chartRepo.insertClimateChart(c);
    }
    
    public List<MonthOfTheYear> getMonthsOfTheYear(){
        return Arrays.asList(MonthOfTheYear.values());
    }
    
    public List<Months> getMonthsOfClimateChart(int climateChartId){
        return monthRepo.getMonthsOfClimateChart(climateChartId);
    }
}
