/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import domain.ClimateChart;
import domain.DomeinController;
import domain.MonthOfTheYear;
import domain.Months;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import repository.RepositoryController;

/**
 * FXML Controller class
 *
 * @author bremme windows
 */
public class LocationWizardController extends AnchorPane {
    @FXML
    private TextField LengteParameter;
    @FXML
    private TextField BreedteParameter;
    @FXML
    private TextField BGraden1;
    @FXML
    private TextField BMinuten1;
    @FXML
    private TextField BSeconden1;
    @FXML
    private TextField BSeconden;
    @FXML
    private TextField BMinuten;
    @FXML
    private TextField BGraden;
    @FXML
    private TextField eindPeriode;
     @FXML
    private TableColumn<Months, MonthOfTheYear> maandcol;
    @FXML
    private TextField beginPeriode;
    @FXML
    private TableView<Months> monthTable;
    @FXML
    private TableColumn<Months, String> tempCol;
    @FXML
    private TableColumn<Months, String> sedCol;
    @FXML
    private Label locatieLable;
    @FXML
    private TextField locatieNaam;
    @FXML
    private TextField maandText;
    @FXML
    private TextField temperatuurText;
    @FXML
    private TextField neerslagText;
    @FXML
    private Button addRowButton;
    @FXML
    private Button addLocationButton;
    @FXML
    private Label errorBar;
    private ObservableList<Months> monthList;
    private int counter;
    private int countryID; 
    private DomeinController dc;
    private RepositoryController rc;
    /**
     * Initializes the controller class.
     */
public LocationWizardController(int countryId){
    dc = new DomeinController();
    rc = new RepositoryController();
    this.countryID = countryId;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationWizard.fxml"));
    loader.setRoot(this);
    loader.setController(this);
        try {
            loader.load();
//            initMonthTable();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }

        initMonthTable();
}  

    @FXML
    private void updateCol(TableColumn.CellEditEvent<Months,Double> event) {
    }

    @FXML
    private void addRow(MouseEvent event) {
        try{
            
           double temp= Double.parseDouble(temperatuurText.getText());
           int n=Integer.parseInt(neerslagText.getText());
           MonthOfTheYear m = MonthOfTheYear.valueOf(maandText.getText());
           monthList.add(new Months(n,temp,m));
       
         
          if(counter==12)
          {
              temperatuurText.setDisable(true);
              neerslagText.setDisable(true);
              addRowButton.setDisable(true);
              errorBar.setText("*Pas individuele cellen aan door te dubbelklikken");
          }
          else{
          maandText.setText(MonthOfTheYear.values()[counter]+"");
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
        temperatuurText.getText();
        neerslagText.getText();
    }

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
//           rc.InsertClimatechart(c);
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

     public void initMonthTable() {
        monthList = FXCollections.observableArrayList();
        monthTable.setItems(monthList);
        counter=1;
        maandText.setDisable(true);
        maandText.setText(MonthOfTheYear.Jan+"");
        monthTable.setEditable(true);
        maandcol.setCellValueFactory(new PropertyValueFactory("month"));
        tempCol.setCellValueFactory(new PropertyValueFactory("temp"));
        sedCol.setCellValueFactory(new PropertyValueFactory("sed"));
        Callback<TableColumn<Months, String>, TableCell<Months, String>> cellFactory =
                new Callback<TableColumn<Months,String>, TableCell<Months,String>>() {
                     
                    @Override
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        tempCol.setCellFactory(cellFactory);
        sedCol.setCellFactory(cellFactory);
    }
}
