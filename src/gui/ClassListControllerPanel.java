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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

/**
 * FXML Controller class
 *
 * @author SAMUEL
 */
public class ClassListControllerPanel extends Accordion {

    @FXML
    private TitledPane tpKlas;
    @FXML
    private TitledPane tpLeerling;

    //Klas deel
    @FXML
    private ComboBox<String> dbKlasGraad;
    @FXML
    private ComboBox<String> dbKlasLeerjaar;
    @FXML
    private TextField txtKlasName;
    @FXML
    private Button btnKlasToevoegen;
    @FXML
    private Button btnKlasVerwijderen;

    //Leerling deel
    @FXML
    private ComboBox<String> dbLeerlingGraad;
    @FXML
    private ComboBox<String> dbLeerlingLeerjaar;
    @FXML
    private ComboBox<String> dbLeerlingKlas;
    @FXML
    private TextField txtNaam;
    @FXML
    private TextField txtVoornaam;
    @FXML
    private Button btnLeerlingToevoegen;
    @FXML
    private Button btnLeerlingVerwijderen;

    private ClassListController controller;
    //lijst voor klas deel
    private ObservableList<String> gradeList;
    private ObservableList<String> schoolyearList;
    private ObservableList<String> classGroupList;

    public ClassListControllerPanel() {

        controller = new ClassListController();
        dbKlasGraad = new ComboBox<>();
        dbKlasLeerjaar = new ComboBox<>();
        dbLeerlingGraad = new ComboBox<>();
        dbLeerlingLeerjaar = new ComboBox<>();
        dbLeerlingKlas = new ComboBox<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClassListControllerPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        //Klas deel
        setExpandedPane(tpKlas);

        gradeList = FXCollections.observableList(controller.giveAllGrades()
                .stream().map(c -> c.getGradeString()).sorted().collect(Collectors.toList()));
        dbKlasGraad.setItems(gradeList);
        dbKlasGraad.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {

                schoolyearList = FXCollections.observableList(controller.giveSchoolYearsOfGrade(controller.giveGradeWithName(t1.toString()))
                        .stream().map(c -> c.getSchoolYearString()).sorted().collect(Collectors.toList()));
                dbKlasLeerjaar.setItems(schoolyearList);
            }
        });

        //Leerling deel
        dbLeerlingGraad.setItems(gradeList);
        dbLeerlingGraad.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {

                schoolyearList = FXCollections.observableList(controller.giveSchoolYearsOfGrade(controller.giveGradeWithName(t1.toString()))
                        .stream().map(c -> c.getSchoolYearString()).sorted().collect(Collectors.toList()));
                dbLeerlingLeerjaar.setItems(schoolyearList);
                dbLeerlingKlas.setItems(null);
                dbLeerlingLeerjaar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue ov, Object t, Object t1) {

                        classGroupList = FXCollections.observableList(controller.giveClassGroupOfSchoolYear(controller.giveSchoolYearWithName(t1.toString()))
                                .stream().map(c -> c.getGroupName()).sorted().collect(Collectors.toList()));
                        dbLeerlingKlas.setItems(classGroupList);
                    }
                });
            }
        });

    }

    @FXML
    private void addKlas(ActionEvent event) {
        Grade g = new Grade(Integer.parseInt(dbKlasGraad.getSelectionModel().getSelectedItem()));
        SchoolYear sy = new SchoolYear(Integer.parseInt(dbKlasLeerjaar.getSelectionModel().getSelectedItem()), g);
        controller.addClassGroup(new ClassGroup(txtKlasName.getText(), sy));
        txtKlasName.clear();
    }

    @FXML
    private void removeKlas(ActionEvent event) {
        try {
            controller.removeClassGroup(controller.giveClassGroupWithName(txtKlasName.getText()));
            txtKlasName.clear();
        } catch (Exception e) {
            System.out.println("Klas niet gevonden");
        }
    }

    @FXML
    private void addLeerling(ActionEvent event) {
        Grade g = new Grade(Integer.parseInt(dbLeerlingGraad.getSelectionModel().getSelectedItem()));
        SchoolYear sy = new SchoolYear(Integer.parseInt(dbLeerlingLeerjaar.getSelectionModel().getSelectedItem()), g);
        ClassGroup cg = new ClassGroup(dbLeerlingKlas.getSelectionModel().getSelectedItem(), sy);
        controller.addStudent(new Student(txtNaam.getText(), txtVoornaam.getText(), cg));
        txtNaam.clear();
        txtVoornaam.clear();
    }

    @FXML
    private void removeLeerling(ActionEvent event) {
        //controller.removeStudent(controller.);
    }

}
