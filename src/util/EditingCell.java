package util;

import domain.Months;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;

public class EditingCell extends TableCell<Months, Number> {

    private TextField textField;
    private Boolean isInteger;

    public EditingCell(Boolean isInteger) {
        this.isInteger = isInteger;
    }

    @Override
    public void startEdit() {

        super.startEdit();

        if (textField == null) {
            createTextField();
        }

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
    public void updateItem(Number item, boolean empty) {
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
        textField = new TextField(getItem().toString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        String st = textField.getText();
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER || t.getCode() == KeyCode.TAB) {
                    try {
                        if (isInteger) {
                            if(Double.parseDouble(textField.getText())<0)
                                throw new IllegalArgumentException();
                            Double d = Double.parseDouble(textField.getText());
                            commitEdit(Math.floor(d));
                        } else {
                            commitEdit(Double.parseDouble(textField.getText()));
                        }

                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Heeft u tekst ingegeven? een komma wordt door een . weergegeven.", "Er is een fout opgetreden", JOptionPane.ERROR_MESSAGE);
                        textField.setText(st);
                    }catch(IllegalArgumentException ilex)
                    {
                        JOptionPane.showMessageDialog(null,"Neerslag mag niet negatief zijn");
                        textField.setText(st);
                    }
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "er is iets onverwacht gebeurt" + e.getCause().getMessage());
                    }

                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
