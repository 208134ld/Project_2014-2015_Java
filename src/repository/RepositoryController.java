/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Stijn
 */
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
    
    public List<Country> getAllCountries() throws SQLException{
        return countryRepo.getAllCountries();
    }
    
    public List<Country> getCountriesOfContinent(int continentId) throws SQLException{
        return countryRepo.getCountriesOfContinent(continentId);
    }
    
    public List<ClimateChart> getClimateChartsOfCountry(int countryId) throws SQLException{
        return chartRepo.getClimateChartsOfCountry(countryId);
    }
    
    public ClimateChart getClimateChartByClimateChartID(int chartId){
        return chartRepo.getClimateChartByClimateChartID(chartId);
    }

}
