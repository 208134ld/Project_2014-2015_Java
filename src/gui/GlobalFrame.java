/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repository.RepositoryController;

/**
 * FXML Controller class
 *
 * @author Logan Dupont
 */
public class GlobalFrame extends VBox {

    @FXML
    private MenuBar mainMenu;
    
    @FXML
    private GridPane workPanel;
    
    private RepositoryController repositoryController;
    
    private final LocationControllerPanel locationControllerPanel;
    private final MainPanel mainPanel;
    
    public GlobalFrame(RepositoryController repositoryController){
        
        this.repositoryController = repositoryController;
        locationControllerPanel = new LocationControllerPanel(repositoryController);
        
        mainPanel = new MainPanel();
        //repositoryController.addObserver(mainPanel);
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GlobalFrame.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        
        workPanel.add(new LocationControllerPanel(repositoryController), 0, 0);
        
        
        
            
    }
    
//    @FXML
//    private void setClimateChartPannels(ActionEvent event){
//        try{
//            workPanel.add(new MainPanel(), 1, 0);
//        }
//        catch(SQLException ex){
//            ex.printStackTrace();
//        }
//    }
}
