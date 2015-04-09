/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import domain.MonthOfTheYear;
import domain.Months;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author bremme windows
 */
public class LocationWizardController extends GridPane {
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

    /**
     * Initializes the controller class.
     */
public LocationWizardController(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationWizard.fxml"));
    loader.setController(this);
        try {
            loader.load();
//            initMonthTable();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }

}  

    @FXML
    private void updateCol(TableColumn.CellEditEvent<Months,Double> event) {
    }

    @FXML
    private void addRow(MouseEvent event) {
          
        temperatuurText.getText();
        neerslagText.getText();
    }

    @FXML
    private void addLocation(MouseEvent event) {
        try{
               int g1 = Integer.parseInt(BGraden.getText().trim());
            int g2 = Integer.parseInt(BGraden1.getText().trim());
            int m1 = Integer.parseInt(BMinuten.getText().trim());
            int m2 = Integer.parseInt(BMinuten1.getText().trim());
            int s1 = Integer.parseInt(BSeconden.getText().trim());
            int s2 = Integer.parseInt(BSeconden1.getText().trim());
            int begin = Integer.parseInt(beginPeriode.getText().trim());
            int einde = Integer.parseInt(eindPeriode.getText().trim());
            String lNaam = locatieNaam.getText().trim();
             
           if(!(LengteParameter.getText().trim().equalsIgnoreCase("ol")||LengteParameter.getText().trim().equalsIgnoreCase("wl")))
               throw new IllegalArgumentException("Lengteparameter kan alleen OL of WL zijn");
           if(!(BreedteParameter.getText().equalsIgnoreCase("nb")||BreedteParameter.getText().equalsIgnoreCase("zb")))
               throw new IllegalArgumentException("Breedteparameter kan alleen NB of ZB zijn");
           
        }catch(Exception e)
        {
            
        }
         
           
    }

     public void initMonthTable() {
        monthList = FXCollections.observableArrayList();
        System.out.println(MonthOfTheYear.Jan);
//        maandText.setText(MonthOfTheYear.Jan+"");
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
