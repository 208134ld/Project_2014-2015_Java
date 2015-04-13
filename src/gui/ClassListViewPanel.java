/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.ClassGroup;
import domain.ClassListController;
import domain.Grade;
import domain.SchoolYear;
import domain.Student;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author SAMUEL
 */
public class ClassListViewPanel extends GridPane {

    @FXML
    private TableView<Student> studentInfoTable;

    @FXML
    private TableColumn<Student, String> firstNameCol;

    @FXML
    private TableColumn<Student, String> lastNameCol;
    
    @FXML
    private Label classLbl;
    
    @FXML
    private TreeView classTreeView;

    private ClassListController controller;

    public ClassListViewPanel() {
        
        //Controllers
        controller = new ClassListController();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClassListViewPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        TreeItem<String> rootItem = new TreeItem<>("Klassen Lijst");
        rootItem.setExpanded(true);
        //data toevoegen in Tree
        for (Grade g : controller.giveAllGrades()) {
            TreeItem<String> grade = new TreeItem<>(g.getGradeString());
            rootItem.getChildren().add(grade); //GRADE TOEVOEGEN
            for (SchoolYear sy : controller.giveSchoolYearsOfGrade(g)) {
                TreeItem<String> schoolYear = new TreeItem<>(sy.getSchoolYearString());
                grade.getChildren().add(schoolYear); //SCHOOLYEARS TOEVOEGEN
                for (ClassGroup cg : controller.giveClassGroupOfSchoolYear(sy)) {
                    TreeItem<String> classGroup = new TreeItem<>(cg.getGroupName());
                    schoolYear.getChildren().add(classGroup); //KLASSEN TOEVOEGEN
                    for (Student s : controller.giveStudentsOfClassGroup(cg)) { //testing
                        TreeItem<String> student = new TreeItem<>(s.getFullName());
                        classGroup.getChildren().add(student); // STUDENTLIJST TOEVOEGEN
                    }
                }
            }
            //TreeView opvullen
            classTreeView = new TreeView<>(rootItem);

        }
        
        
        
    }
    
    
    
}
