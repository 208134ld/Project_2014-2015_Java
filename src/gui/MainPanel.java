/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import domain.MonthOfTheYear;
import domain.Months;
import domain.MyNode;
import domain.TextFieldTreeCellImpl;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Month;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import persistentie.ContinentRepository;

/**
 * FXML Controller class
 *
 * @author Stijn
 */
public class MainPanel extends GridPane {

    //test
    @FXML
    TreeView selectionTreeView;
    @FXML
    private TableColumn<Months,MonthOfTheYear> maandcol;
    @FXML
    private TableColumn<Months,String> tempCol;
    @FXML
    private TableColumn<Months,String> sedCol;
    @FXML
    private TableView<Months> monthTable;
    @FXML
    private Label longitudeLabel;
    @FXML
    private Label latitudelabel;
    @FXML
    private Label landId,locatieLable;
    @FXML
    private Label beginPeriode,eindPeriode;
    private ContinentRepository continentRepository;

    public MainPanel() throws SQLException {
        continentRepository = new ContinentRepository();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        TreeItem<MyNode> root = new TreeItem<>(new MyNode("Root node", "Root", 1));

        for(Continent c : continentRepository.getAllContinents()){
            TreeItem<MyNode> itemChild = new TreeItem<>(new MyNode(c.getName(), "Continent", c.getId()));
            
            for(Country co : continentRepository.getCountriesOfContinent(c.getId())){
                TreeItem<MyNode> countryChild = new TreeItem<>(new MyNode(co.getName(), "Country", co.getId()));

                for(ClimateChart chart : continentRepository.getClimateChartsOfCountry(co.getId())){
                    TreeItem<MyNode> climateChartChild = new TreeItem<>(new MyNode(chart.getLocation(), "ClimateChart", chart.getId()));
                    countryChild.getChildren().add(climateChartChild);
                }
                
                itemChild.getChildren().add(countryChild);
            }
            
            root.getChildren().add(itemChild);
        }
        
        
        
        root.setExpanded(true);
        initMonthTable();
        //Onderstaand gedeelte maakt het mogelijk om treeviewitems "on the spot" van naam te veranderen, dit werkt alleen met treeItem<String> dus moet nog aangepast worden
        selectionTreeView.setEditable(true);
        selectionTreeView.setCellFactory(new Callback<TreeView<MyNode>,TreeCell<MyNode>>(){
            @Override
            public TreeCell<MyNode> call(TreeView<MyNode> p) {
                try {
                    return new TextFieldTreeCellImpl();
                } catch (SQLException ex) {
                    Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
 
            
        });
         
        //itemChild.setExpanded(false);
        selectionTreeView.setRoot(root);
        //selectionTreeView.setShowRoot(false);
        
        selectionTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<MyNode>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<MyNode>> observable, TreeItem<MyNode> oldValue, TreeItem<MyNode> newValue) {
                TreeItem<MyNode> selectedItem = newValue;
                if(selectedItem.getValue().type.equalsIgnoreCase("ClimateChart")){
                    
                    ClimateChart c  = continentRepository.getClimateChartByClimateChartID(selectedItem.getValue().id);
                    updateLocationDetailPanel(c);
                }
                
            }
        });
    }
    public void updateLocationDetailPanel(ClimateChart c)
    {
       
        longitudeLabel.setText(c.getLongitude()+"");
        latitudelabel.setText(c.getLatidude()+"");
        locatieLable.setText(c.getLocation());
        landId.setText(c.getCountry().getName());
        beginPeriode.setText(c.getBeginperiod()+"");
        eindPeriode.setText(c.getEndperiod()+"");
        ObservableList<Months> m = FXCollections.observableArrayList(c.getMonths());
        
        monthTable.setItems(FXCollections.observableArrayList(m));
        
    }
    public void initMonthTable()
    {
        maandcol.setCellValueFactory(new PropertyValueFactory("month"));
        tempCol.setCellValueFactory(new PropertyValueFactory("sed"));
        sedCol.setCellValueFactory(new PropertyValueFactory("temp"));
    }

}
