package gui;

import domain.ClauseComponent;
import domain.DeterminateTable;
import domain.DomeinController;
import domain.Grade;
import domain.Parameter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import repository.RepositoryController;
import util.MyNode;
import util.TextFieldTreeCellImpl;
import util.TreeIterator;

public class ManageDeterminateTable extends GridPane {

    @FXML
    private TreeView treeViewDeterminateTable;
    @FXML
    private Button btnViewDeterminateTable;
    @FXML
    private Label lblActiveDeterminateTable;
    @FXML
    private ComboBox<String> parDropd;
    @FXML
    private ComboBox<String> operatorDropd;
    @FXML
    private ComboBox<String> gradeCombo;
    @FXML
    private ComboBox<String> createGradeCombo;
    @FXML
    private ComboBox<String> createDeterminateTableCombo;
    @FXML
    private TextField waardeParameter;
    @FXML
    private Button saveItem;
    @FXML
    private TextField beschrijving;
    @FXML
    private TextField vegetatie;
    @FXML
    private Text foutmelding;
    @FXML
    private Button btnDeleteDeterminateTable;
    @FXML
    private Button btnCreateDeterminateTable;
    @FXML
    private TextField txtNameNewDeterminateTable;
    @FXML
    private RadioButton jaKnoop;
    @FXML
    private RadioButton neeKnoop;
    @FXML
    private Button btnAddClause;
    @FXML
    private ComboBox<ClauseComponent> comboChooseParent;
    private ObservableList<TreeItem<MyNode>> obsTreeItems;
    private List<TreeItem<MyNode>> treeItems;
    private DomeinController dc;
    private RepositoryController rc;
    private TreeItem<MyNode> root;
    private ObservableList<String> operatoren;
    private List<Parameter> paraLijst;
    private ClauseComponent selectedClauseComponent;
    private ObservableList<String> graden;
    private ObservableList<String> graden2;
    private ObservableList<String> comboListDeterminateTables;
    private ObservableList<ClauseComponent> comboClauseComponentsParents;
    private FXMLLoader loader;
    @FXML
    private ToggleGroup typeRadioButtonGroup;



