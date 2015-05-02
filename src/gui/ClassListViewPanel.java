package gui;

import domain.ClassGroup;
import domain.ClassListController;
import domain.Grade;
import domain.Months;
import domain.SchoolYear;
import domain.Student;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import repository.RepositoryController;
import util.EditingCell;
import util.EditingClassCell;
import util.MyNode;
import util.TextFieldTreeCellImpl;

/**
 * FXML Controller class
 *
 * @author SAMUEL
 * @author bremmewindows
 */
public class ClassListViewPanel extends GridPane implements Observer {

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
    private ObservableList<TreeItem<MyNode>> obsTreeItems;
    private List<TreeItem<MyNode>> gradeItems;
    private List<TreeItem<MyNode>> treeItems;
    private RepositoryController rc;
    private ContextMenu cm = new ContextMenu();

    public ClassListViewPanel(ClassListController clc) {

        //Controllers
        controller = clc;
        rc = new RepositoryController();
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
        initTable();
        updateClassListViewPanel();

    }

    public void updateClassListViewPanel() {
        TreeItem<MyNode> rootItem = new TreeItem<>(new MyNode("ClassGroups", "Root", 1));
        treeItems.clear();
        gradeItems.clear();

        for (Grade g : controller.giveAllGrades()) {
            TreeItem<MyNode> grade = new TreeItem<>(new MyNode("Graad " + g.getGradeString(), "Graad", g.getGrade()));

            for (SchoolYear sy : controller.giveSchoolYearsOfGrade(g)) {
                TreeItem<MyNode> schoolYear = new TreeItem<>(new MyNode("Leerjaar " + sy.getSchoolYearString(), "Leerjaar", sy.getSchoolYear()));
                //controller.giveSchoolYearsOfGrade(g).stream().forEach(c -> System.out.println(c.getSchoolYear()));

                for (ClassGroup cg : controller.giveClassGroupOfSchoolYear(sy)) {
                    TreeItem<MyNode> classGroup = new TreeItem<>(new MyNode(cg.getGroupName(), "classgroup", cg.getGroupId()));
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

        //Onderstaand gedeelte maakt het mogelijk om treeviewitems "on the spot" van naam te veranderen, dit werkt alleen met treeItem<String> dus moet nog aangepast worden
        classTreeView.setEditable(true);

        classTreeView.setCellFactory(new Callback<TreeView<MyNode>, TreeCell<MyNode>>() {
            @Override
            public TreeCell<MyNode> call(TreeView<MyNode> p) {
                try {
                    return new TextFieldTreeCellImpl(rootItem, treeItems, rc);
                } catch (SQLException ex) {
                    Logger.getLogger(ClassListViewPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }

        });

        //itemChild.setExpanded(false);
        classTreeView.setRoot(rootItem);

        selectedClassGroup = null;

        classTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<MyNode>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<MyNode>> observable, TreeItem<MyNode> oldValue, TreeItem<MyNode> newValue) {
                TreeItem<MyNode> selectedItem = newValue;
                if (selectedItem != null) {
                    if (selectedItem.getValue().isClassgroup()) {
                        selectedClassGroup = rc.findClassGroupById(selectedItem.getValue().getId());;
//                     selectedClimatechart = new ClimateChart(1,"Gent",1950,1970,true,23.34534,44.34523,"30° 45' 10\" NB ","51° 3' 15\" OL ",1);
                        updateLocationDetailPanel(selectedClassGroup);
                    }
                }

            }
        });
        //functionaliteit in klastable steken -->leerling verwijderen en veranderen klas
        studentInfoTable.getSelectionModel().selectedItemProperty().
                addListener((observableValue, oldValue, newValue) -> {
                    if (newValue != null) {
                        int index = studentInfoTable.getSelectionModel().getSelectedIndex();
                        if (index != currentIndex) {
                            cm.getItems().clear();
                            MenuItem cmItem2 = new MenuItem("Verwijder " + newValue.getFirtsName());
                            cmItem2.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    controller.removeStudent(newValue);
                                    studentListObservable.remove(newValue);
                                }
                            });
                            MenuItem cmItem3 = new MenuItem("verander klasgroep");
                            cmItem3.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    List<String> choices = new ArrayList<>();
                                    controller.giveAllClassGroups().stream().forEach(ch->choices.add(ch.getGroupName()));

                                    ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
                                    dialog.setTitle("Verander de klas voor " +newValue.getFullName());
                                    dialog.setHeaderText("Verander klas voor " +newValue.getFullName());
                                    dialog.setContentText("Kies de klas");

// Traditional way to get the response value.
                                    Optional<String> result = dialog.showAndWait();
                                    if (result.isPresent()) {
                                        ClassGroup c = controller.giveClassGroupWithName(result.get());
                                        newValue.setClassGroup(c);
                                        controller.addStudent(newValue);
                                        System.out.println("Your choice: " + result.get());
                                    }
                                }
                            });
                            cm.getItems().add(cmItem2);
                            cm.getItems().add(cmItem3);
                            studentInfoTable.setContextMenu(cm);
                            currentIndex = index;
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

    @Override
    public void update(Observable o, Object arg) {
        updateClassListViewPanel();
    }

    private void initTable() {
        //TableView opvullen
            /*De eerste kolom verbinden met de property “firstName” van de klasse Student. */
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        //Analoog tweede kolom:
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        Callback<TableColumn<Student, String>, TableCell<Student, String>> cellFactory
                = new Callback<TableColumn<Student, String>, TableCell<Student, String>>() {

                    @Override
                    public TableCell call(TableColumn p) {
                        return new EditingClassCell();
                    }
                };
        lastNameCol.setCellFactory(cellFactory);
        firstNameCol.setCellFactory(cellFactory);
    }

    @FXML
    private void updateCell(TableColumn.CellEditEvent<Student, String> event) {
        if (studentInfoTable.getSelectionModel().getSelectedCells().get(0).getColumn() == 0) {
            studentListObservable.get(0).setLastName(event.getNewValue());
            for(Student s : studentListObservable)
            {
                if(event.getRowValue().getStudentId()==s.getStudentId()){
                    System.out.println("HHHHHHHHHHHHHIER " +event.getRowValue().getStudentId() + "   --->"+event.getNewValue());
                    s.setLastName(event.getNewValue());
                    controller.addStudent(s);
                }
                    
            }
           

        }
        if (studentInfoTable.getSelectionModel().getSelectedCells().get(0).getColumn() == 1) {
            System.out.println("update firstname");
        for(Student s : studentListObservable)
            {
                if(event.getRowValue().getStudentId()==s.getStudentId()){
                      System.out.println("HHHHHHHHHHHHHIER " +event.getRowValue().getStudentId());
                    s.setFirtsName(event.getNewValue());
                    controller.addStudent(s);
                }
                    
            }
        }
    }
}
