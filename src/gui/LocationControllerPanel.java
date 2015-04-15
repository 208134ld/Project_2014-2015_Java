/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Continent;
import domain.Country;
import domain.MonthOfTheYear;
import domain.Months;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Logan Dupont
 */
public class LocationControllerPanel extends Accordion{
    
    //Continent-Part
    @FXML
    private TextField txtContinentName;
    @FXML
    private Button btnAddContinent;
    @FXML
    private Button btnRemoveContinent;
    
    //Country-Part
    @FXML
    private ComboBox<Continent> cbContinentCountry;
    @FXML
    private TextField txtCountryName;
    @FXML
    private Button btnAddCountry;
    @FXML
    private Button btnRemoveCountry;
    
    //ClimateChart-Part
    @FXML
    private ComboBox<Continent> cbContinentClimateChart;
    @FXML
    private ComboBox<Country> cbCountryClimateChart;
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
    private TableColumn<Months, String> tempCol;
    @FXML
    private TableColumn<Months, String> sedCol;
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
    private ObservableList<Months> monthList;
    private int counter;
//    private int countryID; 
//    private DomeinController dc;
//    private RepositoryController rc;
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
           //MonthOfTheYear m = MonthOfTheYear.valueOf(maandText.getText());
           //monthList.add(new Months(n,temp,m));
       
         
          if(counter==12)
          {
              txtTemp.setDisable(true);
              txtSed.setDisable(true);
              btnAddRow.setDisable(true);
              errorBar.setText("*Pas individuele cellen aan door te dubbelklikken");
          }
//          else{
//          maandText.setText(MonthOfTheYear.values()[counter]+"");
//          }
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
        txtTemp.getText();
        txtSed.getText();
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
//        monthList = FXCollections.observableArrayList();
//        monthTable.setItems(monthList);
//        counter=1;
//        maandText.setDisable(true);
//        maandText.setText(MonthOfTheYear.Jan+"");
//        monthTable.setEditable(true);
//        maandcol.setCellValueFactory(new PropertyValueFactory("month"));
//        tempCol.setCellValueFactory(new PropertyValueFactory("temp"));
//        sedCol.setCellValueFactory(new PropertyValueFactory("sed"));
//        Callback<TableColumn<Months, String>, TableCell<Months, String>> cellFactory =
//                new Callback<TableColumn<Months,String>, TableCell<Months,String>>() {
//                     
//                    @Override
//                    public TableCell call(TableColumn p) {
//                        return new EditingCell();
//                    }
//                };
//        tempCol.setCellFactory(cellFactory);
//        sedCol.setCellFactory(cellFactory);
//    }
    public LocationControllerPanel(){ 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationControllerPanel_1.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } 
    }
    
}
