package controllers;

import domain.ClassGroup;
import domain.Grade;
import domain.SchoolYear;
import domain.Student;
import java.io.IOException;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javax.persistence.NoResultException;

public class ClassListControllerPanel extends Accordion {

    @FXML
    private TitledPane tpKlas;
    @FXML
    private TitledPane tpLeerling;

    //Klas deel
    @FXML
    private ComboBox<Grade> dbKlasGraad;
    @FXML
    private ComboBox<SchoolYear> dbKlasLeerjaar;
    @FXML
    private TextField txtKlasName;
    @FXML
    private Button btnKlasToevoegen;

    //Leerling deel
    @FXML
    private ComboBox<Grade> dbLeerlingGraad;
    @FXML
    private ComboBox<SchoolYear> dbLeerlingLeerjaar;
    @FXML
    private ComboBox<ClassGroup> dbLeerlingKlas;
    @FXML
    private TextField txtNaam;
    @FXML
    private TextField txtVoornaam;
    @FXML
    private Button btnLeerlingToevoegen;
    @FXML
    private Text errorText;
    @FXML
    private Text errorText1;
    private ClassListController controller;
    //lijst voor klas deel
    private ObservableList<Grade> gradeList;
    private ObservableList<SchoolYear> schoolyearList;
    private ObservableList<ClassGroup> classGroupList;

