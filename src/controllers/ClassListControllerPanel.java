package controllers;

import domain.ClassGroup;
import domain.SchoolYear;
import domain.Student;
import java.io.IOException;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import javax.persistence.NoResultException;

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
    private Text errorText;
    private ClassListController controller;
    //lijst voor klas deel
    private ObservableList<String> gradeList;
    private ObservableList<String> schoolyearList;
    private ObservableList<String> classGroupList;

    public ClassListControllerPanel(ClassListController clc) {

        controller = clc;
        dbKlasGraad = new ComboBox<>();
        dbKlasLeerjaar = new ComboBox<>();
        dbLeerlingGraad = new ComboBox<>();
        dbLeerlingLeerjaar = new ComboBox<>();
        dbLeerlingKlas = new ComboBox<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ClassListControllerPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        //Klas deel comboBox
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

        //Leerling deel comboBox
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

                        try {
                            classGroupList = FXCollections.observableList(controller.giveClassGroupOfSchoolYear(controller.giveSchoolYearWithName(t1.toString()))
                                    .stream().map(c -> c.getGroupName()).sorted().collect(Collectors.toList()));
                            dbLeerlingKlas.setItems(classGroupList);
                            errorText.setText("");
                        } catch (NullPointerException nullex) {
                        } catch (Exception e) {
                            errorText.setText("Er is iets misgelopen. probeer opnieuw");
                        }
                    }
                });
            }
        });
    }

    @FXML
    private void addKlas(ActionEvent event) {
        SchoolYear sy = controller.giveSchoolYearWithName(dbKlasLeerjaar.getSelectionModel().getSelectedItem());//new SchoolYear(Integer.parseInt(dbKlasLeerjaar.getSelectionModel().getSelectedItem()), g);

        controller.addClassGroup(new ClassGroup(txtKlasName.getText(), sy));
        txtKlasName.clear();
    }

    @FXML
    private void addLeerling(ActionEvent event) {
        try {
            ClassGroup cg = controller.giveClassGroupWithName(dbLeerlingKlas.getSelectionModel().getSelectedItem());
            controller.addStudent(new Student(txtNaam.getText(), txtVoornaam.getText(), cg));
            txtNaam.clear();
            txtVoornaam.clear();
            errorText.setText("");
        } catch (NoResultException invok) {
            errorText.setText("De klas is niet gevonden.");
        }
    }
}
