/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.ClassGroup;
import domain.ClassListController;
import domain.ClimateChart;
import domain.Continent;
import domain.Country;
import domain.Grade;
import domain.SchoolYear;
import domain.Student;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import util.MyNode;
import util.TextFieldTreeCellImpl;

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

    private List<Student> studentList;
    private ObservableList<Student> studentListObservable;
    private ClassGroup selectedClassGroup;

    private int currentIndex = -1;

    private ClassListController controller;
    
    //TOEVOEGING TREEVIEW OPBOUW
    private ObservableList<TreeItem<String>> obsTreeItems;
    private List<TreeItem<String>> gradeItems;
    private List<TreeItem<String>> treeItems;

    public ClassListViewPanel() {

        //Controllers
        controller = new ClassListController();
        treeItems = new ArrayList<>();
        gradeItems = new ArrayList<>();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClassListViewPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }        
        
        TreeItem<String> rootItem = new TreeItem<>("Klassen Lijst");

        for (Grade g : controller.giveAllGrades()) {
            TreeItem<String> grade = new TreeItem<>("Graad " + g.getGradeString());

            for (SchoolYear sy : controller.giveSchoolYearsOfGrade(g)) {
                TreeItem<String> schoolYear = new TreeItem<>("Leerjaar " + sy.getSchoolYearString());
                //controller.giveSchoolYearsOfGrade(g).stream().forEach(c -> System.out.println(c.getSchoolYear()));

                for (ClassGroup cg : controller.giveClassGroupOfSchoolYear(sy)) {
                    TreeItem<String> classGroup = new TreeItem<>("Klas " + cg.getGroupName());
                    schoolYear.getChildren().add(classGroup);
                }
                grade.getChildren().add(schoolYear);
                treeItems.add(schoolYear);
            }
            gradeItems.add(grade);
            treeItems.add(grade);
            //root.getChildren().add(itemChild);
        }
        
        obsTreeItems = FXCollections.observableArrayList(gradeItems);
        rootItem.getChildren().addAll(obsTreeItems);

        rootItem.setExpanded(true);
        //initMonthTable();
        //Onderstaand gedeelte maakt het mogelijk om treeviewitems "on the spot" van naam te veranderen, dit werkt alleen met treeItem<String> dus moet nog aangepast worden
        classTreeView.setEditable(true);
        /*classTreeView.setCellFactory(new Callback<TreeView<MyNode>, TreeCell<MyNode>>() {
            @Override
            public TreeCell<MyNode> call(TreeView<MyNode> p) {
                return new TextFieldTreeCellImpl(rootItem, treeItems, rc);
            }

        });*/

        //itemChild.setExpanded(false);
        classTreeView.setRoot(rootItem);
        
        classTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                TreeItem<String> selectedItem = newValue;
                if (selectedItem.getValue().contains("Klas")) {
                    selectedClassGroup = controller.giveClassGroupWithName(selectedItem.getValue().substring(5));
                    System.out.println(selectedClassGroup.getGroupName());
//                     selectedClimatechart = new ClimateChart(1,"Gent",1950,1970,true,23.34534,44.34523,"30° 45' 10\" NB ","51° 3' 15\" OL ",1);
                    updateLocationDetailPanel(selectedClassGroup);
                }
            }
        });

        //TableView opvullen
            /*De eerste kolom verbinden met de property “firstName” van de klasse Student. */
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
            //firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        //Analoog tweede kolom:
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        //tableView opvullen met data
        /*studentList = controller.giveStudentsOfClassGroup(selectedClassGroup);
        studentListObservable = FXCollections.observableArrayList(studentList);

        //Add data to the table
        studentInfoTable.setItems(studentListObservable);*/

        //Bij dubbelklik in een cel van firstNameCol, verandert de cel in een textfield
        //firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        
        //Een listener toevoegen
        studentInfoTable.getSelectionModel().selectedItemProperty().
                addListener((observableValue, oldValue, newValue) -> {
                    //Controleer of er een item is geselecteerd
                    if (newValue != null) {
                        int index = studentInfoTable.getSelectionModel().getSelectedIndex();
                        if (index != currentIndex) {//nieuwe rij is geselecteerd
                            currentIndex = index;
                            Student student = newValue;
                            System.out.printf("%d %s %s\n  ", index,
                                    student.getFirtsName(), student.getLastName());
                        }
                    }
                });

    }
    
     public void updateLocationDetailPanel(ClassGroup cg) {
         classLbl.setText(controller.giveGradeInfo(cg)); //Van de geselectreerde grade
         
        studentList = controller.giveStudentsOfClassGroup(cg);
        studentList = studentList.stream().sorted(Comparator.comparing(Student::getLastName).thenComparing(Student::getFirtsName)).collect(Collectors.toList());
        studentListObservable = FXCollections.observableArrayList(studentList);
        
        studentInfoTable.setItems(studentListObservable);
        
    }

}
