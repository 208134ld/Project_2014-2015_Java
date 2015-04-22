/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.MonthOfTheYear;
import domain.Months;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
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
    private TableColumn<Months, MonthOfTheYear> monthcol;
    @FXML
    private TableColumn<Months, Double> tempCol;
    @FXML
    private TableColumn<Months, Integer> sedCol;
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
    
    private RepositoryController repositoryController;
    private ObservableList<MonthOfTheYear> monthOfTheYearList;
    private ObservableList<String> continentList;
    private ObservableList<String> countryList;
    private List<Months> monthList;
    private ObservableList<Months> tableMonthList;
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
        try{
            double temp= Double.parseDouble(txtTemp.getText());
            int n=Integer.parseInt(txtSed.getText());
            MonthOfTheYear m = cbMonth.getValue();
            monthList.add(new Months(n, temp, m));

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
            else{
             if(monthList.stream().map(mo -> mo.getMonth()).collect(Collectors.toList())
                     .contains(cbMonth.getValue())){
                 errorBar.setText("Deze maand is al reeds in gebruik");
             }
 
            }
          counter++;
        }
        catch(NumberFormatException numb){
            errorBar.setText("Pars error. hebt u tekst in de tekstbox staan?");
        }
        catch(NullPointerException ex)
        {
            errorBar.setText("Elk tekstvakje moet ingevuld worden.");
        }
        catch(Exception e)
        {
            errorBar.setText("Er is een fout opgetreden. probeer het opnieuw.");
        }
           
        
    }
//
//    @FXML
//    private void addLocation(MouseEvent event) {
//        try{
//            
//            int g1 = Integer.parseInt(BGraden.getText().trim());
//            int g2 = Integer.parseInt(BGraden1.getText().trim());
//            int m1 = Integer.parseInt(BMinuten.getText().trim());
//            int m2 = Integer.parseInt(BMinuten1.getText().trim());
//            int s1 = Integer.parseInt(BSeconden.getText().trim());
//            int s2 = Integer.parseInt(BSeconden1.getText().trim());
//            int begin = Integer.parseInt(beginPeriode.getText().trim());
//            int einde = Integer.parseInt(eindPeriode.getText().trim());
//            String lNaam = locatieNaam.getText().trim();
//             
//           if(!(LengteParameter.getText().trim().equalsIgnoreCase("ol")||LengteParameter.getText().trim().equalsIgnoreCase("wl")))
//               throw new IllegalArgumentException("Lengteparameter kan alleen OL of WL zijn");
//           if(!(BreedteParameter.getText().equalsIgnoreCase("nb")||BreedteParameter.getText().equalsIgnoreCase("zb")))
//               throw new IllegalArgumentException("Breedteparameter kan alleen NB of ZB zijn");
//           if(monthList.size()!=12)
//               throw new IllegalArgumentException("Er moeten 12 maanden zijn.");
//           
//           ClimateChart c = new ClimateChart(lNaam,begin,einde);
//           String Bcord = c.giveCords(g1, m1, s1);
//           String Lcord = c.giveCords(g2, m2, s2);
//           double lat = c.calcDecimals(g1, m1, s1, BreedteParameter.getText().trim());
//           double longi = c.calcDecimals(g2, m2, s2, LengteParameter.getText().trim());
//           c.setBCord(Bcord);
//           c.setLCord(Lcord);
//           c.setLatitude(lat);
//           c.setLongitude(longi);
//           c.setCountry(rc.findCountryById(countryID));
//           List<Months> maanden = new ArrayList<>();
//           monthList.stream().forEach(p->maanden.add(p));
//           c.setMonths(maanden);
//           System.out.println(c.getLocation()+"   "+c.getLCord());
//           c.getMonths().stream().forEach(m->System.out.println(m.getSed()));
//           
//           
//        } catch(NumberFormatException numb){
//            errorBar.setText("Pars error. hebt u tekst in de tekstbox staan?");
//        }
//        catch(NullPointerException ex)
//        {
//            errorBar.setText("Elk tekstvakje moet ingevuld worden.");
//        }
//        catch(Exception e)
//        {
//            errorBar.setText(e.getMessage());
//        }
//         
//           
//    }
//
//     public void initMonthTable() {
//        tableMonthList = FXCollections.observableList(monthList);
//        monthTable.setItems(tableMonthList);
//        monthTable.setEditable(true);
//        monthcol.setCellValueFactory(cellData -> cellData.getValue().monthProperty());
//        tempCol.setCellValueFactory(cellData -> cellData.getValue().tempProperty());
//        sedCol.setCellValueFactory(cellData -> cellData.getValue().sedProperty());
//        
//        /*Callback<TableColumn<Months, String>, TableCell<Months, String>> cellFactory =
//                new Callback<TableColumn<Months,String>, TableCell<Months,String>>() {
//                     
//                    @Override
//                    public TableCell call(TableColumn p) {
//                        return new EditingCell();
//                    }
//                };*/
//        tempCol.setCellFactory(cellFactory);
//        sedCol.setCellFactory(cellFactory);
//    }
     
    public LocationControllerPanel(RepositoryController repositoryController){
        this.repositoryController = repositoryController;
        cbContinentCountry = new ComboBox<>();
        cbContinentClimateChart = new ComboBox<>();
        cbCountryClimateChart = new ComboBox<>();
        cbMonth = new ComboBox<>();
        

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationControllerPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } 
        
        setExpandedPane(tpContinent);
        
        continentList = FXCollections.observableList(repositoryController.getAllContinents()
                .stream().map(c -> c.getName()).sorted().collect(Collectors.toList()));
        cbContinentCountry.setItems(continentList);
        cbContinentClimateChart.setItems(continentList);
        
        countryList = FXCollections.observableList(repositoryController.getAllCountries()
                .stream().map(c -> c.getName()).sorted().collect(Collectors.toList()));
        cbCountryClimateChart.setItems(countryList);
        
        monthOfTheYearList = FXCollections.observableList(repositoryController.getMonthsOfTheYear());
        cbMonth.setItems(monthOfTheYearList);
        cbMonth.setVisibleRowCount(3);
        
    }
    
    @FXML
    private void addContinent(ActionEvent event) {
        //repositoryController.insertContinent(new Continent(txtContinentName.getText()));
        txtContinentName.clear();
    }
    
    @FXML
    private void removeContinent(ActionEvent event) {
        txtContinentName.clear();
    }
    
    @FXML
    private void addCountry(ActionEvent event) {
        //repositoryController.insertCo(new Continent(txtContinentName.getText()));
        txtCountryName.clear();
    }
    
    @FXML
    private void removeCountry(ActionEvent event) {
        txtCountryName.clear();
    }
    
    @FXML
    private void addClimateChart(ActionEvent event) {
        txtLocation.clear();
    }
    
    @FXML
    private void removeClimateChart(ActionEvent event) {
        txtLocation.clear();
    }
}
