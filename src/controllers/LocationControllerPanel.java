package controllers;

import util.EditingCell;
import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import domain.MonthOfTheYear;
import domain.Months;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import repository.RepositoryController;

public class LocationControllerPanel extends Accordion {

    @FXML
    private TitledPane tpContinent;
    @FXML
    private TitledPane tpCountry;
    @FXML
    private TitledPane tpClimateChart;

    //Continent-Part
    @FXML
    private TextField txtContinentName;
    @FXML
    private Button btnAddContinent;
    @FXML
    private Button btnRemoveContinent;

    //Country-Part
    @FXML
    private ComboBox<String> cbContinentCountry;
    @FXML
    private TextField txtCountryName;
    @FXML
    private Button btnAddCountry;
    @FXML
    private Button btnRemoveCountry;

    //ClimateChart-Part
    @FXML
    private ComboBox<String> lengteChoice;
    @FXML
    private TextField txtLocation;
    @FXML
    private ComboBox<String> breedteChoice;
    @FXML
    private ComboBox<String> cbContinentClimateChart;
    @FXML
    private ComboBox<String> cbCountryClimateChart;
    @FXML
    private TextField BGrades;
    @FXML
    private TextField BMinutes;
    @FXML
    private TextField BSeconds;
    @FXML
    private TextField BreedteParameter;
    @FXML
    private TextField LGrades;
    @FXML
    private TextField LMinutes;
    @FXML
    private TextField LSeconds;
    @FXML
    private TextField LengteParameter;
    @FXML
    private TextField startPeriod;
    @FXML
    private TextField endPeriod;
    @FXML
    private TableView<Months> monthTable;
    @FXML
    private TableColumn<Months, String> maandcol;
    @FXML
    private TableColumn<Months, Number> tempCol;
    @FXML
    private TableColumn<Months, Number> sedCol;
    @FXML
    private ComboBox<MonthOfTheYear> cbMonth;
    @FXML
    private TextField txtTemp;
    @FXML
    private TextField txtSed;
    @FXML
    private Button btnAddRow;
    @FXML
    private Label errorBar;
    @FXML
    private Button btnAddClimateChart;
    @FXML
    private Button btnRemoveClimateChart;
    @FXML
    private WebView siteView;
    @FXML
    private ProgressBar webProgress;
    private final String WEBSITE = "http://climatechart.azurewebsites.net/";
    private RepositoryController repositoryController;
    private ObservableList<MonthOfTheYear> monthOfTheYearList;
    private ObservableList<String> continentList;
    private ObservableList<String> countryList;
    private List<Months> monthList;
    private ObservableList<Months> tableMonthList;
    private MonthOfTheYear[] months;
    private int counter = 0;

    public LocationControllerPanel(RepositoryController repositoryController) {
        this.repositoryController = repositoryController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LocationControllerPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        setExpandedPane(tpContinent);

        updateComboBoxes();
        breedteChoice.setItems(FXCollections.observableArrayList(new String[]{"NB", "ZB"}));
        breedteChoice.setValue("NB");
        lengteChoice.setItems(FXCollections.observableArrayList(new String[]{"OL", "WL"}));
        lengteChoice.setValue("OL");
        monthOfTheYearList = FXCollections.observableList(repositoryController.getMonthsOfTheYear());
        initMonthTable();
        monthList = new ArrayList<>();
        Arrays.asList(MonthOfTheYear.values()).stream().forEach(month -> monthList.add(new Months(0, 0, month)));
        tableMonthList = FXCollections.observableList(monthList);
        monthTable.setItems(tableMonthList);
        monthTable.setEditable(true);
        months = MonthOfTheYear.values();
    }

