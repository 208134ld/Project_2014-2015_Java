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
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import persistentie.ContinentRepository;

/**
 * FXML Controller class
 *
 * @author Stijn
 */
public class MainPanel extends GridPane {

    @FXML
    TreeView selectionTreeView;
    
    private ContinentRepository continentRepository;

    public MainPanel() throws SQLException{
        continentRepository = new ContinentRepository();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        TreeItem<String> root = new TreeItem<>("Root");
        
        for(Continent c : continentRepository.getAllContinents()){
            TreeItem<String> itemChild = new TreeItem<>(c.getName());

            for(Country co : continentRepository.getCountriesOfContinent(c.getId())){
                TreeItem<String> countryChild = new TreeItem<>(co.getName());
                //List<ClimateChart> list = continentRepository.getClimateChartsOfCountry(co.getId());
                //int i = 0;
                for(ClimateChart chart : continentRepository.getClimateChartsOfCountry(co.getId())){
                    TreeItem<String> climateChartChild = new TreeItem<>(chart.getLocation());
                    countryChild.getChildren().add(climateChartChild);
                }
                
                itemChild.getChildren().add(countryChild);
            }
            
            root.getChildren().add(itemChild);
        }
        
        root.setExpanded(true);
        
        //itemChild.setExpanded(false);
        
        
        selectionTreeView.setRoot(root);
        selectionTreeView.setShowRoot(false);
    }
}
