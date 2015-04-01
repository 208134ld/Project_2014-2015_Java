package domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public final class TextFieldTreeCellImpl extends TreeCell<MyNode> {

    private TextField textField;
    private ContextMenu cm = new ContextMenu();

    private Connection connection;
    String url = "jdbc:sqlserver://localhost:1433;databaseName=HOGENT1415_11";
    String user = "sa";
    String password = "root";
    Statement statement;

    public TextFieldTreeCellImpl() throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
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
            MenuItem cmItem1 = new MenuItem("Add country");
            cmItem1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    System.out.println(item.value);
                }
            });
            cm.getItems().add(cmItem1);
            setContextMenu(cm);
        }

        if (getType().equalsIgnoreCase("Root")) {
            MenuItem cmItem1 = new MenuItem("Add continent");
            cmItem1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    System.out.println("Geklikt!");
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
