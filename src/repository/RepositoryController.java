package repository;

import domain.Clause;
import domain.ClauseComponent;
import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import domain.DeterminateTable;
import domain.Grade;
import domain.MonthOfTheYear;
import domain.Months;
import domain.Parameter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.beans.InvalidationListener;

public class RepositoryController extends Observable{

    private ContinentRepository continentRepo;
    private CountryRepository countryRepo;
    private ClimateChartRepository chartRepo;
    private MonthRepository monthRepo;
    private DeterminateTableRepository determinateRepo;
    private GradeRepository gradeRepo;

    public RepositoryController() {
        this.continentRepo = new ContinentRepository();
        this.countryRepo = new CountryRepository();
        this.chartRepo = new ClimateChartRepository();
        this.monthRepo = new MonthRepository();
        this.determinateRepo = new DeterminateTableRepository();
        this.gradeRepo = new GradeRepository();
    }

    public List<Continent> getAllContinents() {
        return continentRepo.getAllContinents();
    }

    public void insertContinent(Continent c) {
        continentRepo.insertContinent(c);
        
        setChanged();
        notifyObservers();
    }

    public Continent findContinentById(int id) {
        return continentRepo.findContinentById(id);
    }
    
    public Country findCountryByName(String name) {
        return countryRepo.findCountryByName(name);
    }

    public List<Country> getAllCountries() {
        return countryRepo.getAllCountries();
    }

    public List<Country> getCountriesOfContinent(int continentId) {
        return countryRepo.getCountriesOfContinent(continentId);
    }

    public List<ClimateChart> getClimateChartsOfCountry(int countryId) {
        return chartRepo.getClimateChartsOfCountry(countryId);
    }

    public void updateClimateChart(int id, String LCord, String BCord, int bP, int eP, double longi, double lat) {
        chartRepo.updateClimateChart();
    }

    public ClimateChart getClimateChartByClimateChartID(int chartId) {
        return chartRepo.getClimateChartByClimateChartID(chartId);
    }

    public void insertCountry(Country c) {
        countryRepo.insertCountry(c);
        
        setChanged();
        notifyObservers();
    }

    public Country findCountryById(int id) {
        return countryRepo.findCountryById(id);
    }

    public void updateTemp(int id, double temp) {
        chartRepo.updateTemp(id, temp);
    }

    public void updateSed(int id, int sed) {
        chartRepo.updateSed(id, sed);
    }

    public void InsertClimatechart(ClimateChart c) {
        chartRepo.insertClimateChart(c);
        
        setChanged();
        notifyObservers();
    }

    public List<MonthOfTheYear> getMonthsOfTheYear() {
        return Arrays.asList(MonthOfTheYear.values());
    }

    public List<Months> getMonthsOfClimateChart(int climateChartId) {
        return monthRepo.getMonthsOfClimateChart(climateChartId);
    }

    public Grade findGradeById(int id) {
        return gradeRepo.findById(id);
    }

    public List<ClauseComponent> findClauseComponentsByDeterminateTableId(int id) {
        return determinateRepo.getAllClauseComponentsOfDeterminateTable(id);
    }

    public List<ClauseComponent> findClausesByDeterminateTableId(DeterminateTable id) {
        return determinateRepo.getAllClausesOfDeterminateTable(id);
    }

    public ClauseComponent findClauseById(int clauseId) {
        return determinateRepo.findClauseById(clauseId);
    }

    public Parameter findParameterById(int parameterId) {
        return determinateRepo.getParameterById(parameterId);
    }

    public void updateRepo() {
        determinateRepo.updateRepo();
    }

    public List<Parameter> findAllParamaters() {
        return determinateRepo.getAllParameters();
    }

    public Parameter findParameterByName(String name) {
        return determinateRepo.getParameterByName(name);
    }

    public List<Grade> getAllGrades() {
        return gradeRepo.getAllGrades();
    }
    
    public void removeDeterminateTableById(int id){
        determinateRepo.deleteDeterminateTableById(id);
    }
    
    public void createDeterminateTable(int gradeId, String name){
        Grade g = findGradeById(gradeId);
        DeterminateTable d = determinateRepo.createDeterminateTable(name);
        g.setDeterminateTableId(d);
    }
    
    public List<DeterminateTable> getAllDeterminateTables(){
        return determinateRepo.getAllDeterminateTables();
    }
    
    public DeterminateTable findDeterminateTableById(int id){
        return determinateRepo.findDeterminateTableById(id);
    }
    
    public void insertClause(ClauseComponent clause){
        determinateRepo.insertClause(clause);
    }
    
    @Override
    public void addObserver(Observer observer)
    {
        super.addObserver(observer);
        observer.update(this, null);
    }
    
}