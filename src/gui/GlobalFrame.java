/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

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
    
    /*@FXML
    private Menu mManage;
    
    @FXML
    private Menu mOverview;
    
    @FXML
    private MenuItem miDeterminateTabel;
    
    @FXML
    private MenuItem miClassList;
    
    @FXML
    private MenuItem miClimateChart;
    
    @FXML
    private MenuItem miStatistics;
    
    @FXML
    private MenuItem miWeb;*/
    
    public GlobalFrame(){
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GlobalFrame.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
//        try{
//            workPanel.add(new MainPanel(), 1, 0);
//        }
//        catch(SQLException ex){
//            ex.printStackTrace();
//        }
        
            
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
