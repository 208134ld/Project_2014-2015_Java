package domain;

import gui.MainPanel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import persistentie.ContinentRepository;

public final class TextFieldTreeCellImpl extends TreeCell<MyNode> {

    private TextField textField;
    private ContextMenu cm = new ContextMenu();

    private Connection connection;
    private String url = "jdbc:sqlserver://localhost:1433;databaseName=HOGENT1415_11";
    private String user = "sa";
    private String password = "root";
    private Statement statement;
    final TreeItem<MyNode> root;
    private List<TreeItem<MyNode>> treeItems;
    private ContinentRepository repository;

    public TextFieldTreeCellImpl(TreeItem<MyNode> root, List<TreeItem<MyNode>> treeItems) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
        this.root = root;
        this.treeItems = treeItems;
        repository = new ContinentRepository();
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText((String) getItem().value);
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void updateItem(MyNode item, boolean empty) {

        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(getTreeItem().getGraphic());
            }
        }

        cm.getItems().clear();

        //onderstaande if's wou ik in een switch gieten maar dit leverde een contextmenu op met 3 items bij alle levels...
        if (getType().equalsIgnoreCase("Continent")) {
            MenuItem cmItem1 = new MenuItem("Voeg land toe");
            MenuItem cmItem2 = new MenuItem("Verwijder werelddeel");
            cmItem1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    System.out.println(item.value);
                }
            });
            cmItem2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        String sql = "DELETE FROM Continents WHERE ContinentID=" + item.id ;
                        statement.executeUpdate(sql);
                    } catch (SQLException ex) {
                        Logger.getLogger(TextFieldTreeCellImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    treeItems.remove(item.id-1);
                    ObservableList obsTreeItems = FXCollections.observableArrayList(treeItems);
                                    
                    root.getChildren().clear();
                    root.getChildren().addAll(obsTreeItems);
                }
            });
            cm.getItems().add(cmItem1);
            cm.getItems().add(cmItem2);
            setContextMenu(cm);
        }

        if (getType().equalsIgnoreCase("Root")) {
            MenuItem cmItem1 = new MenuItem("Voeg werelddeel toe");
            
            cmItem1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Voeg werelddeel toe");
                    dialog.setHeaderText("Voeg werelddeel toe");
                    dialog.setContentText("Geef naam in:");
                    dialog.showAndWait()
                            .ifPresent(response -> {
                                if (!response.isEmpty()) {
                                    try {
                                        String sql = "INSERT INTO Continents (Name, ContinentID) VALUES ('"+response+"', " + (repository.getAllContinents().size()+1) + ")";
                                        statement.executeUpdate(sql);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(TextFieldTreeCellImpl.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    
                                    try {
                                        treeItems.add(new TreeItem(new MyNode(response, "Continent", repository.getAllContinents().size())));
                                    } catch (SQLException ex) {
                                        Logger.getLogger(TextFieldTreeCellImpl.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    
                                    ObservableList obsTreeItems = FXCollections.observableArrayList(treeItems);
                                    
                                    root.getChildren().clear();
                                    root.getChildren().addAll(obsTreeItems);
                                }
                            });
                }
            });
            cm.getItems().add(cmItem1);
            setContextMenu(cm);
        }

        if (getType().equalsIgnoreCase("Country")) {
            MenuItem cmItem1 = new MenuItem("Add location");
            cmItem1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    System.out.println("Geklikt!");
                }
            });
            cm.getItems().add(cmItem1);
            setContextMenu(cm);
        }

    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {

                    String sql = "UPDATE Continents SET Name='" + textField.getText() + "' WHERE ContinentID=" + getItemId();
                    if (getItem().isCountry()) {
                        sql = "UPDATE Countries SET Name='" + textField.getText() + "' WHERE CountryID=" + getItemId();
                    }
                    if (getItem().isClimateChart()) {
                        sql = "UPDATE ClimateCharts SET Location='" + textField.getText() + "' WHERE ClimateChartID=" + getItemId();
                    }

                    try {
                        ResultSet result = statement.executeQuery(sql);
                    } catch (SQLException ex) {
                        Logger.getLogger(TextFieldTreeCellImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    commitEdit(new MyNode(textField.getText(), getType(), getItemId()));
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

    private String getType() {
        return getItem() == null ? "" : getItem().type;
    }

    private int getItemId() {
        return getItem() == null ? null : getItem().id;
    }

    //    private void contextMenuHelper(String word){
//        MenuItem cmItem1 = new MenuItem("Voeg " + word + " toe!");
//        cmItem1.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                System.out.println("Geklikt!");
//            }
//        });
//        cm.getItems().add(cmItem1);
//        setContextMenu(cm);
//    }
}
