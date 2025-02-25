package controllers;

import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import domain.Months;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import repository.RepositoryController;
import util.EditingCell;
import util.MyNode;
import util.TextFieldTreeCellImpl;

public class LocationViewPanel extends GridPane implements Observer {

    @FXML
    private TreeView selectionTreeView;
    @FXML
    private Label locationLable;
//    @FXML
//    private Label errorBar;
    @FXML
    private TextField txtBGrades;
    @FXML
    private TextField txtBMinutes;
    @FXML
    private TextField txtBSeconds;
    @FXML
    private TextField txtBreedteParameter;
    @FXML
    private TextField txtLGrades;
    @FXML
    private TextField txtLMinutes;
    @FXML
    private TextField txtLSeconds;
    @FXML
    private TextField txtLengteParameter;
    @FXML
    private TextField txtBeginPeriod;
    @FXML
    private TextField txtEndPeriod;
    @FXML
    private Label lbTemperatureYear;
    @FXML
    private Label lbSedimentYear;
    @FXML
    private TableView<Months> monthTable;
    @FXML
    private TableColumn<Months, String> monthcol;
    @FXML
    private TableColumn<Months, Number> tempCol;
    @FXML
    private TableColumn<Months, Number> sedCol;
    @FXML
    private Text errorText;
    @FXML
    private Button saveBut;
    private RepositoryController rc;
    public static ClimateChart selectedClimatechart;
    private ObservableList<TreeItem<MyNode>> obsTreeItems;
    private List<TreeItem<MyNode>> treeItems;
    private List<TreeItem<MyNode>> continentItems;
    private List<TreeItem<MyNode>> countryItems;
    private ObservableList<Months> monthsList;
    private boolean firstTime = true;
    int i = 0;

    public LocationViewPanel(RepositoryController repositoryController) {
        rc = repositoryController;
        treeItems = new ArrayList<>();
        continentItems = new ArrayList<>();
        countryItems = new ArrayList<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LocationViewPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        disableEverything(true);
        initMonthTable();
    }

    private void disableEverything(boolean disable) {
        txtBGrades.setDisable(disable);
        txtBMinutes.setDisable(disable);
        txtBSeconds.setDisable(disable);
        txtLGrades.setDisable(disable);
        txtLMinutes.setDisable(disable);
        txtLSeconds.setDisable(disable);
        txtBeginPeriod.setDisable(disable);
        txtEndPeriod.setDisable(disable);
        txtBreedteParameter.setDisable(disable);
        txtLengteParameter.setDisable(disable);
        saveBut.setDisable(disable);
    }

    public void updateLocationDetailPanel(ClimateChart c) {
        if (txtBGrades.disableProperty().getValue() == true) {
            disableEverything(false);
        }
        locationLable.setText(c.getLocation());
        txtBGrades.setText(c.getGrade(true));
        txtBMinutes.setText(c.getMinutes(true));
        txtBSeconds.setText(c.getSeconds(true));
        txtLGrades.setText(c.getGrade(false));
        txtLMinutes.setText(c.getMinutes(false));
        txtLSeconds.setText(c.getSeconds(false));
//        txtBGrades.setText(c.getBCord().split("°")[0].trim());
//        txtBMinutes.setText(c.getBCord().split("°")[1].split("'")[0].trim());
        String waarde = c.getBCord().split("°")[1].split("'")[1].trim();
        txtBreedteParameter.setText(waarde.substring(waarde.length() - 2, waarde.length()).trim());
        waarde = c.getLCord().split("°")[1].split("'")[1].trim();
        txtLengteParameter.setText(waarde.substring(waarde.length() - 2, waarde.length()));
        txtBeginPeriod.setText(c.getBeginperiod() + "");
        txtEndPeriod.setText(c.getEndperiod() + "");
        lbTemperatureYear.setText(Double.toString(Math.round(c.calcAverageYearTemp().getAsDouble() * 10.0) / 10.0) + " °C");
        lbSedimentYear.setText(Integer.toString(c.calcSedimentYear()) + " mm");
        List<Months> maanden = new ArrayList<>();
        c.getMonths().stream().sorted((e1, e2) -> e1.getMonth().compareTo(e2.getMonth())).forEach(m -> maanden.add(m));
        c.setMonths(maanden);
        // TABLE
        monthsList = FXCollections.observableArrayList(maanden);
        monthTable.setItems(monthsList);
    }

