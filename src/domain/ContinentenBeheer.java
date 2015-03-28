/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistentie.ContinentenMapper;

/**
 *
 * @author Stijn
 */
public class ContinentenBeheer {

    private ObservableList<String> continenten;
    private ContinentenMapper continentenMapper = new ContinentenMapper();

    public ContinentenBeheer() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=HOGENT1415_11";   //database specific url.
        String user = "sa";
        String password = "root";

        Connection connection
                = DriverManager.getConnection(url, user, password);

        Statement statement = connection.createStatement();
        String sql = "select * from Continents";
        ResultSet result = statement.executeQuery(sql);
        
        List continentenList = new ArrayList<>();
        
        while(result.next()) {
            continentenList.add(result.getString("name"));
        }
        
        continenten = FXCollections.observableArrayList(
                continentenList);
    }

    public ObservableList<String> getContinenten() {
        return continenten;
    }
    
    public boolean noContinenten(){
        return continenten.isEmpty();
    }
    
    public void addContinent(String naam){
        if(naam != null && !naam.trim().isEmpty()){
            continenten.add(naam);
        }
    }
    
    public void removeContinent(String naam){
        continenten.remove(naam);
    }
}
