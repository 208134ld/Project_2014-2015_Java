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
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTableCell;
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

    private List<Student> studentList;
    private ObservableList<Student> studentListObservable;

    private int currentIndex = -1;

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
                    /*for (Student s : controller.giveStudentsOfClassGroup(cg)) { //testing
                        TreeItem<String> student = new TreeItem<>(s.getFullName());
                        classGroup.getChildren().add(student); // STUDENTLIJST TOEVOEGEN
                    }*/
                }
            }
        }

            //LABEL OPVULLEN
        //testing
        Grade g = new Grade(1);
        SchoolYear sy = new SchoolYear(2, g);
        ClassGroup cg = new ClassGroup("2A", sy);
        //end testing
        classLbl.setText(controller.giveGradeInfo(cg)); //Van de geselectreerde grade

        //TREEVIEW OPVULLEN
        classTreeView = new TreeView<>(rootItem);

        //TableView opvullen
            /*De eerste kolom verbinden met de property “firstName” van de klasse Student. */
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
            //firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        //Analoog tweede kolom:
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        //tableView opvullen met data
        studentList = controller.giveStudentsOfClassGroup(cg); // ---> TESTING, normaal geselecteerde klas!!!!!!!!!!!!
        studentListObservable = FXCollections.observableArrayList(studentList);

        //Add data to the table
        studentInfoTable.setItems(studentListObservable);

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

}
