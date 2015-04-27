package gui;

import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import domain.MonthOfTheYear;
import domain.Months;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
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
public class LocationViewPanel extends GridPane {

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
    private TableColumn<Months, MonthOfTheYear> monthCol;
    @FXML
    private TableColumn<Months, Number> tempCol;
    @FXML
    private TableColumn<Months, Number> sedCol;

    private RepositoryController rc;
    private ClimateChart selectedClimatechart;
    private ObservableList<TreeItem<MyNode>> obsTreeItems;
    private List<TreeItem<MyNode>> treeItems;
    private List<TreeItem<MyNode>> continentItems;
    private List<TreeItem<MyNode>> countryItems;
    private ObservableList<Months> monthsList;

    public LocationViewPanel(RepositoryController repositoryController) {
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

                for (ClimateChart chart : rc.getClimateChartsOfCountry(co.getId())) {
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
        initMonthTable();

        selectionTreeView.setRoot(root);
        //selectionTreeView.setShowRoot(false);

        selectionTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<MyNode>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<MyNode>> observable, TreeItem<MyNode> oldValue, TreeItem<MyNode> newValue) {
                TreeItem<MyNode> selectedItem = newValue;
                if (selectedItem.getValue().getType().equalsIgnoreCase("ClimateChart")) {

                    selectedClimatechart = rc.getClimateChartByClimateChartID(selectedItem.getValue().getId());
                    selectedClimatechart.setMonths(rc.getMonthsOfClimateChart(selectedItem.getValue().getId()));
                    updateLocationDetailPanel(selectedClimatechart);
                }

            }
        });

    }

    public void updateLocationDetailPanel(ClimateChart c) {

        txtLocation.setText(c.getLocation());
        txtBCoord.setText(c.getBCord());
        txtLCoord.setText(c.getLCord());
        txtPeriod.setText(c.getBeginperiod() + " - " + c.getEndperiod());
        monthsList = FXCollections.observableArrayList(c.getMonths());
        c.getMonths().stream().map(m->m.getMonthProp()).collect(Collectors.toList()).forEach(System.out::println);
        monthCol.setCellValueFactory(cellData -> cellData.getValue().monthProperty());
        sedCol.setCellValueFactory(cellData -> cellData.getValue().sedProperty());
        tempCol.setCellValueFactory(cellData -> cellData.getValue().tempProperty());
    
        monthTable.setItems(monthsList);
    }

    public void initMonthTable() {
//        tempCol = new TableColumn<Months,Number>("Temperatuur (C°)");
//        sedCol = new TableColumn<Months,Number>("Neerslag (mmN)");
//        tempCol.setCellFactory(new PropertyValueFactory("temp"));
//        sedCol.setCellFactory(new PropertyValueFactory("sed"));
//        monthCol = new TableColumn<Months,MonthOfTheYear>("Maand");
//        monthCol.setCellValueFactory(new Callback<CellDataFeatures<Months,MonthOfTheYear>, ObservableValue<MonthOfTheYear>>() {
//            public ObservableValue<MonthOfTheYear> call(CellDataFeatures<Months, MonthOfTheYear> p) {
//                return p.getValue().monthProperty();
//            }
//         });
//        
//        tempCol = new TableColumn<Months,Number>("Temperatuur (C°)");
//        tempCol.setCellValueFactory(new Callback<CellDataFeatures<Months,Number>, ObservableValue<Number>>() {
//            public ObservableValue<Number> call(CellDataFeatures<Months, Number> p) {
//                // p.getValue() returns the Person instance for a particular TableView row
//                return p.getValue().tempProperty();
//            }
//         });
//        
//        sedCol = new TableColumn<Months,Number>("Neerslag (mmN)");
//        sedCol.setCellValueFactory(new Callback<CellDataFeatures<Months,Number>, ObservableValue<Number>>() {
//            public ObservableValue<Number> call(CellDataFeatures<Months, Number> p) {
//                // p.getValue() returns the Person instance for a particular TableView row
//                return p.getValue().sedProperty();
//            }
//         });
    }

}
