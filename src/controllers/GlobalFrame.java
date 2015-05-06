package controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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
    @FXML
    private MenuItem miTest;

    private RepositoryController repositoryController;
    private ClassListController classListController;

    private final LocationControllerPanel locationControllerPanel;
    private final LocationViewPanel locationViewPanel;
    private final ClassListControllerPanel classListControllerPanel;
    private final ClassListViewPanel classListViewPanel;
    private final ManageDeterminateTable manageDeterminateTable;
    private final TestControllerPanel testControllerPanel;
    private final TestViewPanel testViewPanel;
    private double minWidth;
    private double prefWidth;
    private double maxWidth;
    private Priority priority;

    public GlobalFrame() {

        this.repositoryController = new RepositoryController();
        this.classListController = new ClassListController();
        locationControllerPanel = new LocationControllerPanel(repositoryController);
        locationViewPanel = new LocationViewPanel(repositoryController);
        classListControllerPanel = new ClassListControllerPanel(classListController);
        classListViewPanel = new ClassListViewPanel(classListController);
        manageDeterminateTable = new ManageDeterminateTable(repositoryController);
        testControllerPanel = new TestControllerPanel(repositoryController);
        testViewPanel = new TestViewPanel(repositoryController);
        repositoryController.addObserver(locationViewPanel);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GlobalFrame.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        workPanel.add(locationControllerPanel, 0, 0);
        workPanel.add(locationViewPanel, 1, 0);

        minWidth = workPanel.getColumnConstraints().get(0).getMinWidth();
        prefWidth = workPanel.getColumnConstraints().get(0).getPrefWidth();
        maxWidth = workPanel.getColumnConstraints().get(0).getMaxWidth();
        priority = workPanel.getColumnConstraints().get(0).getHgrow();

        //KLIK OP KLASLIJST
        miClassList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                resetConstraints();
                workPanel.add(classListControllerPanel, 0, 0);
                workPanel.add(classListViewPanel, 1, 0);
                classListController.addObserver(classListViewPanel);
            }
        });

        miClimateChart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                resetConstraints();
                workPanel.add(locationControllerPanel, 0, 0);
                workPanel.add(locationViewPanel, 1, 0);
                repositoryController.addObserver(locationViewPanel);
            }
        });

        miDeterminateTabel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                workPanel.getChildren().clear();
                workPanel.getColumnConstraints().get(0).setMinWidth(0);
                workPanel.getColumnConstraints().get(0).setPrefWidth(0);
                workPanel.getColumnConstraints().get(0).setMaxWidth(0);
                workPanel.getColumnConstraints().get(0).setHgrow(Priority.NEVER);
                workPanel.getColumnConstraints().get(1).setMinWidth(minWidth);
                workPanel.getColumnConstraints().get(1).setPrefWidth(prefWidth);
                workPanel.getColumnConstraints().get(1).setMaxWidth(maxWidth);
                workPanel.getColumnConstraints().get(1).setHgrow(priority);
                workPanel.add(manageDeterminateTable, 1, 0);
            }
        });

        miTest.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                resetConstraints();
                workPanel.add(testViewPanel, 0, 0);
                workPanel.add(testControllerPanel, 1, 0);
                testViewPanel.initialize();
                //testControllerPanel.addObserver(testViewPanel);
            }
        });
    }

    private void resetConstraints() {
        workPanel.getChildren().clear();
        workPanel.getColumnConstraints().get(0).setMinWidth(minWidth);
        workPanel.getColumnConstraints().get(0).setPrefWidth(prefWidth);
        workPanel.getColumnConstraints().get(0).setMaxWidth(maxWidth);
        workPanel.getColumnConstraints().get(0).setHgrow(priority);
        workPanel.getColumnConstraints().get(1).setMinWidth(minWidth);
        workPanel.getColumnConstraints().get(1).setPrefWidth(prefWidth);
        workPanel.getColumnConstraints().get(1).setMaxWidth(maxWidth);
        workPanel.getColumnConstraints().get(1).setHgrow(priority);
    }
}
