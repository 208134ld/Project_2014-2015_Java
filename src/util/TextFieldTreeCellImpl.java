package util;

import domain.ClauseComponent;
import domain.Continent;
import domain.Country;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import repository.RepositoryController;

public final class TextFieldTreeCellImpl extends TreeCell<MyNode> {

    private TextField textField;
    private ContextMenu cm = new ContextMenu();
    private TreeItem<MyNode> root;
    private List<TreeItem<MyNode>> treeItems;
    private RepositoryController rc;
    
    private EntityManager em;

    public TextFieldTreeCellImpl(TreeItem<MyNode> root, List<TreeItem<MyNode>> treeItems, RepositoryController rc) throws SQLException {
        this.root = root;
        this.treeItems = treeItems;
        this.rc = rc;
        this.em = JPAUtil.getEntityManager();
    }

    @Override
    public void startEdit() {
        super.startEdit();
        System.out.println("START EDIT" +getType() + getItemId());
//        if (textField == null) {
            createTextField();
//        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText((String) getItem().getValue());
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
//       if(getType()=="ClimateChart")
//           System.out.println("CLIMATECHARTTT" +getString());
        if (getType().equalsIgnoreCase("Country")) {
            MenuItem cmItem2 = new MenuItem("Verwijder land");
           
            cmItem2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    System.out.println("VERWIJDER LAND");
                    rc.deleteCountry(item.getId());
                    treeItems.remove(getTreeItem());
                    getTreeItem().getParent().getChildren().remove(getTreeItem());
                }
            });
            cm.getItems().add(cmItem2);
            setContextMenu(cm);
        }
        if(getType().equalsIgnoreCase("climatechart")){
            MenuItem cmItem2 = new MenuItem("Verwijder klimatogram");
            cmItem2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    rc.deleteClimatechart(item.getId());
                    treeItems.remove(getTreeItem());
                    getTreeItem().getParent().getChildren().remove(getTreeItem());
                }
            });
            cm.getItems().add(cmItem2);
            setContextMenu(cm);
        }
        if(getType().equalsIgnoreCase("continent")){
            MenuItem cmItem2 = new MenuItem("Verwijder continent");
            cmItem2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    rc.deleteContinent(item.getId());
                    treeItems.remove(getTreeItem());
                    getTreeItem().getParent().getChildren().remove(getTreeItem());
                }
            });
            cm.getItems().add(cmItem2);
            setContextMenu(cm);
        }

    }
    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                   
                    if (getItem().isCountry()) {
                        rc.findCountryById(getItemId()).setName(textField.getText());
                        rc.updateRepo();
                    }
                    else if (getItem().isClimateChart()) {
                        rc.getClimateChartByClimateChartID(getItemId()).setLocation(textField.getText());
                        rc.updateRepo();
                    }
                    else if (getItem().isContinent()){
                        rc.findContinentById(getItemId()).setName(textField.getText());
                        rc.updateRepo();
                    }
                    
                    if(getItem().isClause()){
                        ClauseComponent c =rc.findClauseById(getItemId());
                        c.setName(textField.getText());
                        rc.updateRepo();
//                        rc.findClauseById(getItemId()).setName(textField.getText());
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
        return getItem() == null ? "" : getItem().getType();
    }

    public int getItemId() {
        return getItem() == null ? null : getItem().getId();
    }
}
