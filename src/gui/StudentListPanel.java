/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.ClassListController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author SAMUEL
 */
public class StudentListPanel extends BorderPane {

    @FXML
    private Button btnAddStudent, btnRemoveStudent;

    @FXML
    private ListView<String> listStudents;

    private ClassListController controller;

    public StudentListPanel() {
        controller = new ClassListController(); //Moeten we dezelfde controller gebruiken als in de ClassListPanel???
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentListPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        /*listStudents.setItems(controller.giveStudentsOfClassGroup(null)); //zorgt ervoor dat er data komt in onze frame

        controller.giveStudentsOfClassGroup(null).addListener(
                (ListChangeListener<String>) e
                -> btnRemoveFilosoof.setDisable(controller.noFilosofen())); //disabled de buttun remove als er geen filosofen meer zijn
        
        listStudents.getSelectionModel().selectedItemProperty(). //geeft de filosofen die we hebben geselecteerd terug
             addListener((observableValue, oldValue, newValue) -> 
        {
            if (newValue != null) {
                  int index = listStudents.getSelectionModel().getSelectedIndex();
                System.out.printf("%d %s\n", index, newValue);
            }
        });*/
        
    }
    
    
    
}
