package controllers;

import domain.ClassGroup;
import domain.ClimateChart;
import domain.DeterminateTable;
import domain.Exercise;
import domain.Test;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import repository.RepositoryController;

public class TestControllerPanel extends GridPane {

    //    @FXML
//    private TextField Titel;
//    @FXML
//    private TextArea Beschrijving;
//    @FXML
//    private DatePicker begindatum;
//    @FXML
//    private DatePicker einddatum;
//    @FXML
//    private ComboBox<Grade> graad;
//    @FXML
//    private ComboBox<Exercise> oefening;
//    @FXML
//    private TextField vraag;
//    @FXML
//    private ComboBox<String> alleVragen;
//    @FXML
//    private Button voegVraagToe;
//    @FXML
//    private Button verwijderVraag;
//    @FXML
//    private ComboBox<ClimateChart> klimatogram;
//    @FXML
//    private ComboBox<DeterminateTable> determinatieTabel;
//    @FXML
//    private Button newOef;
//    @FXML
//    private Button voegTestToe;
//    @FXML
//    private Text errorText;
    //    private ObservableList<ClimateChart> climateCharts;
//    private ObservableList<DeterminateTable> detTable;
//    private ObservableList<Grade> grades;
//    private ObservableList<Exercise> exercises;
//    private ObservableList<String> vragen;
//    private int oefeningenCounter = 1;
    @FXML
    private ComboBox comboTestClassGroup;
    @FXML
    private TextField txtTestTitle;
    @FXML
    private TextArea txtAreaDescription;
    @FXML
    private DatePicker dpTestBegin;
    @FXML
    private DatePicker dpTestEnd;
    @FXML
    private ComboBox comboClassGroupExercises;
    @FXML
    private Button btnDeleteSelectedExercise;
    @FXML
    private TextField txtExerciseName;
    @FXML
    private TextField txtExerciseQuotation;
    @FXML
    private Button btnAddExercise;
    @FXML
    private ComboBox comboTestClimateChart;
    @FXML
    private ComboBox comboTestDeterminateTable;
    @FXML
    private Button btnSaveTest;
    @FXML
    private Label txtInfo;
    @FXML
    private ComboBox comboChooseTest;
    @FXML
    private Button btnViewTest;
    @FXML
    private Button btnDeleteTest;
    @FXML
    private Button btnEditExercise;
    @FXML
    private Button btnEditTest;