    public ClassListControllerPanel(ClassListController clc) {

        controller = clc;
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
        gradeList = FXCollections.observableList(controller.giveAllGrades());
        dbKlasGraad.setItems(gradeList);
        dbKlasGraad.setValue(gradeList.get(0));
        schoolyearList = FXCollections.observableList(controller.giveSchoolYearsOfGrade(gradeList.get(0)));
        dbKlasLeerjaar.setItems(schoolyearList);
        dbKlasLeerjaar.setValue(schoolyearList.get(0));
        dbKlasGraad.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {

                schoolyearList = FXCollections.observableList(controller.giveSchoolYearsOfGrade((Grade) t1));
                dbKlasLeerjaar.setItems(schoolyearList);
                dbKlasLeerjaar.setValue(schoolyearList.get(0));
            }
        });

        //Leerling deel comboBox
        //try {
        dbLeerlingGraad.setItems(gradeList);
        dbLeerlingGraad.setValue(gradeList.get(0));
        schoolyearList = FXCollections.observableList(controller.giveSchoolYearsOfGrade((gradeList.get(0))));
        dbLeerlingLeerjaar.setItems(schoolyearList);
        dbLeerlingLeerjaar.setValue(schoolyearList.get(0));
        classGroupList = FXCollections.observableList(controller.giveClassGroupOfSchoolYear(schoolyearList.get(0)));
        classListEmpty();
        dbLeerlingLeerjaar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {

                try {
                    classGroupList = FXCollections.observableList(controller.giveClassGroupOfSchoolYear((SchoolYear) t1));
                    errorText.setText("");
                    classListEmpty();
                } catch (NullPointerException nullex) {
                } /*catch (Exception e) {
                 errorText.setText("Er is geen klas voor dit jaar");
                 }*/

            }
        }
        );
        /*} catch (Exception e) {
         errorText.setText("geen klas gevonden voor dit jaar");
         }*/

        dbLeerlingGraad.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {

                schoolyearList = FXCollections.observableList(controller.giveSchoolYearsOfGrade((Grade) t1));
                dbLeerlingLeerjaar.setItems(schoolyearList);
                dbLeerlingLeerjaar.setValue(schoolyearList.get(0));
                classGroupList = FXCollections.observableList(controller.giveClassGroupOfSchoolYear(schoolyearList.get(0)));
                classListEmpty();
                dbLeerlingLeerjaar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue ov, Object t, Object t1) {

                        try {
                            classGroupList = FXCollections.observableList(controller.giveClassGroupOfSchoolYear((SchoolYear) t1));
                            errorText.setText("");
                            classListEmpty();
                        } catch (NullPointerException nullex) {
                        } /*catch (Exception e) {
                         errorText.setText("Er is geen klas voor dit jaar");
                         }*/

                    }
                }
                );
            }
        }
        );
    }

    private void classListEmpty() {
        if (classGroupList.isEmpty()) {
            errorText.setText("Geen klas gevonden voor dit leerjaar");
            dbLeerlingKlas.setDisable(true);
            txtNaam.setDisable(true);
            txtVoornaam.setDisable(true);
        } else {
            dbLeerlingKlas.setDisable(false);
            txtNaam.setDisable(false);
            txtVoornaam.setDisable(false);
            dbLeerlingKlas.setItems(classGroupList);
            dbLeerlingKlas.setValue(classGroupList.get(0));
        }
    }
    
    @FXML
    private void fillTextFieldFirstName(KeyEvent event) {
        String regex = "^[a-zA-Z]*$";
        if(txtVoornaam.getText().trim().matches(regex) == true){
            btnLeerlingToevoegen.setDisable(false);
            txtNaam.setDisable(false);
            errorText.setText("");
        }
        else
        {
            txtNaam.setDisable(true);
            btnLeerlingToevoegen.setDisable(true);
            errorText.setText("Gelieve enkel letters te gebruiken in de voornaam");
        }
    }
    
    @FXML
    private void fillTextFieldLastName(KeyEvent event) {
        String regex = "^[a-zA-Z]*$";
        if(txtNaam.getText().trim().matches(regex) == true){
            btnLeerlingToevoegen.setDisable(false);
            txtVoornaam.setDisable(false);
            errorText.setText("");
        }
        else
        {
            txtVoornaam.setDisable(true);
            btnLeerlingToevoegen.setDisable(true);
            errorText.setText("Gelieve enkel letters te gebruiken in de achternaam");
        }
    }

    @FXML
    private void addKlas(ActionEvent event) {
        try {
            if (txtKlasName.getText().length() == 0) {
                throw new NullPointerException();
            }

            if (controller.giveAllClassGroups().contains(controller.giveClassGroupWithName(txtKlasName.getText()))) {
                errorText1.setText("Deze klasnaam bestaat al");
            }

        } catch (NoResultException nre) {
            SchoolYear sy = dbKlasLeerjaar.getSelectionModel().getSelectedItem();
            //controller.addClassGroup(new ClassGroup(txtKlasName.getText(), sy));
            ClassGroup cg = new ClassGroup(txtKlasName.getText(),sy);
            controller.addClassGroup(cg);
            classGroupList.add(cg);
            txtKlasName.clear();
            errorText1.setText("");
            classListEmpty();
        } catch (NullPointerException nule) {
            errorText1.setText("Vul alle gegevens in");
        } catch (Exception e) {
            errorText1.setText("Er is iets misgelopen. Probeer opnieuw");
        }

    }

    @FXML
    private void addLeerling(ActionEvent event) {
        try {
            if (txtNaam.getText().length() == 0 || txtVoornaam.getText().length() == 0) {
                throw new NullPointerException();
            }

            List<Student> studentsWithFName = controller.giveStudentsWithFirstName(txtVoornaam.getText());

            if (!studentsWithFName.isEmpty()) {
                for (Student s : studentsWithFName) {
                    if (s.getLastName().equals(txtNaam.getText())) {
                        errorText.setText("Deze leerling werd al toegevoegd");
                    } else {
                        ClassGroup cg = dbLeerlingKlas.getSelectionModel().getSelectedItem();
                        if (cg == null) {
                            throw new NullPointerException();
                        }
                        controller.addStudent(new Student(txtNaam.getText(), txtVoornaam.getText(), cg));
                        txtNaam.clear();
                        txtVoornaam.clear();
                        errorText.setText("");
                    }
                }
            } else {
                ClassGroup cg = dbLeerlingKlas.getSelectionModel().getSelectedItem();
                if (cg == null) {
                    throw new NullPointerException();
                }
                controller.addStudent(new Student(txtNaam.getText(), txtVoornaam.getText(), cg));
                txtNaam.clear();
                txtVoornaam.clear();
                errorText.setText("");
            }

        } catch (NoResultException invok) {
            errorText.setText("De klas is niet gevonden.");

        } catch (NullPointerException nulex) {
            errorText.setText("Vul alle velden in");
        } catch (Exception e) {
            errorText.setText("Er is iets misgelopen. Probeer opnieuw");
        }
    }
}
