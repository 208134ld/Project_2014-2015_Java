/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import domain.MonthOfTheYear;
import domain.Months;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import repository.RepositoryController;

/**
 * FXML Controller class
 *
 * @author Logan Dupont
 */
public class LocationControllerPanel extends Accordion{
    
    @FXML
    private TitledPane tpContinent;
    @FXML
    private TitledPane tpCountry;
    @FXML
    private TitledPane tpClimateChart;
    
    //Continent-Part
    @FXML
    private TextField txtContinentName;
    @FXML
    private Button btnAddContinent;
    @FXML
    private Button btnRemoveContinent;
    
    //Country-Part
    @FXML
    private ComboBox<String> cbContinentCountry;
    @FXML
    private TextField txtCountryName;
    @FXML
    private Button btnAddCountry;
    @FXML
    private Button btnRemoveCountry;
    
    //ClimateChart-Part
    @FXML
    private ComboBox<String> cbContinentClimateChart;
    @FXML
    private ComboBox<String> cbCountryClimateChart;
    @FXML
    private TextField txtLocation;
    @FXML
    private TextField BGrades;
    @FXML
    private TextField BMinutes;
    @FXML
    private TextField BSeconds;
    @FXML
    private TextField BreedteParameter;
    @FXML
    private TextField LGrades;
    @FXML
    private TextField LMinutes;
    @FXML
    private TextField LSeconds;
    @FXML
    private TextField LengteParameter;
    @FXML
    private TextField startPeriod;
    @FXML
    private TextField endPeriod;
    @FXML
    private TableView<Months> monthTable;
    @FXML
    private TableColumn<Months, String> maandcol;
    @FXML
    private TableColumn<Months, Number> tempCol;
    @FXML
    private TableColumn<Months, Number> sedCol;
    @FXML
    private ComboBox<MonthOfTheYear> cbMonth;
    @FXML
    private TextField txtTemp;
    @FXML
    private TextField txtSed;
    @FXML
    private Button btnAddRow;
    @FXML
    private Label errorBar;
    @FXML
    private Button btnAddClimateChart;
    @FXML
    private Button btnRemoveClimateChart;
      @FXML
    private WebView siteView;
    @FXML
    private ProgressBar webProgress;
    private final String WEBSITE="http://climatechart.azurewebsites.net/";
    private RepositoryController repositoryController;
    private ObservableList<MonthOfTheYear> monthOfTheYearList;
    private ObservableList<String> continentList;
    private ObservableList<String> countryList;
    private List<Months> monthList;
    private ObservableList<Months> tableMonthList;
    private MonthOfTheYear[] months;
    private int counter = 0;
//    private int countryID; 
//    private DomeinController dc;

    /**
     * Initializes the controller class.
     */
//public LocationWizardController(int countryId){
//    dc = new DomeinController();
//    rc = new RepositoryController();
//    this.countryID = countryId;
//    FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationWizard.fxml"));
//    loader.setRoot(this);
//    loader.setController(this);
//        try {
//            loader.load();
////            initMonthTable();
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//            throw new RuntimeException(ex);
//        }
//
//        initMonthTable();
//}  
//
//    @FXML
//    private void updateCol(TableColumn.CellEditEvent<Months,Double> event) {
//    }
//
    @FXML
    private void addRow(MouseEvent event) {
//        try{
            if(monthList !=null){
                if(monthList.stream().map(mo -> mo.getMonth()).collect(Collectors.toList())
                     .contains(cbMonth.getSelectionModel().getSelectedItem())){
                 errorBar.setText("Deze maand is al reeds in gebruik");
             }
            else{                
                double temp= Double.parseDouble(txtTemp.getText());
                int n=Integer.parseInt(txtSed.getText());
                MonthOfTheYear m = cbMonth.getSelectionModel().getSelectedItem();
                System.out.println(m);
                tableMonthList.add(new Months(n, temp, m));
               
                for(int i = 0; i<MonthOfTheYear.values().length;i++)
                {
                    if(months[i] == m && i!=11)
                    {
                        cbMonth.setValue(months[i+1]);
                    }
                }
                txtTemp.clear();
                txtSed.clear();
                
               
                if(counter==12)
                {
                    cbMonth.setDisable(true);
                    txtTemp.setDisable(true);
                    txtSed.setDisable(true);
                    btnAddRow.setDisable(true);
                    errorBar.setText("*Pas individuele cellen aan door te dubbelklikken");
                }
            }
            }
            
          counter++;
//        }
//        catch(NumberFormatException numb){
//            errorBar.setText("Gelieve getallen in te voeren in de tekstvakken");
//        }
//        catch(NullPointerException ex)
//        {
//            errorBar.setText("Elk tekstvakje moet ingevuld worden.");
//        }
//        catch(Exception e)
//        {
//            errorBar.setText("Er is een fout opgetreden. probeer het opnieuw.");
//        }
           
        
    }

     
    public LocationControllerPanel(RepositoryController repositoryController){
        this.repositoryController = repositoryController;
        cbContinentCountry = new ComboBox<>();
        cbContinentClimateChart = new ComboBox<>();
        cbCountryClimateChart = new ComboBox<>();
        cbMonth = new ComboBox<>();
         System.out.println("HIERZOOOO");
       
        

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationControllerPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } 
        
        setExpandedPane(tpContinent);
        
