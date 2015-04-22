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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import repository.RepositoryController;
import util.MyNode;

/**
 * FXML Controller class
 *
 * @author Logan Dupont
 */
public class LocationViewPanel extends GridPane{

    @FXML
    private TreeView selectionTreeView;
    
    @FXML
    private TextField txtLocation;
    @FXML
    private TextField txtBCoord;
    @FXML
    private TextField txtLCoord;
    @FXML
    private TextField txtPeriod;
    @FXML
    private TextField txtTempYear;
    @FXML
    private TextField txtSedYear;
    
    @FXML
    private TableView<Months> monthTable;
    @FXML
    private TableColumn<Months, MonthOfTheYear> maandcol;
    @FXML
    private TableColumn<Months, String> tempCol;
    @FXML
    private TableColumn<Months, String> sedCol;
    
    private RepositoryController rc;
    private ClimateChart selectedClimatechart;
    private ObservableList<TreeItem<MyNode>> obsTreeItems;
    private List<TreeItem<MyNode>> treeItems;
    private List<TreeItem<MyNode>> continentItems;
    private List<TreeItem<MyNode>> countryItems;
    private ObservableList<Months> monthsList;
    
    public LocationViewPanel(RepositoryController repositoryController){
        rc = repositoryController;
        
        treeItems = new ArrayList<>();
        continentItems = new ArrayList<>();
        countryItems = new ArrayList<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationViewPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        final TreeItem<MyNode> root = new TreeItem<>(new MyNode("Aardrijkskunde database", "Root", 1));

        for (Continent c : rc.getAllContinents()) {

            TreeItem<MyNode> itemChild = new TreeItem<>(new MyNode(c.toString(), "Continent", c.getId()));

            for (Country co : rc.getCountriesOfContinent(c.getId())) {
                TreeItem<MyNode> countryChild = new TreeItem<>(new MyNode(co.getName(), "Country", co.getId()));

                for(ClimateChart chart : rc.getClimateChartsOfCountry(co.getId())){
                    TreeItem<MyNode> climateChartChild = new TreeItem<>(new MyNode(chart.getLocation(), "ClimateChart", chart.getId()));
                    countryChild.getChildren().add(climateChartChild);
                }
                itemChild.getChildren().add(countryChild);
                treeItems.add(countryChild);
                countryItems.add(countryChild);
            }
            continentItems.add(itemChild);
            treeItems.add(itemChild);
           
        }
        
        obsTreeItems = FXCollections.observableArrayList(continentItems);
        root.getChildren().addAll(obsTreeItems);

        root.setExpanded(true);
        //initMonthTable();
        
        selectionTreeView.setRoot(root);
        //selectionTreeView.setShowRoot(false);

        selectionTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<MyNode>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<MyNode>> observable, TreeItem<MyNode> oldValue, TreeItem<MyNode> newValue) {
                TreeItem<MyNode> selectedItem = newValue;
                if (selectedItem.getValue().type.equalsIgnoreCase("ClimateChart")) {
                    
                    selectedClimatechart = rc.getClimateChartByClimateChartID(selectedItem.getValue().id);
                    //System.out.println(selectedClimatechart.getMonths().get(0).getSed());
                    //updateLocationDetailPanel(selectedClimatechart);
                }

            }
        });
        
    }
    
    public void updateLocationDetailPanel(ClimateChart c) {

        
        txtLocation.setText(c.getLocation());
//        txtBCoord.setText(c.getBCord());
//        txtLCoord.setText(c.getLCord());
        
//        beginPeriode.setText(c.getBeginperiod()+"");
//        eindPeriode.setText(c.getEndperiod()+"");
//        LatitudeLabel.setText(c.getLatitude()+"");
//        LongitudeLabel.setText(c.getLongitude()+"");
        
        // GRADEN VAN LENGTE EN BREEDTE
        
//        BGraden.setText(c.getBCord().split("°")[0].trim());
//        BMinuten.setText(c.getBCord().split("°")[1].split("'")[0].trim());
//        String waarde = c.getBCord().split("°")[1].split("'")[1].trim();
//        BSeconden.setText(waarde.substring(0,waarde.length()-4).trim());
//        BreedteParameter.setText(waarde.substring(waarde.length()-2,waarde.length()).trim());
//        BGraden1.setText(c.getLCord().split("°")[0].trim());
//        BMinuten1.setText(c.getLCord().split("°")[1].split("'")[0].trim());
//         waarde = c.getLCord().split("°")[1].split("'")[1].trim();
//        BSeconden1.setText(waarde.substring(0,waarde.length()-4).trim());
//        LengteParameter.setText(waarde.substring(waarde.length()-2,waarde.length()));
//        monthsList  = FXCollections.observableArrayList(c.getMonths());
//        monthTable.setItems(monthsList);
    }

    public void initMonthTable() {
         
//        monthTable.setEditable(true);
//        maandcol.setCellValueFactory(new PropertyValueFactory("month"));
//        tempCol.setCellValueFactory(new PropertyValueFactory("temp"));
//        
//        sedCol.setCellValueFactory(new PropertyValueFactory("sed"));
//        Callback<TableColumn<Months, String>, TableCell<Months, String>> cellFactory =
//                new Callback<TableColumn<Months,String>, TableCell<Months,String>>() {
//                     
//                    @Override
//                    public TableCell call(TableColumn p) {
//                        return new EditingCell();
//                    }
//                };
//        tempCol.setCellFactory(cellFactory);
//        sedCol.setCellFactory(cellFactory);
    }
    
}
