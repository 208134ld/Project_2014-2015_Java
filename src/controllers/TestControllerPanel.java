package controllers;

import domain.ClimateChart;
import domain.DeterminateTable;
import domain.Exercise;
import domain.Grade;
import domain.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import repository.RepositoryController;

public class TestControllerPanel extends TitledPane {

    @FXML
    private TextField Titel;
    @FXML
    private TextArea Beschrijving;
    @FXML
    private DatePicker begindatum;
    @FXML
    private DatePicker einddatum;
    @FXML
    private ComboBox<Grade> graad;
    @FXML
    private ComboBox<Exercise> oefening;
    @FXML
    private TextField vraag;
    @FXML
    private ComboBox<String> alleVragen;
    @FXML
    private Button voegVraagToe;
    @FXML
    private Button verwijderVraag;
    @FXML
    private ComboBox<ClimateChart> klimatogram;
    @FXML
    private ComboBox<DeterminateTable> determinatieTabel;
    @FXML
    private Button newOef;
    @FXML
    private Button voegTestToe;
    @FXML
    private Text errorText;
    private RepositoryController rc;
    private FXMLLoader loader;
    private ObservableList<ClimateChart> climateCharts;
    private ObservableList<DeterminateTable> detTable;
    private ObservableList<Grade> grades;
    private ObservableList<Exercise> exercises;
    private ObservableList<String> vragen;
    private int oefeningenCounter = 1;

    public TestControllerPanel(RepositoryController repositoryController) {
        rc = repositoryController;
        loader = new FXMLLoader(getClass().getResource("/gui/TestControllerPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        init();

    }

    private void init() {
        try {
            climateCharts = FXCollections.observableArrayList(rc.findAllClimateCharts());
            klimatogram.setItems(climateCharts);
            klimatogram.setValue(climateCharts.get(0));
            detTable = FXCollections.observableArrayList(rc.getAllDeterminateTables());
            determinatieTabel.setItems(detTable);
            determinatieTabel.setValue(detTable.get(0));
            grades = FXCollections.observableArrayList(rc.getAllGrades());
            graad.setItems(grades);
            graad.setValue(grades.get(0));
            List<Exercise> lijst = new ArrayList<>();
            lijst.add(new Exercise("oefening " + oefeningenCounter));
            exercises = FXCollections.observableArrayList(lijst);
            oefening.setItems(exercises);
            oefening.setValue(exercises.get(0));
            oefeningenCounter++;
        } catch (Exception e) {

        }
        graad.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                if (graad.getSelectionModel().selectedItemProperty().get().getGrade() == 2) {
                    setEditableGraad2(true);
                } else {
                    setEditableGraad2(false);
                }
            }
        });
        oefening.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                try {
                    Exercise e = oefening.getSelectionModel().getSelectedItem();
                    klimatogram.setValue(e.getClimateChart());
                    determinatieTabel.setValue(e.getDetTable());
                    vragen = FXCollections.observableArrayList(e.getQuestions());
                    alleVragen.setItems(vragen);
                    alleVragen.setValue(vragen.get(0));
                } catch (Exception e) {

                }
            }
        });
        klimatogram.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                try {
                    oefening.getSelectionModel().getSelectedItem().setClimateChart(klimatogram.getSelectionModel().getSelectedItem());
                } catch (Exception e) {

                }
            }
        });
        determinatieTabel.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                try {
                    oefening.getSelectionModel().getSelectedItem().setDetTable(determinatieTabel.getSelectionModel().getSelectedItem());
                } catch (Exception e) {

                }
            }
        });
    }

    private void setEditableGraad2(boolean waarde) {
        vraag.setDisable(waarde);
        alleVragen.setDisable(waarde);
        voegVraagToe.setDisable(waarde);
        verwijderVraag.setDisable(waarde);
    }

    @FXML
    private void saveOefening(MouseEvent event) {
        try {
            Test t = new Test();
            t.setTitle(Titel.getText());
            t.setStartDate(new GregorianCalendar(begindatum.getValue().getYear(), begindatum.getValue().getMonth().getValue(), begindatum.getValue().getDayOfMonth()));
            t.setEndDate(new GregorianCalendar(einddatum.getValue().getYear(), einddatum.getValue().getMonth().getValue(), einddatum.getValue().getDayOfMonth()));
            t.setDescription(Beschrijving.getText());
            voegDataToe();
            t.setExercises(exercises);

            //DATABASE TOEVOEGEN
            Titel.clear();
        } catch (NullPointerException nule) {
            errorText.setText("Een oefening moet minstens 1 vraag hebben");
        } catch (Exception e) {
            errorText.setText(e.getMessage());
        }
    }

    @FXML
    private void voegVraagToe(MouseEvent event) {
        try {
            String vraagbox = vraag.getText();
            if (vragen == null) {
                List<String> eenVraag = new ArrayList<String>();
                eenVraag.add(vraagbox);
                vragen = FXCollections.observableArrayList(eenVraag);
                alleVragen.setItems(vragen);
                alleVragen.setValue(vragen.get(0));
                vraag.clear();
            } else {
                vragen.add(vraagbox);
                vraag.clear();
                alleVragen.setValue(vragen.get(vragen.size() - 1));
            }

        } catch (NullPointerException nulEx) {
            errorText.setText("Geen vraag gevonden");
        } catch (Exception e) {
            errorText.setText(e.getMessage());
        }
    }

    @FXML
    private void verwijderVraag(MouseEvent event) {
        try {
            vragen.remove(alleVragen.getSelectionModel().getSelectedItem());
        } catch (NullPointerException nulexc) {
            errorText.setText("kon vraag niet verwijderen omdat er geen vraag geselecteerd is");
        } catch (Exception e) {
            errorText.setText(e.getMessage());
        }
    }

    @FXML
    private void nieuweoefening(MouseEvent event) {
        try {
            voegDataToe();
            if ((oefeningenCounter - oefening.getSelectionModel().getSelectedIndex()) == 2) {
                exercises.add(new Exercise("oefening " + this.oefeningenCounter));
                oefening.setValue(exercises.get(oefeningenCounter - 1));
                oefeningenCounter++;
                graad.setDisable(true);
                resetLists();
            }
        } catch (NullPointerException nule) {
            errorText.setText("Een oefening moet minstens 1 vraag hebben");
        } catch (Exception e) {
            errorText.setText("Onbekende fout " + e.getMessage());
        }
    }

    private void voegDataToe() {
        ClimateChart c = klimatogram.getSelectionModel().getSelectedItem();
        DeterminateTable d = determinatieTabel.getSelectionModel().getSelectedItem();
        List<String> vragenlijst = new ArrayList<>();
        if (graad.getSelectionModel().getSelectedItem().getGrade() != 2) {
            vragenlijst = vragen;
            if (vragenlijst.size() == 0) {
                throw new NullPointerException();
            }

            oefening.getSelectionModel().getSelectedItem().setQuestions(vragenlijst);
        }
        oefening.getSelectionModel().getSelectedItem().setClimateChart(c);
        oefening.getSelectionModel().getSelectedItem().setDetTable(d);
    }

    private void resetLists() {
        vraag.clear();
        List<String> lijst = new ArrayList<>();
        vragen = FXCollections.observableArrayList(lijst);
        alleVragen.setItems(vragen);
        klimatogram.setValue(climateCharts.get(0));
        determinatieTabel.setValue(detTable.get(0));
    }
}