    public void updateSelectionTreeViewPanel() {
        final TreeItem<MyNode> root = new TreeItem<>(new MyNode("Aardrijkskunde database", "Root", 1));
        treeItems.clear();
        countryItems.clear();
        continentItems.clear();

        rc.getAllContinents().stream().map((c) -> {
            TreeItem<MyNode> itemChild = new TreeItem<>(new MyNode(c.toString(), "Continent", c.getId()));
            rc.getCountriesOfContinent(c.getId()).stream().map((co) -> {
                TreeItem<MyNode> countryChild = new TreeItem<>(new MyNode(co.getName(), "Country", co.getId()));
                rc.getClimateChartsOfCountry(co.getId()).stream().map((chart) -> new TreeItem<>(new MyNode(chart.getLocation(), "ClimateChart", chart.getId()))).forEach((climateChartChild) -> {
                    countryChild.getChildren().add(climateChartChild);
                });
                return countryChild;
            }).map((countryChild) -> {
                itemChild.getChildren().add(countryChild);
                return countryChild;
            }).map((countryChild) -> {
                treeItems.add(countryChild);
                return countryChild;
            }).forEach((countryChild) -> {
                countryItems.add(countryChild);
            });
            return itemChild;
        }).map((itemChild) -> {
            continentItems.add(itemChild);
            return itemChild;
        }).forEach((itemChild) -> {
            treeItems.add(itemChild);
        });
        selectionTreeView.setCellFactory(new Callback<TreeView<MyNode>, TreeCell<MyNode>>() {
            @Override
            public TreeCell<MyNode> call(TreeView<MyNode> p) {
                try {
                    return new TextFieldTreeCellImpl(root, treeItems, rc);
                } catch (SQLException ex) {
                    errorText.setText("cell in de boom veranderen is mislukt");
                }
                return null;
            }
        });
        
        obsTreeItems = FXCollections.observableArrayList(continentItems);
        root.getChildren().addAll(obsTreeItems);

        root.setExpanded(true);
        selectionTreeView.setRoot(root);

        selectionTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<MyNode>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<MyNode>> observable, TreeItem<MyNode> oldValue, TreeItem<MyNode> newValue) {
                TreeItem<MyNode> selectedItem = newValue;
                
                    try {if (selectedItem.getValue().getType().equalsIgnoreCase("ClimateChart")) {
                        if (!(newValue.getValue().getId() == i)) {
                            if (isClimateChartNotUpToDate()) {
                                Alert alert = new Alert(AlertType.CONFIRMATION);
                                alert.setTitle("gewijzigde data opslaan");
                                alert.setHeaderText("De wijzigingen zijn nog niet opgeslaan ");
                                alert.setContentText("Wilt u ze opslaan?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    saveDetaillWindow();
                                } else {
    // ... user chose CANCEL or closed the dialog
                                }}
                            }
                            selectedClimatechart = rc.getClimateChartByClimateChartID(selectedItem.getValue().getId());
                            selectedClimatechart.setMonths(rc.getMonthsOfClimateChart(selectedItem.getValue().getId()));
                            updateLocationDetailPanel(selectedClimatechart);
                            i = newValue.getValue().getId();
                        }

                    } catch (Exception e) {
                        errorText.setText("Kon de gewenste klimatogram niet vinden in de databank");
                    }
                
            }
        });
    }

    private boolean isClimateChartNotUpToDate() {
        boolean isChecked = false;

        if (!firstTime) {
            if (selectedClimatechart.getBeginperiod() != Integer.parseInt(txtBeginPeriod.getText())) {
                isChecked = true;
            }
            if (selectedClimatechart.getEndperiod() != Integer.parseInt(txtEndPeriod.getText())) {
                isChecked = true;
            }
            if (!(selectedClimatechart.getGrade(true).equals(txtBGrades.getText()) && selectedClimatechart.getMinutes(true).equals(txtBMinutes.getText()) && selectedClimatechart.getSeconds(true).equals(txtBSeconds.getText()))) {
                isChecked = true;
            }
            if (!(selectedClimatechart.getGrade(false).equals(txtLGrades.getText()) && selectedClimatechart.getMinutes(false).equals(txtLMinutes.getText()) && selectedClimatechart.getSeconds(false).equals(txtLSeconds.getText()))) {
                isChecked = true;
            }
        } else {
            firstTime = false;
        }
        return isChecked;
    }

    public void initMonthTable() {
        monthcol.setCellValueFactory(cellData -> cellData.getValue().monthProperty());
        sedCol.setCellValueFactory(cellData -> cellData.getValue().sedimentProperty());
        tempCol.setCellValueFactory(cellData -> cellData.getValue().temperatureProperty());
        Callback<TableColumn<Months, Number>, TableCell<Months, Number>> cellFactory
                = new Callback<TableColumn<Months, Number>, TableCell<Months, Number>>() {

                    @Override
                    public TableCell call(TableColumn p) {
                        return new EditingCell(false);
                    }
                };
        Callback<TableColumn<Months, Number>, TableCell<Months, Number>> cellFactory2
                = new Callback<TableColumn<Months, Number>, TableCell<Months, Number>>() {
                    @Override
                    public TableCell call(TableColumn p) {
                        return new EditingCell(true);
                    }
                };
        tempCol.setCellFactory(cellFactory);
        sedCol.setCellFactory(cellFactory2);
    }

    @FXML
    private void saveDetaillWindow() {
        try {
            int g1 = Integer.parseInt(txtBGrades.getText().trim());
            int g2 = Integer.parseInt(txtLGrades.getText().trim());
            int m1 = Integer.parseInt(txtBMinutes.getText().trim());
            int m2 = Integer.parseInt(txtLMinutes.getText().trim());
            int s1 = Integer.parseInt(txtBSeconds.getText().trim());
            int s2 = Integer.parseInt(txtLSeconds.getText().trim());
            int begin = Integer.parseInt(txtBeginPeriod.getText().trim());
            int end = Integer.parseInt(txtEndPeriod.getText().trim());
            if (!(txtLengteParameter.getText().trim().equalsIgnoreCase("ol") || txtLengteParameter.getText().trim().equalsIgnoreCase("wl"))) {
                throw new IllegalArgumentException("Lengteparameter kan alleen OL of WL zijn");
            }
            if (!(txtBreedteParameter.getText().equalsIgnoreCase("nb") || txtBreedteParameter.getText().equalsIgnoreCase("zb"))) {
                throw new IllegalArgumentException("Breedteparameter kan alleen NB of ZB zijn");
            }
            String longi = selectedClimatechart.giveCords(g1, m1, s1) + txtBreedteParameter.getText().toUpperCase().trim();
            String lat = selectedClimatechart.giveCords(g2, m2, s2) + txtLengteParameter.getText().toUpperCase().trim();
            selectedClimatechart.setBCord(longi);
            selectedClimatechart.setLCord(lat);
            selectedClimatechart.setBeginperiod(begin);
            selectedClimatechart.setEndperiod(end);
            selectedClimatechart.setLatitude(selectedClimatechart.calcDecimals(g1, m1, s1, txtBreedteParameter.getText().trim()));
            selectedClimatechart.setLongitude(selectedClimatechart.calcDecimals(g2, m2, s2, txtLengteParameter.getText().trim()));

            rc.updateClimateChart(selectedClimatechart.getId(), selectedClimatechart.getLCord(), selectedClimatechart.getBCord(), selectedClimatechart.getBeginperiod(), selectedClimatechart.getEndperiod(), selectedClimatechart.getLongitude(), selectedClimatechart.getLatitude());
            updateLocationDetailPanel(selectedClimatechart);
            errorText.setText("");

        } catch (IllegalArgumentException e) {
            errorText.setText(e.getMessage());
        } catch (NullPointerException e) {
            errorText.setText("Alle velden moeten ingevuld zijn");
        } catch (Exception e) {
            errorText.setText("Er is een onbekende fout gebeurt");
        }

    }

    @FXML
    private void updateCol(TableColumn.CellEditEvent<Months, Double> event) {

        int id = monthTable.getSelectionModel().getSelectedCells().get(0).getRow();
        if (monthTable.getSelectionModel().getSelectedCells().get(0).getColumn() == 1) {

            selectedClimatechart.getMonths().get(id).setAverTemp(event.getNewValue());
            rc.updateTemp(selectedClimatechart.getMonths().get(id).getMonthId(), event.getNewValue());
        }
        if (monthTable.getSelectionModel().getSelectedCells().get(0).getColumn() == 2) {

            double d = event.getNewValue();
            int d2 = (int) d;
            selectedClimatechart.getMonths().get(id).setSediment(d2);
            rc.updateSed(selectedClimatechart.getMonths().get(id).getMonthId(), d2);
        }

        updateLocationDetailPanel(selectedClimatechart);
    }

    @Override
    public void update(Observable o, Object arg) {
        updateSelectionTreeViewPanel();
    }

}
