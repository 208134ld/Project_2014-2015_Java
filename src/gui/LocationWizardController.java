/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author bremme windows
 */
public class LocationWizardController extends GridPane {
    @FXML
    private TextField beginPeriode1;
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
    private TextField beginPeriode;
    @FXML
    private Pane pane;
    @FXML
    private FlowPane flowpPane;


    /**
     * Initializes the controller class.
     */
public LocationWizardController(){
    FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationWizard.fxml"));
    
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

}  

}
