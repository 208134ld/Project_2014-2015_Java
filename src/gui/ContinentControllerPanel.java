/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.ContinentenBeheer;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;

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
    private ListView<String> listContinenten;

    private ContinentenBeheer domeinController;

    public ContinentControllerPanel(ContinentenBeheer domeinController) {
        this.domeinController = domeinController;

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "ContinentControllerPanel.fxml"));
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
        String geselecteerdeContinent = listContinenten.getSelectionModel().
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

}
