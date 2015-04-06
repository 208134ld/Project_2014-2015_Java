/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import domain.DomeinController;
import domain.MonthOfTheYear;
import domain.Months;
import util.MyNode;
import util.TextFieldTreeCellImpl;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import repository.RepositoryController;

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
    private TableColumn<Months, MonthOfTheYear> maandcol;
    @FXML
    private TableColumn<Months, String> tempCol;
    @FXML
    private TableColumn<Months, String> sedCol;
    @FXML
    private TableView<Months> monthTable;
    @FXML
    private TextField beginPeriode;
    @FXML
    private TextField eindPeriode;
    @FXML
    private TextField BGraden;
    @FXML
    private TextField BMinuten;
    @FXML
    private TextField BSeconden;
    @FXML
    private TextField BSeconden1;
    @FXML
    private TextField BMinuten1,LengteParameter,BreedteParameter;
    @FXML
    private TextField BGraden1;
    @FXML
    private Label LatitudeLabel;
    @FXML
    private Label LongitudeLabel;
    //private ContinentRepository continentRepository;
    private ClimateChart selectedClimatechart;

    private ObservableList<TreeItem<MyNode>> obsTreeItems;
    private List<TreeItem<MyNode>> treeItems;

    private DomeinController dc;
    private RepositoryController rc;

    public MainPanel() throws SQLException {
        dc = new DomeinController();
        rc = new RepositoryController();

        //continentRepository = dc.getConRepo();
        treeItems = new ArrayList<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        final TreeItem<MyNode> root = new TreeItem<>(new MyNode("Root node", "Root", 1));

        for (Continent c : rc.getAllContinents()) {

            TreeItem<MyNode> itemChild = new TreeItem<>(new MyNode(c.toString(), "Continent", c.getId()));

            for (Country co : rc.getCountriesOfContinent(c.getId())) {
                TreeItem<MyNode> countryChild = new TreeItem<>(new MyNode(co.getName(), "Country", co.getId()));

                for(ClimateChart chart : rc.getClimateChartsOfCountry(co.getId())){
                    TreeItem<MyNode> climateChartChild = new TreeItem<>(new MyNode(chart.getLocation(), "ClimateChart", chart.getId()));
                    countryChild.getChildren().add(climateChartChild);
                }
                itemChild.getChildren().add(countryChild);
            }

            treeItems.add(itemChild);
            //root.getChildren().add(itemChild);
        }
        
        obsTreeItems = FXCollections.observableArrayList(treeItems);
        root.getChildren().addAll(obsTreeItems);

        root.setExpanded(true);
        initMonthTable();
        //Onderstaand gedeelte maakt het mogelijk om treeviewitems "on the spot" van naam te veranderen, dit werkt alleen met treeItem<String> dus moet nog aangepast worden
        selectionTreeView.setEditable(true);
        selectionTreeView.setCellFactory(new Callback<TreeView<MyNode>, TreeCell<MyNode>>() {
            @Override
            public TreeCell<MyNode> call(TreeView<MyNode> p) {
                try {
                    return new TextFieldTreeCellImpl(root, treeItems, rc);
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
                if (selectedItem.getValue().type.equalsIgnoreCase("ClimateChart")) {

//                    selectedClimatechart = rc.getClimateChartByClimateChartID(selectedItem.getValue().id);
                    selectedClimatechart = new ClimateChart(1,"Gent",1950,1970,true,23.34534,44.34523,"51° 3' 15 OL ","30° 45' 10 NB ",1);
                    updateLocationDetailPanel(selectedClimatechart);
                }

            }
        });

    }

    public void updateLocationDetailPanel(ClimateChart c) {

        
        beginPeriode.setText(c.getBeginperiod()+"");
        eindPeriode.setText(c.getEndperiod()+"");
        LatitudeLabel.setText(c.getLatitude()+"");
        LongitudeLabel.setText(c.getLongitude()+"");
        // GRADEN VAN LENGTE EN BREEDTE
        BGraden1.setText(c.getBCord().trim().split("°")[0]);
        BMinuten1.setText(c.getBCord().trim().split("°")[1].split("'")[0]);
        String waarde = c.getBCord().trim().split("°")[1].split("'")[1];
        BSeconden1.setText(waarde.substring(0,waarde.length()-2).trim());
        BreedteParameter.setText(waarde.substring(waarde.length()-2,waarde.length()));
        BGraden.setText(c.getLCord().trim().split("°")[0]);
        BMinuten.setText(c.getLCord().trim().split("°")[1].split("'")[0]);
        waarde = c.getLCord().trim().split("°")[1].split("'")[1];
        BSeconden.setText(waarde.substring(0,waarde.length()-2).trim());
        LengteParameter.setText(waarde.substring(waarde.length()-2,waarde.length()));
//        ObservableList<Months> m = FXCollections.observableArrayList(c.getMonths());
//        monthTable.setItems(FXCollections.observableArrayList(m));

    }

    public void initMonthTable() {
        maandcol.setCellValueFactory(new PropertyValueFactory("month"));
        tempCol.setCellValueFactory(new PropertyValueFactory("temp"));
        sedCol.setCellValueFactory(new PropertyValueFactory("sed"));
    }



}