    public void updateComboBoxes() {
        continentList = FXCollections.observableList(repositoryController.getAllContinents()
                .stream().map(c -> c.getName()).collect(Collectors.toList()));
        cbContinentCountry.setItems(continentList);
        cbContinentClimateChart.setItems(continentList);
        cbContinentClimateChart.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                countryList = FXCollections.observableList(repositoryController.getCountriesOfContinent(
                        repositoryController.findContinentByName(cbContinentClimateChart.getSelectionModel().getSelectedItem()).getId())
                        .stream().map(c -> c.getName()).collect(Collectors.toList()));
                cbCountryClimateChart.setItems(countryList);
            }
        });
    }

    @FXML
    private void addContinent(MouseEvent event) {
        repositoryController.insertContinent(new Continent(txtContinentName.getText().trim()));
        txtContinentName.clear();
        updateComboBoxes();
    }

    @FXML
    private void addCountry(MouseEvent event) {
        Continent continent = repositoryController.findContinentById(
                cbContinentCountry.getSelectionModel().getSelectedIndex() + 1);
        repositoryController.insertCountry(new Country(txtCountryName.getText().trim(), continent));
        txtCountryName.clear();
        updateComboBoxes();
    }

    @FXML
    private void addClimateChart(MouseEvent event) {
        try{
            String loc = txtLocation.getText().trim();
        int begin = Integer.parseInt(startPeriod.getText().trim());
        int end = Integer.parseInt(endPeriod.getText().trim());
        int g1 = Integer.parseInt(BGrades.getText().trim());
        int g2 = Integer.parseInt(LGrades.getText().trim());
        int m1 = Integer.parseInt(BMinutes.getText().trim());
        int m2 = Integer.parseInt(LMinutes.getText().trim());
        int s1 = Integer.parseInt(BSeconds.getText().trim());
        int s2 = Integer.parseInt(LSeconds.getText().trim());

        Country country = repositoryController.findCountryByName(
                cbCountryClimateChart.getSelectionModel().getSelectedItem());
        if (monthList.size() != 12) {
            throw new IllegalArgumentException("Er moeten 12 maanden zijn.");
        }

        ClimateChart c = new ClimateChart();
        String Bcord = c.giveCords(g1, m1, s1) + " " + breedteChoice.getValue();
        String Lcord = c.giveCords(g2, m2, s2) + " " + lengteChoice.getValue();
        double lat = c.calcDecimals(g1, m1, s1, breedteChoice.getValue());
        double longi = c.calcDecimals(g2, m2, s2, lengteChoice.getValue());
        c.setLocation(loc);
        c.setBeginperiod(begin);
        c.setEndperiod(end);
        c.setBCord(Bcord);
        c.setLCord(Lcord);
        c.setLatitude(lat);
        c.setLongitude(longi);
        c.setCountry(country);
        List<Months> maanden = new ArrayList<>();
        monthList.stream().forEach(p -> maanden.add(p));
        maanden.stream().forEach(mont -> mont.setClimateChart(c));
        c.setMonths(maanden);        
        repositoryController.InsertClimatechart(c);
        txtLocation.clear();
        startPeriod.clear();
        endPeriod.clear();
        BGrades.clear();
        BMinutes.clear();
        BSeconds.clear();
        LGrades.clear();
        LMinutes.clear();
        LSeconds.clear();
        errorBar.setText("");
        monthList = new ArrayList<>();
        Arrays.asList(MonthOfTheYear.values()).stream().forEach(month -> monthList.add(new Months(0, 0, month)));
        tableMonthList = FXCollections.observableList(monthList);
        monthTable.setItems(tableMonthList);
        }catch(Exception e)
        {
            this.errorBar.setText(e.getMessage());
        }
        
    }

    public void initMonthTable() {
        counter = 1;
        maandcol.setCellValueFactory(cellData -> cellData.getValue().monthProperty());
        tempCol.setCellValueFactory(cellData -> cellData.getValue().temperatureProperty());
        sedCol.setCellValueFactory(cellData -> cellData.getValue().sedimentProperty());
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
    private void refreshSite(MouseEvent event) {
        try {
            ClimateChart selectedClimatechart = LocationViewPanel.selectedClimatechart;
            if (selectedClimatechart == null) {
                throw new NullPointerException();
            }

            WebEngine eng = siteView.getEngine();
            eng.load(WEBSITE + "ClimateChart/ShowExercises?selectedYear=1&continentId=" + selectedClimatechart.getCountry().getContinent().getId() + "&countryId=" + selectedClimatechart.getCountry().getId() + "&climateId=" + selectedClimatechart.getId());
            webProgress.progressProperty().bind(eng.getLoadWorker().progressProperty());
            siteView.setZoom(0.70);
            eng.getLoadWorker().stateProperty().addListener(
                    new ChangeListener<Worker.State>() {
                        @Override
                        public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                            if (newState == Worker.State.SUCCEEDED) {
                                webProgress.setVisible(false);
                            }
                        }
                    });
        } catch (Exception e) {

        }
    }

    @FXML
    private void updateTempCol(TableColumn.CellEditEvent<Months, Double> event) {
        errorBar.setText(" ");
        int id = monthTable.getSelectionModel().getSelectedCells().get(0).getRow();

        if (monthTable.getSelectionModel().getSelectedCells().get(0).getColumn() == 1) {
            tableMonthList.get(id).setAverTemp(event.getNewValue());
        }
        if (monthTable.getSelectionModel().getSelectedCells().get(0).getColumn() == 2) {
            tableMonthList.get(id).setSediment(event.getNewValue().intValue());
        }
    }
}
