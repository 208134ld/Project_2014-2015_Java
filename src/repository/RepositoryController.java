
package repository;

import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import java.sql.SQLException;
import java.util.List;

public class RepositoryController {
    
    private ContinentRepository continentRepo;
    private CountryRepository countryRepo;
    private ClimateChartRepository chartRepo;
    
    public RepositoryController() throws SQLException{
        this.continentRepo = new ContinentRepository();
        this.countryRepo = new CountryRepository();
        this.chartRepo = new ClimateChartRepository();
    }
    
    public List<Continent> getAllContinents() throws SQLException{
        return continentRepo.getAllContinents();
    }
    
    public void insertContinent(Continent c){
        continentRepo.insertContinent(c);
    }
    
    public Continent findContinentById(int id){
        return continentRepo.findContinentById(id);
    }
    
    public List<Country> getAllCountries() throws SQLException{
        return countryRepo.getAllCountries();
    }
    
    public List<Country> getCountriesOfContinent(int continentId) throws SQLException{
        return countryRepo.getCountriesOfContinent(continentId);
    }
    
    public List<ClimateChart> getClimateChartsOfCountry(int countryId) throws SQLException{
        return chartRepo.getClimateChartsOfCountry(countryId);
    }
    public void updateClimateChart(int id,String LCord,String BCord,int bP,int eP,double longi,double lat)
    {
        chartRepo.updateClimateChart(id, LCord, BCord, bP, eP, longi, lat);
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
}