    private RepositoryController rc;
    private FXMLLoader loader;

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
        initialize();
    }

    public void initialize() {

        txtInfo.setText("");
        disableForm(true);

        ObservableList<ClassGroup> observableListClassGroups = FXCollections.observableArrayList(rc.getAllClassGroups());
        comboTestClassGroup.setItems(observableListClassGroups);

        comboTestClassGroup.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ClassGroup>() {
            @Override
            public void changed(ObservableValue<? extends ClassGroup> observable, ClassGroup oldValue, ClassGroup newValue) {
                txtInfo.setText("");
                disableForm(false);
                ClassGroup cg = (ClassGroup) comboTestClassGroup.getSelectionModel().selectedItemProperty().getValue();
                ObservableList<Test> observableListTests = FXCollections.observableArrayList(rc.findTestsByClassGroup(cg));
                if (!observableListTests.isEmpty()) {
                    comboChooseTest.setItems(observableListTests);
                    comboChooseTest.setValue(observableListTests.get(0));
                    txtTestTitle.setText(null);
                    txtAreaDescription.setText(null);
                    dpTestBegin.setValue(null);
                    dpTestEnd.setValue(null);
                } else {
                    txtInfo.setText("Er zijn nog geen testen voor deze klasgroep.");
                    comboChooseTest.setDisable(true);
                    btnViewTest.setDisable(true);
                    btnDeleteTest.setDisable(true);
                }
                btnEditTest.setDisable(true);
                btnEditExercise.setDisable(true);
                btnAddExercise.setDisable(true);
                btnDeleteSelectedExercise.setDisable(true);
                comboClassGroupExercises.setDisable(true);
                txtExerciseName.setDisable(true);
                txtExerciseQuotation.setDisable(true);
                comboTestClimateChart.setDisable(true);
                comboTestDeterminateTable.setDisable(true);
            }
        }
        );

        comboClassGroupExercises.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Exercise>() {
            @Override
            public void changed(ObservableValue<? extends Exercise> observable, Exercise oldValue, Exercise newValue) {
                txtInfo.setText("");
                try {
                    Exercise e = (Exercise) comboClassGroupExercises.getSelectionModel().selectedItemProperty().getValue();
                    txtExerciseName.setText(e.getNaam());
                    txtExerciseQuotation.setText(e.getPunten().toString());
                    comboTestClimateChart.setValue(e.getClimateChart());
                    comboTestDeterminateTable.setValue(e.getDetTable());
                } catch (NullPointerException e) {

                }
            }
        }
        );

        comboChooseTest.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Test>() {
            @Override
            public void changed(ObservableValue<? extends Test> observable, Test oldValue, Test newValue) {
                try {
                    viewTest();
                } catch (Exception ex) {

                }
            }
        }
        );

        ObservableList<ClimateChart> observableListClimateCharts = FXCollections.observableArrayList(rc.findAllClimateCharts());
        ObservableList<DeterminateTable> observableListDeterminateTables = FXCollections.observableArrayList(rc.getAllDeterminateTables());

        comboTestClimateChart.setItems(observableListClimateCharts);
        comboTestDeterminateTable.setItems(observableListDeterminateTables);
        if (!observableListClimateCharts.isEmpty()) {
            comboTestClimateChart.setValue(observableListClimateCharts.get(0));
        }
        if (!observableListDeterminateTables.isEmpty()) {
            comboTestDeterminateTable.setValue(observableListDeterminateTables.get(0));
        }
    }

    @FXML
    private void deleteSelectedExercise() {
        Exercise e = (Exercise) comboClassGroupExercises.getSelectionModel().getSelectedItem();
        rc.removeExercise(e);
        viewTest();
        if (rc.findExercisesByTest(e.getTest()).isEmpty()) {
            comboClassGroupExercises.setValue(null);
            txtExerciseName.setText("");
            txtExerciseQuotation.setText("");
            comboTestClimateChart.setValue("");
            comboTestDeterminateTable.setValue("");
            btnEditExercise.setDisable(true);
            comboClassGroupExercises.setDisable(true);
            btnDeleteSelectedExercise.setDisable(true);
        }
    }

    @FXML
    private void addExercise() {
        try {
            Exercise e = new Exercise(txtExerciseName.getText(), Double.parseDouble(txtExerciseQuotation.getText()),
                    (ClimateChart) comboTestClimateChart.getSelectionModel().getSelectedItem(),
                    (DeterminateTable) comboTestDeterminateTable.getSelectionModel().getSelectedItem(),
                    (Test) comboChooseTest.getSelectionModel().getSelectedItem());
            rc.insertExercise(e);
            txtInfo.setText("De vraag is succesvol opgeslagen");
            txtExerciseName.setText("");
            txtExerciseQuotation.setText("");
            comboTestClimateChart.setValue("");
            comboTestDeterminateTable.setValue("");
            viewTest();
            txtInfo.setText("De vraag is succesvol opgeslagen.");
        } catch (Exception ex) {
            txtInfo.setText("U moet alles correct invullen.");
        }
    }

    @FXML
    private void saveTest() {
        try {
            LocalDate localDate = dpTestBegin.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date dateBegin = Date.from(instant);
            LocalDate localDate2 = dpTestEnd.getValue();
            Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
            Date dateEnd = Date.from(instant2);
            Test t = new Test(txtTestTitle.getText(), txtAreaDescription.getText(), dateBegin, dateEnd, (ClassGroup) comboTestClassGroup.getSelectionModel().getSelectedItem());
            rc.insertTest(t);
            initialize();
            txtInfo.setText(String.format("De test '%s' is succesvol opgeslagen, u kan hiervoor nu vragen toevoegen.", txtTestTitle.getText()));
            txtTestTitle.setText(null);
            txtAreaDescription.setText(null);
            dpTestBegin.setValue(null);
            dpTestEnd.setValue(null);
        } catch (Exception ex) {
            txtInfo.setText("U moet alles correct invullen.");
        }
    }

    @FXML
    private void viewTest() {
        Test t = (Test) comboChooseTest.getSelectionModel().getSelectedItem();
        txtTestTitle.setText(t.getTitle());
        txtAreaDescription.setText(t.getDescription());
        Date date = t.getStartDate();
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        dpTestBegin.setValue(localDate);
        date = t.getEndDate();
        instant = date.toInstant();
        localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        dpTestEnd.setValue(localDate);

        ObservableList<Exercise> observableListExercises = FXCollections.observableArrayList(rc.findExercisesByTest(t));
        if (!observableListExercises.isEmpty()) {
            btnEditExercise.setDisable(false);
            btnDeleteSelectedExercise.setDisable(false);
            comboClassGroupExercises.setDisable(false);
            comboClassGroupExercises.setItems(observableListExercises);
            comboClassGroupExercises.setValue(observableListExercises.get(0));
        } else {
            comboClassGroupExercises.setValue(null);
            txtExerciseName.setText("");
            txtExerciseQuotation.setText("");
            comboTestClimateChart.setValue("");
            comboTestDeterminateTable.setValue("");
            btnEditExercise.setDisable(true);
            txtInfo.setText("Er zijn nog geen vragen voor deze toets.");
        }

        txtExerciseName.setDisable(false);
        txtExerciseQuotation.setDisable(false);
        comboTestClimateChart.setDisable(false);
        comboTestDeterminateTable.setDisable(false);
        btnAddExercise.setDisable(false);
        btnEditTest.setDisable(false);
    }

    @FXML
    private void deleteTest() {
        Test t = (Test) comboChooseTest.getSelectionModel().getSelectedItem();
        rc.findExercisesByTest(t).stream().forEach((e) -> {
            rc.removeExercise(e);
        });
        rc.removeTest(t);
        disableForm(false);
        ClassGroup cg = (ClassGroup) comboTestClassGroup.getSelectionModel().selectedItemProperty().getValue();
        ObservableList<Test> observableListTests = FXCollections.observableArrayList(rc.findTestsByClassGroup(cg));
        if (!observableListTests.isEmpty()) {
            comboChooseTest.setItems(observableListTests);
            comboChooseTest.setValue(observableListTests.get(0));
        } else {
            txtInfo.setText("Er zijn nog geen testen voor deze klasgroep.");
            comboChooseTest.setDisable(true);
            btnViewTest.setDisable(true);
            btnDeleteTest.setDisable(true);
        }
        btnEditTest.setDisable(true);
        btnEditExercise.setDisable(true);
        btnAddExercise.setDisable(true);
        btnDeleteSelectedExercise.setDisable(true);
        comboClassGroupExercises.setDisable(true);
        txtExerciseName.setDisable(true);
        txtExerciseQuotation.setDisable(true);
        comboTestClimateChart.setDisable(true);
        comboTestDeterminateTable.setDisable(true);
        txtInfo.setText("Test succesvol verwijderd.");
        txtTestTitle.setText(null);
        txtAreaDescription.setText(null);
        dpTestBegin.setValue(null);
        dpTestEnd.setValue(null);
        txtExerciseName.setText(null);
        txtExerciseQuotation.setText(null);
        comboTestClimateChart.setValue(null);
        comboTestDeterminateTable.setValue(null);
    }

    @FXML
    private void editExercise() {
        try {
            Exercise e = (Exercise) comboClassGroupExercises.getSelectionModel().getSelectedItem();
            e.setNaam(txtExerciseName.getText());
            e.setPunten(Double.parseDouble(txtExerciseQuotation.getText()));
            e.setClimateChart((ClimateChart) comboTestClimateChart.getSelectionModel().getSelectedItem());
            e.setDetTable((DeterminateTable) comboTestDeterminateTable.getSelectionModel().getSelectedItem());
            e.setTest((Test) comboChooseTest.getSelectionModel().getSelectedItem());
            rc.updateRepo();
            //comboClassGroupExercises.setValue(e);
        } catch (Exception ex) {
            txtInfo.setText("U moet alles correct invullen.");
        }
    }

    @FXML
    private void editTest() {
        try {
            Test t = (Test) comboChooseTest.getSelectionModel().getSelectedItem();
            t.setTitle(txtTestTitle.getText());
            t.setDescription(txtAreaDescription.getText());
            LocalDate localDate = dpTestBegin.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date dateBegin = Date.from(instant);
            t.setStartDate(dateBegin);
            localDate = dpTestEnd.getValue();
            instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date dateEnd = Date.from(instant);
            t.setEndDate(dateEnd);
            comboChooseTest.setValue(t.getTitle());
            rc.updateRepo();
        } catch (Exception ex) {
            txtInfo.setText("U moet alles correct invullen.");
        }
    }

    private void disableForm(Boolean bool) {
        txtTestTitle.setDisable(bool);
        txtAreaDescription.setDisable(bool);
        dpTestBegin.setDisable(bool);
        dpTestEnd.setDisable(bool);
        comboClassGroupExercises.setDisable(bool);
        btnDeleteSelectedExercise.setDisable(bool);
        txtExerciseName.setDisable(bool);
        txtExerciseQuotation.setDisable(bool);
        btnAddExercise.setDisable(bool);
        comboTestClimateChart.setDisable(bool);
        comboTestDeterminateTable.setDisable(bool);
        btnSaveTest.setDisable(bool);
        btnEditTest.setDisable(bool);
        comboChooseTest.setDisable(bool);
        btnViewTest.setDisable(bool);
        btnDeleteTest.setDisable(bool);
        btnEditExercise.setDisable(bool);
    }
}
