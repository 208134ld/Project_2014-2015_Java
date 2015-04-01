/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author bremme windows
 */
public class MainPanelController implements Initializable {
    @FXML
    private TreeView<?> selectionTreeView;
    @FXML
    private TableView<?> monthTable;
    @FXML
    private TableColumn<?, ?> maandcol;
    @FXML
    private TableColumn<?, ?> tempCol;
    @FXML
    private TableColumn<?, ?> sedCol;
    @FXML
    private Label locatieLable;
    @FXML
    private Label longitudeLabel;
    @FXML
    private Label latitudelabel;
    @FXML
    private Label landId;
    @FXML
    private Label beginPeriode;
    @FXML
    private Label eindPeriode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void changeLongitude(MouseEvent event) {
    }

    @FXML
    private void changeLatitude(KeyEvent event) {
    }

    @FXML
    private void changeBPeriod(KeyEvent event) {
    }

    @FXML
    private void changeEPeriod(KeyEvent event) {
    }
    
}
