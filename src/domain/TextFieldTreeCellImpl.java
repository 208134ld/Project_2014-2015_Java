
package domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public final class TextFieldTreeCellImpl extends TreeCell<String> {
 
        private TextField textField;
        
        String oldItem="";
 
        private Connection connection;
        String url = "jdbc:sqlserver://localhost:1433;databaseName=HOGENT1415_11";
        String user = "sa";
        String password = "root";
        Statement statement;
        
        public TextFieldTreeCellImpl() throws SQLException {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        }
 
        @Override
        public void startEdit() {
            super.startEdit();
 
            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());
        }
 
        @Override
        public void updateItem(String item, boolean empty) {
            
            super.updateItem(item, empty);
 
            //String sql = "UPDATE Continents SET Name='"+item+"' WHERE Name='"+oldItem+"'";
            
//            try {
//                ResultSet result = statement.executeQuery(sql);
//            } catch (SQLException ex) {
//                Logger.getLogger(TextFieldTreeCellImpl.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//            oldItem = item;
            
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                }
            }
            
        }
 
        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
 
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
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