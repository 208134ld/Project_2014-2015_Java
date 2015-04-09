package util;

import domain.Continent;
import domain.Country;
import gui.LocationWizardController;
import gui.MainPanel;
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
    final TreeItem<MyNode> root;
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

        //if (textField == null) {
            createTextField();
        //}
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
                    String tekst = "";
                    TextInputDialog dialog1 = new TextInputDialog();
                    dialog1.setTitle("Voeg land toe");
                    dialog1.setHeaderText("Voeg land toe");
                    dialog1.setContentText("Geef naam in:");
                    dialog1.showAndWait()
                            .ifPresent(response -> {
                                if (!response.isEmpty()) {
                                    Country c = new Country(response, rc.findContinentById(item.id));
                                    
                                    TreeItem<MyNode> ti = new TreeItem<>();
                                    
                                    rc.insertCountry(c);
                                    
                                    for (TreeItem<MyNode> t : treeItems) {
                                        if (t.getValue().type.equalsIgnoreCase(item.type) && t.getValue().value.equalsIgnoreCase(item.value)) {
                                            ti = t;
                                        }
                                    }
                                    TreeItem<MyNode> node = new TreeItem(new MyNode(response, "Country", c.getId()));
                                    //node.
                                    treeItems.add(node);
                                    
                                    

                                    ti.getChildren().add(node);
                                }
                            });

                    //out.println(item);
                }
            });
            cmItem2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    em.getTransaction().begin();
                    em.remove(rc.findContinentById(item.id));
                    em.getTransaction().commit();
                    treeItems.remove(getTreeItem());
                    List<TreeItem<MyNode>> continentItems = new ArrayList<>();
                                    
                    for (TreeItem<MyNode> t : treeItems) {
                        if (t.getValue().type.equalsIgnoreCase("Continent")) {
                            continentItems.add(t);
                        }
                    }
                    
                    
                    //System.out.println(continentItems);
                    ObservableList obsTreeItems = FXCollections.observableArrayList(continentItems);
                    
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
                                    Continent c = new Continent(response);
                                    rc.insertContinent(c);
                                    TreeItem<MyNode> node = new TreeItem(new MyNode(response, "Continent", c.getId()));
                                    
                                    treeItems.add(node);
                                    List<TreeItem<MyNode>> continentItems = new ArrayList<>();
                                    
                                    for (TreeItem<MyNode> t : treeItems) {
                                        if (t.getValue().type.equalsIgnoreCase("Continent")) {
                                            continentItems.add(t);
                                        }
                                    }
                                    
                                    ObservableList obsTreeItems = FXCollections.observableArrayList(continentItems);

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
            MenuItem cmItem1 = new MenuItem("Voeg klimatogram toe");
            MenuItem cmItem2 = new MenuItem("Verwijder land");
            cmItem1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    showStage();
                    System.out.println("Nog niet geïmplementeerd.");
                }
            });
            cmItem2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    em.getTransaction().begin();
                    em.remove(rc.findCountryById(item.id));
                    em.getTransaction().commit();
                    
                    treeItems.remove(getTreeItem());
                    
                    TreeItem<MyNode> ti = getTreeItem();
                    List<TreeItem<MyNode>> countryItems = new ArrayList<>();
                    
                    for (TreeItem<MyNode> t : treeItems) {
                        if ((!t.getValue().value.equalsIgnoreCase(ti.getValue().value)&&(t.getParent().getValue().id==ti.getParent().getValue().id))){
                            countryItems.add(t);
                        }
                    }
                    ObservableList obsTreeItems = FXCollections.observableArrayList(countryItems);
                    ObservableList p = ti.getParent().getChildren();
                    p.clear();
                    p.addAll(obsTreeItems);
                }
            });
            cm.getItems().add(cmItem1);
            cm.getItems().add(cmItem2);
            setContextMenu(cm);
        }

    }
public static void showStage(){
Stage newStage = new Stage();
Scene stageScene = new Scene(new LocationWizardController());
newStage.setScene(stageScene);
newStage.show();
}
    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {

                    em.getTransaction().begin();
                    
                    if (getItem().isCountry()) {
                        rc.findCountryById(getItemId()).setName(textField.getText());
                    }
                    else if (getItem().isClimateChart()) {
                        rc.getClimateChartByClimateChartID(getItemId()).setLocation(textField.getText());
                    }
                    else if (getItem().isContinent()){
                        rc.findContinentById(getItemId()).setName(textField.getText());
                    }
                    em.getTransaction().commit();

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
}