        updateComboBoxes();  
        monthOfTheYearList = FXCollections.observableList(repositoryController.getMonthsOfTheYear());
        cbMonth.setItems(monthOfTheYearList);
        cbMonth.setVisibleRowCount(3);
        initMonthTable();
        monthList = new ArrayList<>();
        tableMonthList = FXCollections.observableList(monthList);
        monthTable.setItems(tableMonthList);
        monthTable.setEditable(true);
        months = MonthOfTheYear.values();
    }
    
    public void updateComboBoxes(){
        continentList = FXCollections.observableList(repositoryController.getAllContinents()
                .stream().map(c -> c.getName()).collect(Collectors.toList()));
        cbContinentCountry.setItems(continentList);
        cbContinentClimateChart.setItems(continentList);
        cbContinentClimateChart.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                countryList = FXCollections.observableList(repositoryController.getCountriesOfContinent(
                cbContinentClimateChart.getSelectionModel().getSelectedIndex()+1)
                .stream().map(c -> c.getName()).collect(Collectors.toList()));
                cbCountryClimateChart.setItems(countryList);
            }
        });
    }
    
    @FXML
    private void addContinent(MouseEvent event) {
        repositoryController.insertContinent(new Continent(txtContinentName.getText().trim()));
        txtContinentName.clear();
        updateComboBoxes();
    }
    
    @FXML
    private void addCountry(MouseEvent event) {
        Continent continent = repositoryController.findContinentById(
            cbContinentCountry.getSelectionModel().getSelectedIndex() + 1);
        repositoryController.insertCountry(new Country(txtCountryName.getText().trim(), continent));
        txtCountryName.clear();
        updateComboBoxes();
    }
    
    @FXML
    private void addClimateChart(MouseEvent event) { 
       
       
        try{
            String loc = txtLocation.getText().trim();
            int begin = Integer.parseInt(startPeriod.getText().trim());
            int end = Integer.parseInt(endPeriod.getText().trim());
            int g1 = Integer.parseInt(BGrades.getText().trim());
            int g2 = Integer.parseInt(LGrades.getText().trim());
            int m1 = Integer.parseInt(BMinutes.getText().trim());
            int m2 = Integer.parseInt(LMinutes.getText().trim());
            int s1 = Integer.parseInt(BSeconds.getText().trim());
            int s2 = Integer.parseInt(LSeconds.getText().trim());
             
            Country country = repositoryController.findCountryByName(
               cbCountryClimateChart.getSelectionModel().getSelectedItem());
            
           if(!(LengteParameter.getText().trim().equalsIgnoreCase("ol")||LengteParameter.getText().trim().equalsIgnoreCase("wl")))
               throw new IllegalArgumentException("Lengteparameter kan alleen OL of WL zijn");
           if(!(BreedteParameter.getText().equalsIgnoreCase("nb")||BreedteParameter.getText().equalsIgnoreCase("zb")))
               throw new IllegalArgumentException("Breedteparameter kan alleen NB of ZB zijn");
           if(monthList.size()!=12)
               throw new IllegalArgumentException("Er moeten 12 maanden zijn.");
           
           ClimateChart c = new ClimateChart();
           String Bcord = c.giveCords(g1, m1, s1);
           String Lcord = c.giveCords(g2, m2, s2);
           double lat = c.calcDecimals(g1, m1, s1, BreedteParameter.getText().trim());
           double longi = c.calcDecimals(g2, m2, s2, LengteParameter.getText().trim());
           c.setLocation(loc);
           c.setBeginperiod(begin);
           c.setEndperiod(end);
           c.setBCord(Bcord);
           c.setLCord(Lcord);
           c.setLatitude(lat);
           c.setLongitude(longi);
           c.setCountry(country);
           List<Months> maanden = new ArrayList<>();
           monthList.stream().forEach(p->maanden.add(p));
           c.setMonths(maanden);
           System.out.println(c.getLocation()+"   "+c.getLCord());
           c.getMonths().stream().forEach(m->System.out.println(m.getSediment()));
           repositoryController.InsertClimatechart(c);
           txtLocation.clear();
           startPeriod.clear();
           endPeriod.clear();
           BGrades.clear();
           BMinutes.clear();
           BSeconds.clear();
           BreedteParameter.clear();
           LGrades.clear();
           LMinutes.clear();
           LSeconds.clear();
           LengteParameter.clear();
           errorBar.setText("");
           
        } catch(NumberFormatException numb){
            errorBar.setText("Gelieve het juiste type gegevens in te voeren in de tekstvakken");
        }
        catch(NullPointerException ex)
        {
            errorBar.setText("Elk tekstvakje moet ingevuld worden.");
        }
        catch(Exception e)
        {
            errorBar.setText(e.getMessage());
        }
    }

     public void initMonthTable() {
        counter=1;
        maandcol.setCellValueFactory(cellData -> cellData.getValue().monthProperty());
        tempCol.setCellValueFactory(cellData -> cellData.getValue().temperatureProperty());
        sedCol.setCellValueFactory(cellData -> cellData.getValue().sedimentProperty());
    }
      @FXML
    private void refreshSite(MouseEvent event) {
      try{
            ClimateChart selectedClimatechart = LocationViewPanel.selectedClimatechart;
        if(selectedClimatechart==null)
            throw new NullPointerException();
        
        WebEngine eng = siteView.getEngine();
        //nog het continent getten
            eng.load(WEBSITE+"ClimateChart/ShowExercises?selectedYear=3&continentId="+1+"&countryId="+"1"+"&climateId="+selectedClimatechart.getId());
         webProgress.progressProperty().bind(eng.getLoadWorker().progressProperty());

    eng.getLoadWorker().stateProperty().addListener(
            new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                    if (newState == Worker.State.SUCCEEDED) {
                         // hide progress bar then page is ready
                         webProgress.setVisible(false);
                    }
                }
            });
        }catch(Exception e)
        {
            
        }
    }
    
}
