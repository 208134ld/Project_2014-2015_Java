/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Continent;
import domain.ContinentenBeheer;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import persistentie.ContinentRepository;

/**
 * FXML Controller class
 *
 * @author Stijn
 */
public class ContinentControllerPanel extends BorderPane {

    @FXML
    private Button btnAddContinent;

    @FXML
    private Button btnRemoveContinent;

    @FXML
    private ListView<Continent> listContinenten;

    @FXML
    private ContinentenBeheer domeinController;

    @FXML
    private ContextMenu continentMenu;
    @FXML
    private MenuItem VeranderNaam;

   
    public ContinentControllerPanel(ContinentenBeheer domeinController) {
        this.domeinController = domeinController; 
       
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ContinentControllerPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        listContinenten.setItems(domeinController.getContinenten());

        if (domeinController.noContinenten()) {
            btnRemoveContinent.setDisable(true);
        }
        
        listContinenten.getSelectionModel().selectedItemProperty().
                addListener((observableValue, oldValue, newValue)->{
                    if(newValue != null){
                        int index = listContinenten.getSelectionModel().getSelectedIndex();
                        
                        System.out.printf("%d %s\n", index, newValue);
                    }
                });
        
        
    }

    @FXML
    private void addContinent(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Voeg continent toe");
        dialog.setContentText("Vul naam in:");

        dialog.showAndWait().ifPresent(response -> {
            if (!response.isEmpty()) {
                domeinController.addContinent(response);
                listContinenten.getSelectionModel().selectLast();
                btnRemoveContinent.setDisable(false);
            }
        });
    }

    @FXML
    private void removeContinent(ActionEvent event) {
        // de geselecteerde filosoof opvragen
        Continent geselecteerdeContinent = listContinenten.getSelectionModel().
                getSelectedItem();
        if (geselecteerdeContinent != null) {
        // zie volgende slide
            listContinenten.getSelectionModel().clearSelection();
            // verwijder de geselecteerde filosoof in model
            domeinController.removeContinent(
                    geselecteerdeContinent);
            if (domeinController.noContinenten()) {
                btnRemoveContinent.setDisable(true);
            }
        }
    }
     @FXML
    private void veranderNaam(ActionEvent event) {
         TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Verander de naam van "+listContinenten.getSelectionModel().getSelectedItem().getName());
        dialog.setHeaderText("Verander de naam van "+listContinenten.getSelectionModel().getSelectedItem().getName());
        dialog.setContentText("Vul naam in:");
        dialog.showAndWait().ifPresent(response -> {
            if (!response.isEmpty()) {
          
                domeinController.changeName(listContinenten.getSelectionModel().getSelectedItem().getId(), response);
                listContinenten.getSelectionModel().selectLast();
                
            }
        });
        
    }
    

}
