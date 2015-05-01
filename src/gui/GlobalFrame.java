package gui;

import domain.ClassListController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import repository.RepositoryController;

public class GlobalFrame extends VBox {

    @FXML
    private MenuBar mainMenu;

    @FXML
    private Menu mManage;

    @FXML
    private MenuItem miDeterminateTabel;

    @FXML
    private MenuItem miClassList;

    @FXML
    private MenuItem miClimateChart;

    @FXML
    private Menu mOverview;

    @FXML
    private MenuItem miStatistics;

    @FXML
    private MenuItem miWeb;

    @FXML
    private GridPane workPanel;

    private RepositoryController repositoryController;
    private ClassListController classListController;

    private final LocationControllerPanel locationControllerPanel;
    private final LocationViewPanel locationViewPanel;
    private final ClassListControllerPanel classListControllerPanel;
    private final ClassListViewPanel classListViewPanel;

    public GlobalFrame() {

        this.repositoryController = new RepositoryController();
        this.classListController = new ClassListController();
        locationControllerPanel = new LocationControllerPanel(repositoryController);
        locationViewPanel = new LocationViewPanel(repositoryController);
        classListControllerPanel = new ClassListControllerPanel(classListController);
        classListViewPanel = new ClassListViewPanel(classListController);
        repositoryController.addObserver(locationViewPanel);
        

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GlobalFrame.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        workPanel.add(locationControllerPanel, 0, 0);
        workPanel.add(locationViewPanel, 1, 0);

        //KLIK OP KLASLIJST
        miClassList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                workPanel.getChildren().clear();
                workPanel.add(classListControllerPanel, 0, 0);
                workPanel.add(classListViewPanel, 1, 0);
                classListController.addObserver(classListViewPanel);
            }
        });
        
        miClimateChart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                workPanel.getChildren().clear();
                workPanel.add(locationControllerPanel, 0, 0);
                workPanel.add(locationViewPanel, 1, 0);
                repositoryController.addObserver(locationViewPanel);
            }
        });

    }

//    @FXML
//    private void setClimateChartPannels(ActionEvent event){
//        try{
//            workPanel.add(new MainPanel(), 1, 0);
//        }
//        catch(SQLException ex){
//            ex.printStackTrace();
//        }
//    }
}
