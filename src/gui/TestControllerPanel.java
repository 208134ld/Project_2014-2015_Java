/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static com.sun.deploy.security.ruleset.DeploymentRuleSet.initialize;
import domain.ClimateChart;
import domain.DeterminateTable;
import domain.Exercise;
import domain.Grade;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import repository.RepositoryController;

/**
 * FXML Controller class
 *
 * @author Logan Dupont
 */
public class TestControllerPanel extends TitledPane{

   @FXML
    private TextField Titel;
    @FXML
    private TextArea Beschrijving;
    @FXML
    private DatePicker begindatum;
    @FXML
    private DatePicker einddatum;
    @FXML
    private ComboBox<Grade> graad;
    @FXML
    private ComboBox<Exercise> oefening;
    @FXML
    private TextField vraag;
    @FXML
    private ComboBox<String> alleVragen;
    @FXML
    private Button voegVraagToe;
    @FXML
    private Button verwijderVraag;
    @FXML
    private ComboBox<ClimateChart> klimatogram;
    @FXML
    private ComboBox<DeterminateTable> determinatieTabel;
    @FXML
    private Button newOef;
    @FXML
    private Button voegTestToe;
    private RepositoryController rc;
    private FXMLLoader loader;
    private ObservableList<ClimateChart> climateCharts;
    private ObservableList<DeterminateTable> detTable;
    private ObservableList<Grade> grades;
    private ObservableList<Exercise> exercises;
    private ObservableList<String> vragen;
    private int oefeningenCounter = 1;
    /**
     * Initializes the controller class.
     */
   public TestControllerPanel() throws IOException 
   {
        rc = new RepositoryController();
        loader = new FXMLLoader(getClass().getResource("TestControllerPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
           try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } 
        init();
        
   }
   private void init()
   {
       try{
       climateCharts = FXCollections.observableArrayList(rc.findAllClimateCharts());
       klimatogram.setItems(climateCharts);
       klimatogram.setValue(climateCharts.get(0));
       detTable = FXCollections.observableArrayList(rc.getAllDeterminateTables());
       determinatieTabel.setItems(detTable);
       determinatieTabel.setValue(detTable.get(0));
       grades = FXCollections.observableArrayList(rc.getAllGrades());
       graad.setItems(grades);
       graad.setValue(grades.get(0));
       List<Exercise> lijst = new ArrayList<>();
       lijst.add(new Exercise("oefening "+oefeningenCounter));
       exercises =FXCollections.observableArrayList(lijst);
       oefening.setItems(exercises);
       oefening.setValue(exercises.get(0));
       oefeningenCounter ++;
       }catch(Exception e)
       {
            //todo foutmelding
       }
      graad.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
              if(graad.getSelectionModel().selectedItemProperty().get().getGrade()==2)
              {
                 
                  setEditableGraad2(true);
              }
              else{
                  setEditableGraad2(false);
              }
  
            }
        });
   
   }
   
   private void setEditableGraad2(boolean waarde)
   {
       vraag.setDisable(waarde);
       alleVragen.setDisable(waarde);
       voegVraagToe.setDisable(waarde);
       verwijderVraag.setDisable(waarde);
   }
 
       
     @FXML
    private void saveOefening(MouseEvent event) {
    }
    @FXML
    private void voegVraagToe(MouseEvent event)
    {
        try{
            String vraagbox = vraag.getText();
            if(vragen==null)
            {
                System.out.println("isnull");
                List<String> eenVraag = new ArrayList<String>();
                eenVraag.add(vraagbox);
                vragen = FXCollections.observableArrayList(eenVraag);
                alleVragen.setItems(vragen);
                alleVragen.setValue(vragen.get(0));
            }else
            {
                vragen.add(vraagbox);
            }
            
        }catch(NullPointerException nulEx)
        {
            //todo leeg textfield error
        }catch(Exception e)
        {
            // todo global error
        }
    }
    @FXML
    private void verwijderVraag(MouseEvent event)
    {
        try{
            vragen.remove(alleVragen.getSelectionModel().getSelectedItem());
        }catch(NullPointerException nulexc)
        {
            
        }catch(Exception e)
        {
            
        }
    }
    @FXML
    private void nieuweoefening(MouseEvent event)
    {
        
    }
}
