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
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import repository.RepositoryController;
import util.MyNode;
import util.TextFieldTreeCellImpl;

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
    private Label LatitudeLabel,errorBar,locatieLable;
    @FXML
    private Label LongitudeLabel;
    @FXML
    private Tab ClimateGraph;
    @FXML
    private WebView siteView;
    @FXML
    private ProgressBar webProgress;
    //private ContinentRepository continentRepository;
    public static double INPUT_NUMBER=0;
    private ClimateChart selectedClimatechart;
    private ObservableList<TreeItem<MyNode>> obsTreeItems;
    private List<TreeItem<MyNode>> treeItems;
    private List<TreeItem<MyNode>> continentItems;
    private List<TreeItem<MyNode>> countryItems;
    private DomeinController dc;
    private RepositoryController rc;
    private final String WEBSITE="http://climatechart.azurewebsites.net/";
    private ObservableList<Months> monthsList;
    public MainPanel(){
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
                    
                    selectedClimatechart = rc.getClimateChartByClimateChartID(selectedItem.getValue().id);
                    System.out.println(selectedClimatechart.getMonths().get(0).getSed());
//                     selectedClimatechart = new ClimateChart(1,"Gent",1950,1970,true,23.34534,44.34523,"30° 45' 10\" NB ","51° 3' 15\" OL ",1);
                    updateLocationDetailPanel(selectedClimatechart);
                }

            }
        });
      

    }

    public void updateLocationDetailPanel(ClimateChart c) {

        errorBar.setText("");
        locatieLable.setText(c.getLocation());
        beginPeriode.setText(c.getBeginperiod()+"");
        eindPeriode.setText(c.getEndperiod()+"");
        LatitudeLabel.setText(c.getLatitude()+"");
        LongitudeLabel.setText(c.getLongitude()+"");
        // GRADEN VAN LENGTE EN BREEDTE
        
        BGraden.setText(c.getBCord().split("°")[0].trim());
        BMinuten.setText(c.getBCord().split("°")[1].split("'")[0].trim());
        String waarde = c.getBCord().split("°")[1].split("'")[1].trim();
        BSeconden.setText(waarde.substring(0,waarde.length()-4).trim());
        BreedteParameter.setText(waarde.substring(waarde.length()-2,waarde.length()).trim());
        BGraden1.setText(c.getLCord().split("°")[0].trim());
        BMinuten1.setText(c.getLCord().split("°")[1].split("'")[0].trim());
         waarde = c.getLCord().split("°")[1].split("'")[1].trim();
        BSeconden1.setText(waarde.substring(0,waarde.length()-4).trim());
        LengteParameter.setText(waarde.substring(waarde.length()-2,waarde.length()));
        monthsList  = FXCollections.observableArrayList(c.getMonths());
        monthTable.setItems(monthsList);
    }

    public void initMonthTable() {
         
        monthTable.setEditable(true);
        maandcol.setCellValueFactory(new PropertyValueFactory("month"));
        tempCol.setCellValueFactory(new PropertyValueFactory("temp"));
        
        sedCol.setCellValueFactory(new PropertyValueFactory("sed"));
        Callback<TableColumn<Months, String>, TableCell<Months, String>> cellFactory =
                new Callback<TableColumn<Months,String>, TableCell<Months,String>>() {
                     
                    @Override
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        tempCol.setCellFactory(cellFactory);
        sedCol.setCellFactory(cellFactory);
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
            
           if(!(LengteParameter.getText().trim().equalsIgnoreCase("ol")||LengteParameter.getText().trim().equalsIgnoreCase("wl")))
               throw new IllegalArgumentException("Lengteparameter kan alleen OL of WL zijn");
           if(!(BreedteParameter.getText().equalsIgnoreCase("nb")||BreedteParameter.getText().equalsIgnoreCase("zb")))
               throw new IllegalArgumentException("Breedteparameter kan alleen NB of ZB zijn");
           String longi=  selectedClimatechart.giveCords(g1, m1, s1) + BreedteParameter.getText().toUpperCase().trim();
           String lat =   selectedClimatechart.giveCords(g2, m2, s2)+ LengteParameter.getText().toUpperCase().trim();
           selectedClimatechart.setBCord(longi);
           selectedClimatechart.setLCord(lat);
           selectedClimatechart.setBeginperiod(begin);
           selectedClimatechart.setEndperiod(einde);
           selectedClimatechart.setLatitude(selectedClimatechart.calcDecimals(g1, m1, s1,BreedteParameter.getText().trim()));
           selectedClimatechart.setLongitude(selectedClimatechart.calcDecimals(g2, m2, s2,LengteParameter.getText().trim()));
           //Database connectie
           rc.updateClimateChart(selectedClimatechart.getId(),selectedClimatechart.getLCord(), selectedClimatechart.getBCord(), selectedClimatechart.getBeginperiod(), selectedClimatechart.getEndperiod(),selectedClimatechart.getLongitude(),selectedClimatechart.getLatitude());
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
        try{
            
        if(selectedClimatechart==null)
            throw new NullPointerException();
        
        WebEngine eng = siteView.getEngine();
        //nog het continent getten
            eng.load(WEBSITE+"ClimateChart/ShowExercises?selectedYear=3&continentId="+1+"&countryId="+"1"+"&climateId="+selectedClimatechart.getId());
         webProgress.progressProperty().bind(eng.getLoadWorker().progressProperty());

    eng.getLoadWorker().stateProperty().addListener(
            new ChangeListener<State>() {
                @Override
                public void changed(ObservableValue ov, State oldState, State newState) {
                    if (newState == State.SUCCEEDED) {
                         // hide progress bar then page is ready
                         webProgress.setVisible(false);
                    }
                }
            });
        }catch(Exception e)
        {
            
        }
    }
    
    @FXML
    private void updateCol(TableColumn.CellEditEvent<Months,Double> event) {
        int id = monthTable.getSelectionModel().getSelectedCells().get(0).getRow();
        
        if(monthTable.getSelectionModel().getSelectedCells().get(0).getColumn()==1)
        {
            selectedClimatechart.getMonths().get(id).setTemp(event.getNewValue());
            rc.updateTemp(selectedClimatechart.getMonths().get(id).getMonthId(), event.getNewValue());
        }
        if(monthTable.getSelectionModel().getSelectedCells().get(0).getColumn()==2)
        {
            System.out.println("Updating sed");
            double d = event.getNewValue();
            int d2 = (int) d;
            selectedClimatechart.getMonths().get(id).setSed(d2);
            rc.updateSed(selectedClimatechart.getMonths().get(id).getMonthId(),d2);
        }
        
        
      
       
       
}
}
