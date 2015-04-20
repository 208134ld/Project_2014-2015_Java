/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.ClassListController;
import domain.Grade;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author SAMUEL
 */
public class DeterminateTableViewPanel extends GridPane {

    @FXML
    private ListView<String> gradeListView;
    
    @FXML
    private Label lblDeterminateTable;
    
    @FXML
    private TreeView determinateTable;
    
    private Grade selectedGrade;
    
    private ObservableList<Grade> gradeListObservable;
    
    private ClassListController controller;

    public DeterminateTableViewPanel() {
        
        controller = new ClassListController();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DeterminateTableViewPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        /*gradeListObservable = FXCollections.observableArrayList(controller.giveAllGrades());
        
        gradeListView.setItems(gradeListObservable);

        gradeListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
        {
            if (newValue != null) {
                  int index = gradeListView.getSelectionModel().getSelectedIndex();
                System.out.printf("%d %s\n", index, newValue);
            }
        });*/

    }

}
