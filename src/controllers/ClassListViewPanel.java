package controllers;

import domain.ClassGroup;
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
import javafx.collections.transformation.SortedList;
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
import util.EditingClassCell;
import util.MyNode;
import util.TextFieldTreeCellImpl;

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

    private SortedList<Student> studentList;
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ClassListViewPanel.fxml"));
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

        controller.giveAllGrades().stream().map((g) -> {
            TreeItem<MyNode> grade = new TreeItem<>(new MyNode("Graad " + g.getGradeString(), "Graad", g.getGrade()));
            controller.giveSchoolYearsOfGrade(g).stream().map((sy) -> {
                TreeItem<MyNode> schoolYear = new TreeItem<>(new MyNode("Leerjaar " + sy.getSchoolYearString(), "Leerjaar", sy.getSchoolYear()));
                controller.giveClassGroupOfSchoolYear(sy).stream().map((cg) -> new TreeItem<>(new MyNode(cg.getGroupName(), "classgroup", cg.getGroupId()))).forEach((classGroup) -> {
                    schoolYear.getChildren().add(classGroup);
                });
                return schoolYear;
            }).map((schoolYear) -> {
                grade.getChildren().add(schoolYear);
                return schoolYear;
            }).forEach((schoolYear) -> {
                treeItems.add(schoolYear);
            });
            return grade;
        }).map((grade) -> {
            gradeItems.add(grade);
            return grade;
        }).forEach((grade) -> {
            treeItems.add(grade);
        });

        obsTreeItems = FXCollections.observableArrayList(gradeItems);
        rootItem.getChildren().addAll(obsTreeItems);
        rootItem.setExpanded(true);

        rootItem.getChildren().forEach(p -> p.setExpanded(true));

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

        classTreeView.setRoot(rootItem);

        classTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<MyNode>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<MyNode>> observable, TreeItem<MyNode> oldValue, TreeItem<MyNode> newValue) {
                TreeItem<MyNode> selectedItem = newValue;
                if (selectedItem != null) {
                    if (selectedItem.getValue().isClassgroup()) {
                        selectedClassGroup = rc.findClassGroupById(selectedItem.getValue().getId());
                        updateStudentListDetailPanel(selectedClassGroup);
                    }
                }
            }
        });

        if (selectedClassGroup != null) {
            updateStudentListDetailPanel(selectedClassGroup);
        }

        studentInfoTable.getSelectionModel().selectedItemProperty().
                addListener((observableValue, oldValue, newValue) -> {
                    if (newValue != null) {
                        int index = studentInfoTable.getSelectionModel().getSelectedIndex();
//                        if (index != currentIndex) {
                            cm.getItems().clear();
                            MenuItem cmItem2 = new MenuItem("Verwijder " + newValue.getFirtsName());
                            cmItem2.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    controller.removeStudent(newValue);
                                    studentListObservable.remove(newValue);
                                    updateStudentListDetailPanel(selectedClassGroup);
                                }
                            });
                            MenuItem cmItem3 = new MenuItem("Verander klasgroep");
                            cmItem3.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    List<ClassGroup> choices =controller.giveAllClassGroups().stream().sorted(bySchoolYear).collect(Collectors.toList());
                                    choices.remove(newValue.getClassGroup());
                                    ChoiceDialog<ClassGroup> dialog = new ChoiceDialog<>(choices.get(0), choices);
                                    
                                    dialog.setTitle("Verander de klas voor " + newValue.getFullName());
                                    dialog.setHeaderText("Verander klas voor " + newValue.getFullName());
                                    dialog.setContentText("Kies de klas");

                                    Optional<ClassGroup> result = dialog.showAndWait();
                                    if (result.isPresent()) {
                                        ClassGroup c = result.get();
                                        newValue.setClassGroup(c);
                                        controller.addStudent(newValue);
                                        updateStudentListDetailPanel(selectedClassGroup);
                                    }
                                }
                            });
                            cm.getItems().add(cmItem2);
                            cm.getItems().add(cmItem3);
                            studentInfoTable.setContextMenu(cm);
                            currentIndex = index;
//                        }
                    }
                });
    }

    public void updateStudentListDetailPanel(ClassGroup cg) {
        classLbl.setText(controller.giveGradeInfo(cg)); //Van de geselectreerde grade
        studentList = controller.giveStudentsOfClassGroupSorted(cg);
        studentListObservable = FXCollections.observableArrayList(studentList);
        studentInfoTable.setItems(studentListObservable);
    }

    @Override
    public void update(Observable o, Object arg) {
        updateClassListViewPanel();
    }

    private void initTable() {
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
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
            studentListObservable.stream().filter((s) -> (event.getRowValue().getStudentId() == s.getStudentId())).map((s) -> {
                s.setLastName(event.getNewValue());
                return s;
            }).forEach((s) -> {
                controller.addStudent(s);
            });
        }
        if (studentInfoTable.getSelectionModel().getSelectedCells().get(0).getColumn() == 1) {
            studentListObservable.stream().filter((s) -> (event.getRowValue().getStudentId() == s.getStudentId())).map((s) -> {
                s.setFirtsName(event.getNewValue());
                return s;
            }).forEach((s) -> {
                controller.addStudent(s);
            });
        }
    }
    private final Comparator<ClassGroup> bySchoolYear = (p1, p2) -> Integer.compare(p1.getSchoolYear().getSchoolYear(),p2.getSchoolYear().getSchoolYear());
}
