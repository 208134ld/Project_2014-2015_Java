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
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
    private Label LatitudeLabel,errorBar;
    @FXML
    private Label LongitudeLabel;
        @FXML
    private Tab ClimateGraph;
    @FXML
    private WebView siteView;
    //private ContinentRepository continentRepository;
    private ClimateChart selectedClimatechart;

    private ObservableList<TreeItem<MyNode>> obsTreeItems;
    private List<TreeItem<MyNode>> treeItems;
    private List<TreeItem<MyNode>> continentItems;
    private List<TreeItem<MyNode>> countryItems;

    private DomeinController dc;
    private RepositoryController rc;
    private final String WEBSITE="http://climatechart.azurewebsites.net/";
    public MainPanel() throws SQLException {
        dc = new DomeinController();
        rc = new RepositoryController();

        //continentRepository = dc.getConRepo();
        treeItems = new ArrayList<>();
        continentItems = new ArrayList<>();
        countryItems = new ArrayList<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPanel.fxml"));
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
            //root.getChildren().add(itemChild);
        }
        
        obsTreeItems = FXCollections.observableArrayList(continentItems);
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
                    selectedClimatechart = new ClimateChart(1,"Gent",1950,1970,true,23.34534,44.34523,"30° 45' 10\" NB ","51° 3' 15\" OL ",1);
                    updateLocationDetailPanel(selectedClimatechart);
                }

            }
        });

    }

    public void updateLocationDetailPanel(ClimateChart c) {

        errorBar.setText("");
        beginPeriode.setText(c.getBeginperiod()+"");
        eindPeriode.setText(c.getEndperiod()+"");
        LatitudeLabel.setText(c.getLatitude()+"");
        LongitudeLabel.setText(c.getLongitude()+"");
        // GRADEN VAN LENGTE EN BREEDTE
        
        BGraden.setText(c.getLCord().split("°")[0].trim());
        BMinuten.setText(c.getLCord().split("°")[1].split("'")[0].trim());
        String waarde = c.getLCord().split("°")[1].split("'")[1].trim();
        BSeconden.setText(waarde.substring(0,waarde.length()-4).trim());
        BreedteParameter.setText(waarde.substring(waarde.length()-2,waarde.length()).trim());
        BGraden1.setText(c.getBCord().split("°")[0].trim());
        BMinuten1.setText(c.getBCord().split("°")[1].split("'")[0].trim());
         waarde = c.getBCord().split("°")[1].split("'")[1].trim();
        BSeconden1.setText(waarde.substring(0,waarde.length()-4).trim());
        
        LengteParameter.setText(waarde.substring(waarde.length()-2,waarde.length()));
//        ObservableList<Months> m = FXCollections.observableArrayList(c.getMonths());
//        monthTable.setItems(FXCollections.observableArrayList(m));

    }

    public void initMonthTable() {
        maandcol.setCellValueFactory(new PropertyValueFactory("month"));
        tempCol.setCellValueFactory(new PropertyValueFactory("temp"));
        sedCol.setCellValueFactory(new PropertyValueFactory("sed"));
    }
    
    @FXML
    private void saveDetaillWindow(MouseEvent event) {
        try{
           
            int g1 = Integer.parseInt(BGraden.getText().trim());
            int g2 = Integer.parseInt(BGraden1.getText().trim());
            int m1 = Integer.parseInt(BMinuten.getText().trim());
            int m2 = Integer.parseInt(BMinuten1.getText().trim());
            int s1 = Integer.parseInt(BSeconden.getText().trim());
            int s2 = Integer.parseInt(BSeconden1.getText().trim());
            int begin = Integer.parseInt(beginPeriode.getText().trim());
            int einde = Integer.parseInt(eindPeriode.getText().trim());     
           if(!(BreedteParameter.getText().trim().equalsIgnoreCase("ol")||BreedteParameter.getText().trim().equalsIgnoreCase("wl")))
               throw new IllegalArgumentException("Breedteparameter kan alleen OL of WL zijn");
           if(!(LengteParameter.getText().equalsIgnoreCase("nb")||!LengteParameter.getText().equalsIgnoreCase("zb")))
               throw new IllegalArgumentException("Lengteparameter kan alleen NB of ZB zijn");
           String longi=  selectedClimatechart.giveCords(g1, m1, s1) + BreedteParameter.getText().toUpperCase().trim();
           String lat =   selectedClimatechart.giveCords(g2, m2, s2)+ LengteParameter.getText().toUpperCase().trim();
           selectedClimatechart.setBCord(lat);
           selectedClimatechart.setLCord(longi);
           selectedClimatechart.setBeginperiod(begin);
           selectedClimatechart.setEndperiod(einde);
           selectedClimatechart.setLatitude(selectedClimatechart.calcDecimals(g1, m1, s1,BreedteParameter.getText().trim()));
           selectedClimatechart.setLongitude(selectedClimatechart.calcDecimals(g2, m2, s2,LengteParameter.getText().trim()));
           //Database connectie
           updateLocationDetailPanel(selectedClimatechart);
            
        }catch(NumberFormatException ex)
        {
            errorBar.setText("Pars error. hebt u tekst in de tekstbox staan?");
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
    private void RefreshSite(Event event) {
        
        WebEngine eng = siteView.getEngine();
        //nog het continent getten
            eng.load(WEBSITE+"ClimateChart/ShowExercises?selectedYear=3&continentId="+1+"&countryId="+"1"+"&climateId="+selectedClimatechart.getId());
        
    }
    
    

}