    public ManageDeterminateTable() throws IOException {
        dc = new DomeinController();
        rc = new RepositoryController();
        treeItems = new ArrayList<>();
        
        loader = new FXMLLoader(getClass().getResource("ManageDeterminateTable.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        operatoren = FXCollections.observableArrayList("=", ">", ">=", "<", "<=", "!=");
        paraLijst = rc.findAllParamaters();
        
        
        loader.load();
        initialize();
        
        btnDeleteDeterminateTable.setDisable(true);

    }

    private void initialize() {
        List<String> discLijst = new ArrayList<>();
        List<String> discLijst2 = new ArrayList<>();
        List<Grade> gradeList = rc.getAllGrades();
        List<DeterminateTable> detTableList = rc.getAllDeterminateTables();
        List<String> detTableComboList = new ArrayList<>();

        for (Grade g : gradeList) {
            if (g.getDeterminateTableId() == null) {
                discLijst2.add("Graad " + g.getGrade());
            } else {
                discLijst.add("Graad " + g.getGrade());
            }
        }
        
        for(DeterminateTable d : detTableList){
            detTableComboList.add(d.getDeterminateTableId() + " " + d.getName());
        }

        
        
        graden = FXCollections.observableArrayList(discLijst);
        graden2 = FXCollections.observableArrayList(discLijst2);
        comboListDeterminateTables = FXCollections.observableArrayList(detTableComboList);
        

        if(graden.isEmpty())
        {
            gradeCombo.setDisable(true);
            btnViewDeterminateTable.setDisable(true);
        }
        else{
            gradeCombo.setItems(graden);
            gradeCombo.setValue(graden.get(0));
            gradeCombo.setDisable(false);
            btnViewDeterminateTable.setDisable(false);
        }
            
        if (graden2.isEmpty()) {
            createGradeCombo.setDisable(true);
            btnCreateDeterminateTable.setDisable(true);
        } else {
            createGradeCombo.setDisable(false);
            btnCreateDeterminateTable.setDisable(false);
            createGradeCombo.setItems(graden2);
            createGradeCombo.setValue(graden2.get(0));
        }
        
        if(!comboListDeterminateTables.isEmpty()){
            createDeterminateTableCombo.setItems(comboListDeterminateTables);
            createDeterminateTableCombo.setValue(comboListDeterminateTables.get(0));
            createDeterminateTableCombo.setDisable(false);
        }
        else{
            createDeterminateTableCombo.setDisable(true);
        }
        
        List<ClauseComponent> clauseComponentsList = rc.findClausesByDeterminateTableId(rc.findGradeById(Integer.parseInt(gradeCombo.getSelectionModel().getSelectedItem().split(" ")[1])).getDeterminateTableId().getDeterminateTableId());
        comboClauseComponentsParents = FXCollections.observableArrayList(clauseComponentsList);
        comboChooseParent.setItems(comboClauseComponentsParents);
        
        //Grade gr = rc.findGradeById(Integer.parseInt(gradeCombo.getSelectionModel().getSelectedItem().split(" ")[1]));
        
        
    }

    @FXML
    public void viewDeterminateTable() {
        operatorDropd.setItems(operatoren);
        List<String> discLijst = new ArrayList<>();
        paraLijst.stream().forEach(s -> discLijst.add(s.getDiscriminator()));
        parDropd.setItems(FXCollections.observableArrayList(discLijst));
        int graad = Integer.parseInt(gradeCombo.getSelectionModel().getSelectedItem().split(" ")[1]);
        List<ClauseComponent> clauses = rc.findClausesByDeterminateTableId(rc.findGradeById(graad).getDeterminateTableId().getDeterminateTableId());
        List<Integer> ids = new ArrayList<>();
        parDropd.setDisable(true);
        operatorDropd.setDisable(true);
        waardeParameter.setDisable(true);
        vegetatie.setDisable(true);
        beschrijving.setDisable(true);
        btnDeleteDeterminateTable.setDisable(false);

        lblActiveDeterminateTable.setText(String.format("U bekijkt momenteel determinatietabel %d, deze hoort bij graad %d.",
                rc.findGradeById(graad).getDeterminateTableId().getDeterminateTableId(), graad));

        clauses.stream().map((c) -> {
            ids.add(c.getYesClause());
            return c;
        }).forEach((c) -> {
            ids.add(c.getNoClause());
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

        obsTreeItems = FXCollections.observableArrayList(treeItems);
        try {
            root.getChildren().addAll(obsTreeItems);
            root.setExpanded(true);
            treeViewDeterminateTable.setRoot(root);
            treeViewDeterminateTable.setEditable(true);
            treeViewDeterminateTable.setCellFactory(new Callback<TreeView<MyNode>, TreeCell<MyNode>>() {
                @Override
                public TreeCell<MyNode> call(TreeView<MyNode> p) {
                    try {
                        return new TextFieldTreeCellImpl(root, treeItems, rc);
                    } catch (SQLException ex) {
                        Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return null;
                }

            });
        } catch (NullPointerException ex) {

        }

        treeViewDeterminateTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<MyNode>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<MyNode>> observable, TreeItem<MyNode> oldValue, TreeItem<MyNode> newValue) {
                try {
                    foutmelding.setText("");
                    selectedClauseComponent = rc.findClauseById(newValue.getValue().getId());
                    if (newValue.getValue().getType().equals("Clause")) {
                        beschrijving.setDisable(false);
                        parDropd.setDisable(false);
                        operatorDropd.setDisable(false);
                        waardeParameter.setDisable(false);
                        vegetatie.setDisable(true);
                        operatorDropd.setValue(selectedClauseComponent.getOperator());

                        parDropd.setValue(rc.findParameterById(selectedClauseComponent.getPar1_ParameterId()).getDiscriminator());
                        waardeParameter.setText(selectedClauseComponent.getWaarde() + "");
                        beschrijving.setText(selectedClauseComponent.getName());

                    } else {
                        beschrijving.setDisable(false);
                        vegetatie.setDisable(false);
                        vegetatie.setText(selectedClauseComponent.getVegetationfeature());
                        beschrijving.setText(selectedClauseComponent.getClimatefeature());
                        parDropd.setDisable(true);
                        operatorDropd.setDisable(true);
                        waardeParameter.setDisable(true);
                    }
                } catch (Exception e) {
                    foutmelding.setText("Fout met het weergeven van de eigenschappen");
                }

//            
            }
        });
    }

    public void recursiveClause(TreeItem<MyNode> node, ClauseComponent parentClause, Boolean typeClause) {
        try {
            ClauseComponent clause;
            if (typeClause) {
                clause = rc.findClauseById(parentClause.getYesClause());
            } else {
                clause = rc.findClauseById(parentClause.getNoClause());
            }
            if (clause.getName() != null) {
                TreeItem<MyNode> newNode = new TreeItem<>(new MyNode(clause.getName(), "Clause", clause.getClauseComponentId()));
                recursiveClause(newNode, clause, true);
                recursiveClause(newNode, clause, false);
                node.getChildren().add(newNode);
            } else {
                TreeItem<MyNode> newNode = new TreeItem<>(new MyNode(clause.getClimatefeature(), "Result", clause.getClauseComponentId()));
                node.getChildren().add(newNode);
            }
        } catch (NullPointerException ex) {

        }
    }

    @FXML
    private void saveDetItem(MouseEvent event) {
        try {
            if (selectedClauseComponent != null) {
                if (selectedClauseComponent.getDiscriminator().equals("Clause")) {
                    if (operatorDropd.getSelectionModel().getSelectedItem() != null) {
                        selectedClauseComponent.setOperator(operatorDropd.getSelectionModel().getSelectedItem());
                    }
                    if (parDropd.getSelectionModel().getSelectedItem() != null) {
                        selectedClauseComponent.setPar1_ParameterId(rc.findParameterByName(parDropd.getSelectionModel().getSelectedItem()).getParameterId());
                    }
                    selectedClauseComponent.setWaarde(Integer.parseInt(waardeParameter.getText()));
                    selectedClauseComponent.setName(beschrijving.getText());
                } else {
                    selectedClauseComponent.setClimatefeature(beschrijving.getText());
                    selectedClauseComponent.setVegetationfeature(vegetatie.getText());
                }
                TreeIterator<MyNode> iterator = new TreeIterator<>(root);
                while (iterator.hasNext()) {
                    MyNode node = iterator.next().getValue();
                    if (node.getId() == selectedClauseComponent.getClauseComponentId()) {
                        node.setValue(beschrijving.getText());
                        iterator.expand();
                        break;
                    }
                }
                rc.updateRepo();
                foutmelding.setText("Opslaan succesvol");
            }
        } catch (NumberFormatException ex) {
            foutmelding.setText("Er mag geen text of kommagetallen in de tekstbox van waarde staan");
        } catch (Exception e) {
            foutmelding.setText("Er is een onbekende fout opgetreden");
        }

    }

    //Determineertabel wordt niet verwijderd, deze wordt bij de grade gewoon op null gezet zodat er voor deze grade 
    //een nieuwe tabel kan gemaakt worden, of gekoppeld aan een reeds bestaande determineertabel
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
    }

    @FXML
    private void createDeterminateTable() {
        int graad = Integer.parseInt(createGradeCombo.getSelectionModel().getSelectedItem().split(" ")[1]);
        String name ="";
        if(txtNameNewDeterminateTable.getText().length()==0)
            name = "Nieuwe determineertabel";
        else
            name = txtNameNewDeterminateTable.getText();
        rc.createDeterminateTable(graad, name);
        rc.updateRepo();
        initialize();
    }
    
    @FXML
    private void connectDeterminateTableToGrade(){
        int detTableId = Integer.parseInt(createDeterminateTableCombo.getSelectionModel().getSelectedItem().split(" ")[0]);
        int graad = Integer.parseInt(createGradeCombo.getSelectionModel().getSelectedItem().split(" ")[1]);
        Grade g = rc.findGradeById(graad);
        DeterminateTable d = rc.findDeterminateTableById(detTableId);
        g.setDeterminateTableId(d);
        rc.updateRepo();
        lblActiveDeterminateTable.setText("Succesvol gekoppeld.");
        initialize();
    }

    @FXML
    private void addClause(){
        //comboChooseParent
        viewDeterminateTable();
    }
}
