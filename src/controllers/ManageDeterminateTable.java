package controllers;

import domain.ClauseComponent;
import domain.DeterminateTable;
import domain.Grade;
import domain.Parameter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javax.persistence.NoResultException;
import repository.RepositoryController;
import util.MyNode;
import util.TreeIterator;

public class ManageDeterminateTable extends GridPane {

    @FXML
    private TreeView treeViewDeterminateTable;
    @FXML
    private Label lblActiveDeterminateTable;
    @FXML
    private ComboBox<String> parDropd;
    @FXML
    private ComboBox<String> operatorDropd;
    @FXML
    private ComboBox<String> gradeCombo;
    @FXML
    private ComboBox<String> parDropd2;
    @FXML
    private ComboBox<String> createGradeCombo;
    @FXML
    private ComboBox<String> createDeterminateTableCombo;
    @FXML
    private TextField waardeParameter;
    @FXML
    private Button saveItem;
    @FXML
    private TextField txtClimateFeature;
    @FXML
    private TextField txtVegetationFeature;
    @FXML
    private Text foutmelding;
    @FXML
    private Button btnDeleteDeterminateTable;
    @FXML
    private Button btnCreateDeterminateTable;
    @FXML
    private TextField txtNameNewDeterminateTable;
    @FXML
    private Button btnConnectDeterminateTable;
    @FXML
    private ToggleGroup par2OrValueRadioButtonGroup;
    @FXML
    private ToggleGroup ClauseOrResult;
    @FXML
    private RadioButton par2RadioButton;
    @FXML
    private RadioButton valueRadioButton;
    @FXML
    private RadioButton rbTypeClause;
    @FXML
    private RadioButton rbTypeResult;
    @FXML
    private Button btnDeleteItem;
    @FXML
    private ImageView imageVegetation;
    @FXML
    private Button btnSearchImage;
    @FXML
    private ScrollPane scrollPaneClause;

    private RepositoryController rc;
    private TreeItem<MyNode> root;
    private ObservableList<String> operatoren;
    private List<Parameter> paraLijst;
    private ClauseComponent selectedClauseComponent;
    private ObservableList<String> graden;
    private ObservableList<String> graden2;
    private ObservableList<String> comboListDeterminateTables;
    private FXMLLoader loader;
    private int currentDetTableId;
    private File vegetationPictureFile;

    //variabelen om te checken of er een clause is aangepast zonder op te slagen
    private String par1 = "";
    private String par2 = "";
    private String operator = "";
    private int waarde = 0;
    private String climateFeature = "";
    private String vegetationFeature = "";
    private Image vegetationPicture = null;
    private boolean change = false;
    private Boolean comingFromError = false;

    public ManageDeterminateTable(RepositoryController repositoryController) {
        rc = repositoryController;

        loader = new FXMLLoader(getClass().getResource("/gui/ManageDeterminateTable.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        operatoren = FXCollections.observableArrayList("=", ">", ">=", "<", "<=", "!=");
        paraLijst = rc.findAllParamaters();

        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ManageDeterminateTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        initialize();

        btnDeleteDeterminateTable.setDisable(true);
        disableAddClause(true);

        waardeParameter.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                char ar[] = t.getCharacter().toCharArray();
                char ch = ar[t.getCharacter().toCharArray().length - 1];
                int codeBackSpace = ch;
                if (!(ch >= '0' && ch <= '9') && codeBackSpace != 8) {
                    showError("Fout met ingegeven waarde.", "U kan alleen cijfers invullen.");
                    t.consume();
                }
            }
        });

        gradeCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                viewDeterminateTable();
            }

        }
        );

        if (gradeCombo.getSelectionModel().getSelectedItem() != null) {
            viewDeterminateTable();
        }
    }

    private void initialize() {
        List<String> discLijst = new ArrayList<>();
        List<String> discLijst2 = new ArrayList<>();
        List<Grade> gradeList = rc.getAllGrades();
        List<DeterminateTable> detTableList = rc.getAllDeterminateTables();
        List<String> detTableComboList = new ArrayList<>();

        gradeList.stream().forEach((g) -> {
            if (g.getDeterminateTableId() == null) {
                discLijst2.add("Graad " + g.getGrade());
            } else {
                discLijst.add("Graad " + g.getGrade());
            }
        });

        Collections.sort(discLijst);
        Collections.sort(discLijst2);

        detTableList.stream().forEach((d) -> {
            detTableComboList.add(d.getDeterminateTableId() + " " + d.getName());
        });

        graden = FXCollections.observableArrayList(discLijst);
        graden2 = FXCollections.observableArrayList(discLijst2);
        comboListDeterminateTables = FXCollections.observableArrayList(detTableComboList);

        if (graden.isEmpty()) {
            gradeCombo.setDisable(true);
            //btnViewDeterminateTable.setDisable(true);
        } else {
            gradeCombo.setItems(graden);
            gradeCombo.setValue(graden.get(0));
            gradeCombo.setDisable(false);
            //btnViewDeterminateTable.setDisable(false);
        }

        if (graden2.isEmpty()) {
            createGradeCombo.setDisable(true);

            btnConnectDeterminateTable.setDisable(true);
            txtNameNewDeterminateTable.setDisable(true);
            btnCreateDeterminateTable.setDisable(true);
        } else {
            createGradeCombo.setDisable(false);
            btnCreateDeterminateTable.setDisable(false);
            createDeterminateTableCombo.setDisable(false);
            txtNameNewDeterminateTable.setDisable(false);
            btnConnectDeterminateTable.setDisable(false);
            createGradeCombo.setItems(graden2);
            createGradeCombo.setValue(graden2.get(0));
        }

        if (!comboListDeterminateTables.isEmpty() && !graden2.isEmpty()) {
            createDeterminateTableCombo.setItems(comboListDeterminateTables);
            createDeterminateTableCombo.setValue(comboListDeterminateTables.get(0));
            createDeterminateTableCombo.setDisable(false);
        } else {
            createDeterminateTableCombo.setDisable(true);
        }
    }

    private void viewDeterminateTable() {
        operatorDropd.setItems(operatoren);
        List<String> discLijst = new ArrayList<>();
        paraLijst.stream().forEach(s -> discLijst.add(s.getDiscriminator()));
        discLijst.add(" ");
        parDropd2.setItems(FXCollections.observableArrayList(discLijst));
        parDropd.setItems(FXCollections.observableArrayList(discLijst));
        int graad = Integer.parseInt(gradeCombo.getSelectionModel().getSelectedItem().split(" ")[1]);
        List<ClauseComponent> clauses = rc.findClausesByDeterminateTableId(rc.findGradeById(graad).getDeterminateTableId());
        currentDetTableId = rc.findGradeById(graad).getDeterminateTableId().getDeterminateTableId();

        List<Integer> ids = new ArrayList<>();
        parDropd2.setDisable(true);
        parDropd.setDisable(true);
        operatorDropd.setDisable(true);
        waardeParameter.setDisable(true);
        txtVegetationFeature.setDisable(true);
        txtClimateFeature.setDisable(true);
        btnDeleteDeterminateTable.setDisable(false);

        lblActiveDeterminateTable.setText(String.format("U bekijkt momenteel determinatietabel %d, deze hoort bij graad %d.",
                rc.findGradeById(graad).getDeterminateTableId().getDeterminateTableId(), graad));

        clauses.stream().map((c) -> {
            if (c.getYesClause() != null) {
                ids.add(c.getYesClause().getClauseComponentId());
            }
            return c;
        }).filter((c) -> (c.getNoClause() != null)).forEach((c) -> {
            ids.add(c.getNoClause().getClauseComponentId());
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

        try {
            root.setExpanded(true);
            treeViewDeterminateTable.setRoot(root);
        } catch (NullPointerException ex) {
        }

        treeViewDeterminateTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<MyNode>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<MyNode>> observable, TreeItem<MyNode> oldValue, TreeItem<MyNode> newValue) {
                System.out.println("13ste");
                boolean check = false;
                boolean checkChange = false;

                //try {
                if (change) {
                    try {
                        selectedClauseComponent = rc.findClauseById(newValue.getValue().getId());
                    } catch (Exception ex) {
                        System.out.println("hier.");
                    }

                    if (selectedClauseComponent.getDiscriminator().equals("Clause")) {
                        if (!par1.equals(parDropd.getSelectionModel().getSelectedItem())) {
                            System.out.println("1");
                            checkChange = true;
                        }
                        else if (!par2.equals(parDropd2.getSelectionModel().getSelectedItem())) {
                                System.out.println("2");
                                checkChange = true;
                        }//selectedClausecomponent is fout, ge moet de waarden uit de fxml halen!!!!
                        else if (!operator.equals(operatorDropd.getSelectionModel().getSelectedItem())) {
                            System.out.println("3");
                            checkChange = true;
                        }
                        else if (waarde != Integer.parseInt(waardeParameter.getText())) {
                            System.out.println("4");
                            checkChange = true;
                        }
                    } else {
                        Image imageCheck = null;
                        try {
                            byte[] byteArray = selectedClauseComponent.getVegetationPicture();
                            imageCheck = new Image(new ByteArrayInputStream(byteArray));
                        } catch (Exception ex) {
                            System.out.println("kfkfk");
                        }
                        if (!climateFeature.equals(selectedClauseComponent.getClimatefeature())) {
                            checkChange = true;
                        } else if (!vegetationFeature.equals(selectedClauseComponent.getVegetationfeature())) {
                            checkChange = true;
                        } else if (vegetationPicture.equals(imageCheck)) {
                            checkChange = true;
                        }
                    }

                //} catch (NullPointerException ex) {
                    //    System.out.println("Fout bij listener.");
                    //}
                }

                if (checkChange) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Fout");
                    alert.setHeaderText("Er zijn aanpassingen die nog niet zijn opgeslagen.");
                    alert.setContentText("Wilt u deze alsnog opslagen?");
                    ButtonType buttonTypeOne = new ButtonType("Ja");
                    ButtonType buttonTypeTwo = new ButtonType("Nee");
                    ButtonType buttonTypeCancel = new ButtonType("Terug", ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeOne) {
                        selectedClauseComponent = rc.findClauseById(oldValue.getValue().getId());
                        comingFromError = true;
                        saveDetItem();
                        selectedClauseComponent = rc.findClauseById(newValue.getValue().getId());
                    } else if (result.get() == buttonTypeTwo) {

                    } else {
                        check = true;
                    }
                }

                if (!check) {
                    try {
                        scrollPaneClause.setVvalue(scrollPaneClause.getVmin());
                        imageVegetation.setImage(null);
                        foutmelding.setText("");
                        disableAddClause(false);
                        try {
                            selectedClauseComponent = rc.findClauseById(newValue.getValue().getId());
                            try {
                                byte[] byteArray = selectedClauseComponent.getVegetationPicture();
                                imageVegetation.setImage(new Image(new ByteArrayInputStream(byteArray)));
                            } catch (NullPointerException ex) {
                            }
                        } catch (NullPointerException ex) {
                            disableAddClause(true);
                        }

                        if (newValue.getValue().getType().equals("Clause")) {
                            par1 = rc.findParameterById(selectedClauseComponent.getPar1_ParameterId().getParameterId()).getDiscriminator().toString();

                            operator = selectedClauseComponent.getOperator().toString();
                            climateFeature = "";
                            vegetationFeature = "";
                            vegetationPicture = null;
                            rbTypeClause.setSelected(true);
                            txtClimateFeature.setDisable(true);
                            txtVegetationFeature.setDisable(true);
                            btnSearchImage.setDisable(true);
                            operatorDropd.setValue(selectedClauseComponent.getOperator().toString());
                            if (selectedClauseComponent.getPar2_ParameterId() != null) {
                                parDropd2.setValue(rc.findParameterById(selectedClauseComponent.getPar2_ParameterId().getParameterId()).getDiscriminator().toString());
                                waardeParameter.setDisable(true);
                                par2RadioButton.setSelected(true);
                                par2 = rc.findParameterById(selectedClauseComponent.getPar2_ParameterId().getParameterId()).getDiscriminator().toString();
                                waarde = 0;
                            } else {
                                valueRadioButton.setSelected(true);
                                parDropd2.setValue("");
                                parDropd2.setDisable(true);
                                waardeParameter.setDisable(false);
                                par2 = "";
                                waarde = selectedClauseComponent.getWaarde();
                            }
                            parDropd.setValue(rc.findParameterById(selectedClauseComponent.getPar1_ParameterId().getParameterId()).getDiscriminator().toString());
                            waardeParameter.setText(selectedClauseComponent.getWaarde() + "");
                            txtClimateFeature.setText(selectedClauseComponent.getName());

                            RadioButton rb = (RadioButton) par2OrValueRadioButtonGroup.getSelectedToggle();

                            if (rb.getText().equals("Waarde")) {
                                unblockValue();
                            } else {
                                unblockPar2();
                            }

                        } else {
                            rbTypeResult.setSelected(true);
                            txtClimateFeature.setDisable(false);
                            parDropd2.setDisable(true);
                            par2RadioButton.setDisable(true);
                            valueRadioButton.setDisable(true);
                            waardeParameter.setDisable(true);
                            txtVegetationFeature.setText(selectedClauseComponent.getVegetationfeature());
                            txtClimateFeature.setText(selectedClauseComponent.getClimatefeature());
                            parDropd.setDisable(true);
                            operatorDropd.setDisable(true);
                            par1 = null;
                            par2 = null;
                            operator = null;
                            waarde = 0;
                            vegetationFeature = selectedClauseComponent.getVegetationfeature();
                            climateFeature = selectedClauseComponent.getClimatefeature();
                            try {
                                byte[] byteArray = selectedClauseComponent.getVegetationPicture();
                                vegetationPicture = new Image(new ByteArrayInputStream(byteArray));
                            } catch (NullPointerException ex) {
                            }
                        }
                        change = true;
                    } catch (Exception e) {
                    }
                }
            }
        }
        );

        if (clauses.isEmpty()) {
            initialize();
            treeViewDeterminateTable.setRoot(null);
        }
    }

    public void recursiveClause(TreeItem<MyNode> node, ClauseComponent parentClause, Boolean typeClause) {
        try {
            ClauseComponent clause = null;
            String type;
            if (typeClause) {
                try {
                    clause = rc.findClauseById(parentClause.getYesClause().getClauseComponentId());
                } catch (NoResultException ex) {

                }
                type = "(Ja Knoop)";
            } else {
                try {
                    clause = rc.findClauseById(parentClause.getNoClause().getClauseComponentId());
                } catch (NoResultException ex) {
                }
                type = "(Nee Knoop)";
            }
            if (clause != null) {
                if (clause.getName() != null) {
                    TreeItem<MyNode> newNode = new TreeItem<>(new MyNode(clause.getName() + " " + type, "Clause", clause.getClauseComponentId()));
                    recursiveClause(newNode, clause, true);
                    recursiveClause(newNode, clause, false);
                    node.getChildren().add(newNode);
                } else {
                    TreeItem<MyNode> newNode = new TreeItem<>(new MyNode(clause.getClimatefeature() + " " + type, "Result", clause.getClauseComponentId()));
                    node.getChildren().add(newNode);
                }
            }
        } catch (NullPointerException ex) {

        }
        if (node != null) {
            node.setExpanded(true);
        }

    }

    @FXML
    private void saveDetItem() {
        System.out.println("1ste");
        try {
            if (selectedClauseComponent != null) {

                RadioButton rbClauseOrResult = (RadioButton) ClauseOrResult.getSelectedToggle();
                RadioButton rbValueOrPar2 = (RadioButton) par2OrValueRadioButtonGroup.getSelectedToggle();
                
                System.out.println("2ste");
                
                if (selectedClauseComponent.getDiscriminator().equals("Clause")) {
                    if (rbClauseOrResult.getText().equals("Knoop")) {
                        if (operatorDropd.getSelectionModel().getSelectedItem() != null) {
                            selectedClauseComponent.setOperator(operatorDropd.getSelectionModel().getSelectedItem());
                        }
                        if (parDropd.getSelectionModel().getSelectedItem() != null) {
                            selectedClauseComponent.setPar1_ParameterId(rc.findParameterByName(parDropd.getSelectionModel().getSelectedItem()));
                        }
                        System.out.println("3ste");
                        if (rbValueOrPar2.getText().equals("Waarde")) {
                            selectedClauseComponent.setWaarde(Integer.parseInt(waardeParameter.getText()));
                            selectedClauseComponent.setPar2_ParameterId(null);
                            selectedClauseComponent.setName(String.format("%s %s %s", parDropd.getSelectionModel().getSelectedItem().toString(), operatorDropd.getSelectionModel().getSelectedItem().toString(), waardeParameter.getText()));
                        } else {
                            selectedClauseComponent.setPar2_ParameterId(rc.findParameterByName(parDropd2.getSelectionModel().getSelectedItem()));
                            selectedClauseComponent.setWaarde(0);
                            selectedClauseComponent.setName(String.format("%s %s %s", parDropd.getSelectionModel().getSelectedItem().toString(), operatorDropd.getSelectionModel().getSelectedItem().toString(), parDropd2.getSelectionModel().getSelectedItem().toString()));
                        }

                        if (selectedClauseComponent.getYesClause() == null) {
                            ClauseComponent newClause = new ClauseComponent("Klimaatkenmerk", "Vegetatiekenmerk", "Result", rc.findDeterminateTableById(currentDetTableId));
                            rc.insertClause(newClause);
                            selectedClauseComponent.setYesClause(newClause);
                        }
                        if (selectedClauseComponent.getNoClause() == null) {
                            ClauseComponent newClause = new ClauseComponent("Klimaatkenmerk", "Vegetatiekenmerk", "Result", rc.findDeterminateTableById(currentDetTableId));
                            rc.insertClause(newClause);
                            selectedClauseComponent.setNoClause(newClause);
                        }
                        System.out.println("4ste");
                        rc.updateRepo();
                        System.out.println("5ste");
                    } else {
                        ClauseComponent yesClause = selectedClauseComponent.getYesClause();
                        ClauseComponent noClause = selectedClauseComponent.getNoClause();
                        selectedClauseComponent.setName(null);
                        selectedClauseComponent.setDiscriminator("Result");
                        selectedClauseComponent.setNoClause(null);
                        selectedClauseComponent.setOperator(null);
                        selectedClauseComponent.setPar1_ParameterId(null);
                        selectedClauseComponent.setPar2_ParameterId(null);
                        selectedClauseComponent.setWaarde(0);
                        selectedClauseComponent.setYesClause(null);
                        selectedClauseComponent.setClimatefeature(txtClimateFeature.getText());
                        selectedClauseComponent.setVegetationfeature(txtVegetationFeature.getText());
                        rc.removeClauseComponent(yesClause);
                        rc.removeClauseComponent(noClause);

                        byte[] bFile;
                        try (FileInputStream fis = new FileInputStream(vegetationPictureFile)) {
                            bFile = new byte[(int) vegetationPictureFile.length()];
                            fis.read(bFile);
                        }
                        selectedClauseComponent.setVegetationPicture(bFile);
                    }
                } else {
                    if (rbClauseOrResult.getText().equals("Resultaat")) {
                        selectedClauseComponent.setClimatefeature(txtClimateFeature.getText());
                        selectedClauseComponent.setVegetationfeature(txtVegetationFeature.getText());

                        byte[] bFile;
                        try (FileInputStream fis = new FileInputStream(vegetationPictureFile)) {
                            bFile = new byte[(int) vegetationPictureFile.length()];
                            fis.read(bFile);
                        }
                        selectedClauseComponent.setVegetationPicture(bFile);
                    } else {
                        selectedClauseComponent.setClimatefeature(null);
                        selectedClauseComponent.setVegetationfeature(null);
                        selectedClauseComponent.setDiscriminator("Clause");
                        if (operatorDropd.getSelectionModel().getSelectedItem() != null) {
                            selectedClauseComponent.setOperator(operatorDropd.getSelectionModel().getSelectedItem());
                        }
                        if (parDropd.getSelectionModel().getSelectedItem() != null) {
                            selectedClauseComponent.setPar1_ParameterId(rc.findParameterByName(parDropd.getSelectionModel().getSelectedItem()));
                        }
                        if (rbValueOrPar2.getText().equals("Waarde")) {
                            selectedClauseComponent.setWaarde(Integer.parseInt(waardeParameter.getText()));
                            selectedClauseComponent.setPar2_ParameterId(null);
                            selectedClauseComponent.setName(String.format("%s %s %s", parDropd.getSelectionModel().getSelectedItem().toString(), operatorDropd.getSelectionModel().getSelectedItem().toString(), waardeParameter.getText()));
                        } else {
                            selectedClauseComponent.setPar2_ParameterId(rc.findParameterByName(parDropd2.getSelectionModel().getSelectedItem()));
                            selectedClauseComponent.setWaarde(0);
                            selectedClauseComponent.setName(String.format("%s %s %s", parDropd.getSelectionModel().getSelectedItem().toString(), operatorDropd.getSelectionModel().getSelectedItem().toString(), parDropd2.getSelectionModel().getSelectedItem().toString()));
                        }
                        if (selectedClauseComponent.getYesClause() == null) {
                            ClauseComponent newClause = new ClauseComponent("Klimaatkenmerk", "Vegetatiekenmerk", "Result", rc.findDeterminateTableById(currentDetTableId));
                            rc.insertClause(newClause);
                            selectedClauseComponent.setYesClause(newClause);
                        }
                        if (selectedClauseComponent.getNoClause() == null) {
                            ClauseComponent newClause = new ClauseComponent("Klimaatkenmerk", "Vegetatiekenmerk", "Result", rc.findDeterminateTableById(currentDetTableId));
                            rc.insertClause(newClause);
                            selectedClauseComponent.setNoClause(newClause);
                        }
                        rc.updateRepo();
                    }
                }
                System.out.println("6ste");
                TreeIterator<MyNode> iterator = new TreeIterator<>(root);
                System.out.println("7ste");
                while (iterator.hasNext()) {
                    MyNode node = iterator.next().getValue();
                    if (node.getId() == selectedClauseComponent.getClauseComponentId()) {
                        node.setValue(txtClimateFeature.getText());
                        iterator.expand();
                        break;
                    }
                }
                System.out.println("8ste");
                rc.updateRepo();
                foutmelding.setText("Opslaan succesvol");
                System.out.println("9ste");
            }
        } catch (Exception e) {
            foutmelding.setText("Er is een onbekende fout opgetreden");
        }
        System.out.println("10ste");
        if(comingFromError){
            System.out.println("11ste");
            viewDeterminateTable();
            System.out.println("12ste");
            comingFromError = false;
        }
            
    }

    @FXML
    private void deleteDeterminateTable() {
        int graad = Integer.parseInt(gradeCombo.getSelectionModel().getSelectedItem().split(" ")[1]);
        Grade g = rc.findGradeById(graad);
        int id = g.getDeterminateTableId().getDeterminateTableId();
        g.setDeterminateTableId(null);
        rc.updateRepo();
        lblActiveDeterminateTable.setText("De determineertabel is succesvol ontkoppeld van de geselecteerde graad.");
        treeViewDeterminateTable.setRoot(null);
        initialize();
        disableAddClause(true);
    }

    @FXML
    private void createDeterminateTable() {
        int graad = Integer.parseInt(createGradeCombo.getSelectionModel().getSelectedItem().split(" ")[1]);
        String name;
        if (txtNameNewDeterminateTable.getText().length() == 0) {
            name = "Nieuwe determineertabel";
        } else {
            name = txtNameNewDeterminateTable.getText();
        }

        rc.createDeterminateTable(graad, name);
        rc.updateRepo();
        rc.insertClause(new ClauseComponent("Nieuwe clause", "Clause", 0, "<", rc.findAllParamaters().get(0), rc.findGradeById(graad).getDeterminateTableId()));
        initialize();
        disableAddClause(true);
        txtNameNewDeterminateTable.setText("");
    }

    @FXML
    private void connectDeterminateTableToGrade() {
        int detTableId = Integer.parseInt(createDeterminateTableCombo.getSelectionModel().getSelectedItem().split(" ")[0]);
        int graad = Integer.parseInt(createGradeCombo.getSelectionModel().getSelectedItem().split(" ")[1]);
        Grade g = rc.findGradeById(graad);
        DeterminateTable d = rc.findDeterminateTableById(detTableId);
        g.setDeterminateTableId(d);
        rc.updateRepo();
        lblActiveDeterminateTable.setText("Succesvol gekoppeld.");
        initialize();
        disableAddClause(true);
    }

    private void disableAddClause(boolean bool) {
        rbTypeClause.setDisable(bool);
        rbTypeResult.setDisable(bool);
        parDropd.setDisable(bool);
        operatorDropd.setDisable(bool);
        parDropd2.setDisable(bool);
        par2RadioButton.setDisable(bool);
        valueRadioButton.setDisable(bool);
        waardeParameter.setDisable(bool);
        txtClimateFeature.setDisable(bool);
        txtVegetationFeature.setDisable(bool);
        btnSearchImage.setDisable(bool);
        saveItem.setDisable(bool);
        btnDeleteItem.setDisable(bool);
    }

    private void showError(String headerText, String contextText) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Fout");
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    @FXML
    private void unblockValue() {
        waardeParameter.setDisable(false);
        parDropd2.setDisable(true);
    }

    @FXML
    private void unblockPar2() {
        waardeParameter.setDisable(true);
        parDropd2.setDisable(false);
    }

    @FXML
    private void switchToResult() {
        parDropd.setDisable(true);
        operatorDropd.setDisable(true);
        parDropd2.setDisable(true);
        par2RadioButton.setDisable(true);
        valueRadioButton.setDisable(true);
        waardeParameter.setDisable(true);
        txtClimateFeature.setDisable(false);
        txtVegetationFeature.setDisable(false);
        btnSearchImage.setDisable(false);
        imageVegetation.setDisable(false);
    }

    @FXML
    private void switchToClause() {
        parDropd.setDisable(false);
        operatorDropd.setDisable(false);
        parDropd2.setDisable(false);
        par2RadioButton.setDisable(false);
        valueRadioButton.setDisable(false);
        waardeParameter.setDisable(false);
        txtClimateFeature.setDisable(true);
        txtVegetationFeature.setDisable(true);
        btnSearchImage.setDisable(true);
        imageVegetation.setDisable(true);

        RadioButton rb = (RadioButton) par2OrValueRadioButtonGroup.getSelectedToggle();

        if (rb.getText().equals("Waarde")) {
            unblockValue();
        } else {
            unblockPar2();
        }

    }

    @FXML
    private void deleteItem() {
        recursiveDelete(selectedClauseComponent);
        viewDeterminateTable();
    }

    @FXML
    private void recursiveDelete(ClauseComponent selectedClauseComponent) {
        if (selectedClauseComponent.getYesClause() != null) {
            ClauseComponent toDelete = selectedClauseComponent.getYesClause();
            selectedClauseComponent.setYesClause(null);
            recursiveDelete(toDelete);
        }
        if (selectedClauseComponent.getNoClause() != null) {
            ClauseComponent toDelete = selectedClauseComponent.getNoClause();
            selectedClauseComponent.setNoClause(null);
            recursiveDelete(toDelete);
        }

        selectedClauseComponent.setClimatefeature("Klimaatkenmerk");
        selectedClauseComponent.setDiscriminator("Result");
        selectedClauseComponent.setName(null);
        selectedClauseComponent.setNoClause(null);
        selectedClauseComponent.setYesClause(null);
        selectedClauseComponent.setOperator(null);
        selectedClauseComponent.setPar1_ParameterId(null);
        selectedClauseComponent.setPar2_ParameterId(null);
        selectedClauseComponent.setVegetationfeature("Vegetatiekenmerk");
        selectedClauseComponent.setWaarde(0);
        rc.updateRepo();
    }

    @FXML
    private void searchImage() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("Images (.png, .jpg, .bmp)", "*.jpg", "*.png", "*.bmp");
        fileChooser.getExtensionFilters().add(extentionFilter);

        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        if (!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        fileChooser.setInitialDirectory(userDirectory);

        File chosenFile = fileChooser.showOpenDialog(null);

        String path;
        if (chosenFile != null) {
            path = chosenFile.getPath();
            File file = new File(path);
            vegetationPictureFile = file;

            Image image = new Image(file.toURI().toString());
            imageVegetation.setImage(image);

        } else {
            path = null;
        }
    }
}
