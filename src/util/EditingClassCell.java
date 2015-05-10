package util;

import domain.Student;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;

public class EditingClassCell extends TableCell<Student, String> {

    private TextField textField;

    public EditingClassCell() {

    }

    @Override
    public void startEdit() {
        super.startEdit();
        createTextField();
        setGraphic(textField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(String.valueOf(getItem()));
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setText(getString());
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getItem());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        String st = textField.getText();
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER || t.getCode() == KeyCode.TAB) {
                    try {
                        String regex = "^[a-zA-Z]+$";
                        if (textField.getText().trim().matches(regex) == false) {
                            throw new NumberFormatException();
                        } else {
                            commitEdit(textField.getText());
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Gelieve het veld niet leeg te laten, en geen cijfers in te geven", "Er is een fout opgetreden", JOptionPane.ERROR_MESSAGE);
                        textField.setText(st);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "er is iets onverwacht gebeurt" + e.getCause().getMessage());
                    }

                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
