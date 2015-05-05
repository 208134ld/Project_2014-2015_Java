package gui;

import domain.ClassGroup;
import domain.SchoolYear;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import repository.RepositoryController;
import util.MyNode;

public class TestViewPanel extends GridPane {
    
    @FXML
    private TreeView treeViewTest;
    
    private RepositoryController rc;
    private FXMLLoader loader;
    
    public TestViewPanel(RepositoryController repositoryController){
        rc = repositoryController;
        loader = new FXMLLoader(getClass().getResource("TestViewPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ManageDeterminateTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        initialize();
    }
    
    public void initialize(){
        TreeItem<MyNode> root = new TreeItem<>(new MyNode("root", "root", 0));
        
        treeViewTest.setRoot(root);
        root.setExpanded(true);
        treeViewTest.setShowRoot(false);
        for(SchoolYear sy : rc.getAllSchoolYears()){
            TreeItem<MyNode> ti = new TreeItem<>(new MyNode("Schooljaar " + sy.getSchoolYear(), "SchoolYear", sy.getSchoolYear()));
            
            
            for(ClassGroup cg : rc.getClassGroupsOfSchoolYear(sy)){
                TreeItem<MyNode> tiCg = new TreeItem<>(new MyNode("Klasgroep " + cg.getGroupName(), "ClassGroup", cg.getGroupId()));
                ti.getChildren().add(tiCg);
            }
            ti.setExpanded(true);
            root.getChildren().add(ti);
        }
    }
    
}
