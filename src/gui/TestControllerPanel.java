/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static com.sun.deploy.security.ruleset.DeploymentRuleSet.initialize;
import domain.ClimateChart;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
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
    private ComboBox<?> graad;
    @FXML
    private ComboBox<?> oefening;
    @FXML
    private TextField vraag;
    @FXML
    private ComboBox<?> alleVragen;
    @FXML
    private Button voegVraagToe;
    @FXML
    private Button verwijderVraag;
    @FXML
    private ComboBox<ClimateChart> klimatogram;
    @FXML
    private ComboBox<?> determinatieTabel;
    @FXML
    private Button newOef;
    @FXML
    private Button voegTestToe;
    private RepositoryController rc;
    private FXMLLoader loader;
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
        
        
   }
}
