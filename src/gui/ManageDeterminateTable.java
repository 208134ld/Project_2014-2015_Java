
package gui;

import domain.ClauseComponent;
import domain.DomeinController;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import repository.RepositoryController;
import util.MyNode;
import util.TextFieldTreeCellImpl;

public class ManageDeterminateTable extends GridPane {

    @FXML
    TreeView treeViewDeterminateTable;
    @FXML
    private TextField txtGradeField;
    @FXML
    private Button btnViewDeterminateTable;
    @FXML
    private Label lblActiveDeterminateTable;

    private ObservableList<TreeItem<MyNode>> obsTreeItems;
    private List<TreeItem<MyNode>> treeItems;
    private DomeinController dc;
    private RepositoryController rc;
    private TreeItem<MyNode> root;

    public ManageDeterminateTable() {
        dc = new DomeinController();
        rc = new RepositoryController();
        treeItems = new ArrayList<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageDeterminateTable.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    public void viewDeterminateTable() {
        
        
        List<ClauseComponent> clauses = rc.findClausesByDeterminateTableId(rc.findGradeById(Integer.parseInt(txtGradeField.getText())).getDeterminateTableId());
        List<Integer> ids = new ArrayList<>();

        lblActiveDeterminateTable.setText(String.format("U bekijkt momenteel determinatietabel %d, deze hoort bij graad %d.",
                rc.findGradeById(Integer.parseInt(txtGradeField.getText())).getDeterminateTableId(), Integer.parseInt(txtGradeField.getText())));
        
        clauses.stream().map((c) -> {
            ids.add(c.getYesClause());
            return c;
        }).forEach((c) -> {
            ids.add(c.getNoClause());
        });

        ClauseComponent rootClause = null;

        for (ClauseComponent c : clauses) {
            if (!ids.contains(c.getClauseComponentId())) {
                root = new TreeItem<>(new MyNode(c.getName(), "Clause", c.getClauseComponentId()));
                rootClause = c;
            }
        }
        
        recursiveClause(root, rootClause, true);
        recursiveClause(root, rootClause, false);
        
        obsTreeItems = FXCollections.observableArrayList(treeItems);
        root.getChildren().addAll(obsTreeItems);

        root.setExpanded(true);

        treeViewDeterminateTable.setRoot(root);
        
        treeViewDeterminateTable.setEditable(true);
        treeViewDeterminateTable.setCellFactory(new Callback<TreeView<MyNode>, TreeCell<MyNode>>() {
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
        
        treeViewDeterminateTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<MyNode>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<MyNode>> observable, TreeItem<MyNode> oldValue, TreeItem<MyNode> newValue) {
                TreeItem<MyNode> selectedItem = newValue;
            }
        });
    }
    
    public void recursiveClause(TreeItem<MyNode> node, ClauseComponent parentClause, Boolean typeClause){
        ClauseComponent clause;
        if(typeClause){
            clause = rc.findClauseById(parentClause.getYesClause());
        }
        else{
            clause = rc.findClauseById(parentClause.getNoClause());
        }
        if(clause.getName()!=null){
            TreeItem<MyNode> newNode = new TreeItem<>(new MyNode(clause.getName(), "Clause", clause.getClauseComponentId()));
            recursiveClause(newNode, clause, true);
            recursiveClause(newNode, clause, false);
            node.getChildren().add(newNode);
        }
        else{
            TreeItem<MyNode> newNode = new TreeItem<>(new MyNode(clause.getClimatefeature(), "Result", clause.getClauseComponentId()));
            node.getChildren().add(newNode);
        }
    }

}
