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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author bremme windows
 */
public class LocationWizardController extends GridPane {

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
