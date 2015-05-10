package repository;

import domain.ClassGroup;
import domain.ClauseComponent;
import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import domain.DeterminateTable;
import domain.Exercise;
import domain.Grade;
import domain.MonthOfTheYear;
import domain.Months;
import domain.Parameter;
import domain.SchoolYear;
import domain.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.persistence.TypedQuery;

public class RepositoryController extends Observable {

    private ContinentRepository continentRepo;
    private CountryRepository countryRepo;
    private ClimateChartRepository chartRepo;
    private MonthRepository monthRepo;
    private DeterminateTableRepository determinateRepo;
    private GradeRepository gradeRepo;
    private ClassGroupsRepository clm;
    private TestRepository testRepo;
    private ExerciseRepository exerciseRepo;

    public RepositoryController() {
        this.continentRepo = new ContinentRepository();
        this.countryRepo = new CountryRepository();
        this.chartRepo = new ClimateChartRepository();
        this.monthRepo = new MonthRepository();
        this.determinateRepo = new DeterminateTableRepository();
        this.gradeRepo = new GradeRepository();
        this.clm = new ClassGroupsRepository();
        this.testRepo = new TestRepository();
        this.exerciseRepo = new ExerciseRepository();
    }

    public List<Continent> getAllContinents() {
        return continentRepo.getAllContinents();
    }

    public void insertContinent(Continent c) {
        try {
            continentRepo.insertContinent(c);
            setChanged();
            notifyObservers();
        } catch (Exception e) {

        }
    }

    public List<ClimateChart> findAllClimateCharts() {
        return chartRepo.findAll();
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

    public void removeDeterminateTableById(int id) {
        determinateRepo.deleteDeterminateTableById(id);
    }

    public void createDeterminateTable(int gradeId, String name) {
        Grade g = findGradeById(gradeId);
        DeterminateTable d = determinateRepo.createDeterminateTable(name);
        g.setDeterminateTableId(d);
    }

    public List<DeterminateTable> getAllDeterminateTables() {
        return determinateRepo.getAllDeterminateTables();
    }

    public DeterminateTable findDeterminateTableById(int id) {
        return determinateRepo.findDeterminateTableById(id);
    }

    public void insertClause(ClauseComponent clause) {
        determinateRepo.insertClause(clause);
    }

    public void deleteCountry(int countryId) {
        countryRepo.deleteCountry(countryId);
    }

    public void deleteClimatechart(int climatechartId) {
        chartRepo.deleteClimatechart(climatechartId);
    }
    
    public ClimateChart findClimateChartByName(String name) {
        return chartRepo.findByName(name);
    }
    

    public void deleteContinent(int id) {
        continentRepo.deleteContinent(id);
    }

    public Continent findContinentByName(String name) {
        return continentRepo.findByName(name);
    }

    public ClassGroup findClassGroupById(int id) {
        return clm.findById(id);
    }

    public void deleteClassgroup(int id) {
        clm.removeClassGroup(clm.findById(id));
    }

    @Override
    public void addObserver(Observer observer) {
        super.addObserver(observer);
        observer.update(this, this);
    }

    public void removeClauseComponent(ClauseComponent clause) {
        determinateRepo.removeClauseComponent(clause);
    }

    public List<SchoolYear> getAllSchoolYears() {
        return clm.getAllSchoolYears();
    }

    public List<ClassGroup> getClassGroupsOfSchoolYear(SchoolYear sy) {
        return clm.getAllClassGroupsOfSchoolYear(sy);
    }
    
    public List<ClassGroup> getAllClassGroups(){
        return clm.getAllClassGroups();
    }
    
    public List<Test> getAllTests() {
        return testRepo.getAllTests();
    }

    public void insertTest(Test t) {
        testRepo.insertTest(t);
    }

    public Test findTestById(int id) {
        return testRepo.findTestById(id);
    }
    
    public List<Test> findTestsByClassGroup(ClassGroup classGroup){
        return testRepo.findTestsByClassGroup(classGroup);
    }
    
    public List<Exercise> getAllExercises() {
        return exerciseRepo.getAllExercises();
    }

    public void insertExercise(Exercise e) {
        exerciseRepo.insertExercise(e);
    }

    public Exercise findExerciseById(int id) {
        return exerciseRepo.findExerciseById(id);
    }
    
    public List<Exercise> findExercisesByTest(Test test){
        return exerciseRepo.findExercisesByTest(test);
    }
    
    public void removeExercise(Exercise e){
        exerciseRepo.removeExercise(e);
    }
    
    public void removeTest(Test t){
        testRepo.removeTest(t);
    }
    
    public Continent getEurope(){
        return continentRepo.getEurope();
    }

}
