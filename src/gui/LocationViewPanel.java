package gui;

import com.sun.deploy.uitoolkit.impl.fx.DeployPerfLogger;
import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import domain.MonthOfTheYear;
import domain.Months;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import repository.RepositoryController;
import util.MyNode;

/**
 * FXML Controller class
 *
 * @author Logan Dupont
 */
public class LocationViewPanel extends GridPane implements Observer{

    @FXML
    private TreeView selectionTreeView;

    @FXML
    private Label locationLable;
    @FXML
    private Label errorBar;
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
    
        initMonthTable();
        updateSelectionTreeViewPanel();
    }

    public void updateLocationDetailPanel(ClimateChart c) {

        locationLable.setText(c.getLocation());
        errorBar.setText("");
        
        // GRADEN VAN LENGTE EN BREEDTE
        txtBGrades.setText(c.getBCord().split("°")[0].trim());
        txtBMinutes.setText(c.getBCord().split("°")[1].split("'")[0].trim());
        String waarde = c.getBCord().split("°")[1].split("'")[1].trim();
        txtBSeconds.setText(waarde.substring(0,waarde.length()-4).trim());
        txtBreedteParameter.setText(waarde.substring(waarde.length()-2,waarde.length()).trim());
        txtLGrades.setText(c.getLCord().split("°")[0].trim());
        txtLMinutes.setText(c.getLCord().split("°")[1].split("'")[0].trim());
        waarde = c.getLCord().split("°")[1].split("'")[1].trim();
        txtLSeconds.setText(waarde.substring(0,waarde.length()-4).trim());
        txtLengteParameter.setText(waarde.substring(waarde.length()-2,waarde.length()));
        
        txtBeginPeriod.setText(c.getBeginperiod()+"");
        txtEndPeriod.setText(c.getEndperiod()+"");
        lbTemperatureYear.setText(Double.toString(Math.round(c.calcAverageYearTemp().getAsDouble()*10.0)/10.0)+ " °C");
        lbSedimentYear.setText(Integer.toString(c.calcSedimentYear())+ " mm");
        
        // TABLE
        monthsList = FXCollections.observableArrayList(c.getMonths());
        monthTable.setItems(monthsList);
    }
    
    public void updateSelectionTreeViewPanel() {
        final TreeItem<MyNode> root = new TreeItem<>(new MyNode("Aardrijkskunde database", "Root", 1));

        for (Continent c : rc.getAllContinents()) {

            TreeItem<MyNode> itemChild = new TreeItem<>(new MyNode(c.toString(), "Continent", c.getId()));
//            itemChild.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent e) {
//                    if (e.getButton() == MouseButton.SECONDARY) {
//                        ContextMenu cm = new ContextMenu();
//                        MenuItem cmItem1 = new MenuItem("Verwijder werelddeel");
//                        cm.getItems().add(cmItem1);
//                    }
//                    
//                }
//            });

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
    
    public void initMonthTable() {
        monthcol.setCellValueFactory(cellData -> cellData.getValue().monthProperty());
        sedCol.setCellValueFactory(cellData -> cellData.getValue().sedimentProperty());
        tempCol.setCellValueFactory(cellData -> cellData.getValue().temperatureProperty());
    }
    
    @FXML
    private void saveDetaillWindow(MouseEvent event) {
        try{
           
            int g1 = Integer.parseInt(txtBGrades.getText().trim());
            int g2 = Integer.parseInt(txtLGrades.getText().trim());
            int m1 = Integer.parseInt(txtBMinutes.getText().trim());
            int m2 = Integer.parseInt(txtLMinutes.getText().trim());
            int s1 = Integer.parseInt(txtBSeconds.getText().trim());
            int s2 = Integer.parseInt(txtLSeconds.getText().trim());
            int begin = Integer.parseInt(txtBeginPeriod.getText().trim());
            int end = Integer.parseInt(txtEndPeriod.getText().trim());
            
           if(!(txtLengteParameter.getText().trim().equalsIgnoreCase("ol")||txtLengteParameter.getText().trim().equalsIgnoreCase("wl")))
               throw new IllegalArgumentException("Lengteparameter kan alleen OL of WL zijn");
           if(!(txtBreedteParameter.getText().equalsIgnoreCase("nb")||txtBreedteParameter.getText().equalsIgnoreCase("zb")))
               throw new IllegalArgumentException("Breedteparameter kan alleen NB of ZB zijn");
           String longi=  selectedClimatechart.giveCords(g1, m1, s1) + txtBreedteParameter.getText().toUpperCase().trim();
           String lat =   selectedClimatechart.giveCords(g2, m2, s2)+ txtLengteParameter.getText().toUpperCase().trim();
           selectedClimatechart.setBCord(longi);
           selectedClimatechart.setLCord(lat);
           selectedClimatechart.setBeginperiod(begin);
           selectedClimatechart.setEndperiod(end);
           selectedClimatechart.setLatitude(selectedClimatechart.calcDecimals(g1, m1, s1,txtBreedteParameter.getText().trim()));
           selectedClimatechart.setLongitude(selectedClimatechart.calcDecimals(g2, m2, s2,txtLengteParameter.getText().trim()));
           //Database connectie
           rc.updateClimateChart(selectedClimatechart.getId(),selectedClimatechart.getLCord(), selectedClimatechart.getBCord(), selectedClimatechart.getBeginperiod(), selectedClimatechart.getEndperiod(),selectedClimatechart.getLongitude(),selectedClimatechart.getLatitude());
           updateLocationDetailPanel(selectedClimatechart);
           
        }catch(NumberFormatException ex)
        {
            errorBar.setText("Gelieve getallen in te voeren in de tekstvakken");
        }
        catch(IllegalArgumentException ilExc)
        {
            errorBar.setText(ilExc.getMessage());
        }

        catch(Exception e)
        {
            errorBar.setText("Er is een fout opgetreden "+e.getMessage());
        }
    }
    
    @FXML
    private void updateCol(TableColumn.CellEditEvent<Months,Double> event) {
        int id = monthTable.getSelectionModel().getSelectedCells().get(0).getRow();
        
        if(monthTable.getSelectionModel().getSelectedCells().get(0).getColumn()==1)
        {
            System.out.println("Updating temperature");
            selectedClimatechart.getMonths().get(id).setAverTemp(event.getNewValue());
            rc.updateTemp(selectedClimatechart.getMonths().get(id).getMonthId(), event.getNewValue());
        }
        if(monthTable.getSelectionModel().getSelectedCells().get(0).getColumn()==2)
        {
            System.out.println("Updating sediment");
            double d = event.getNewValue();
            int d2 = (int) d;
            selectedClimatechart.getMonths().get(id).setSediment(d2);
            rc.updateSed(selectedClimatechart.getMonths().get(id).getMonthId(),d2);
        }
        
        updateLocationDetailPanel(selectedClimatechart);
    }

    @Override
    public void update(Observable o, Object arg) {
        updateSelectionTreeViewPanel();
    }

}
