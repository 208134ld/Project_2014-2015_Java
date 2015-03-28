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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistentie.ContinentenMapper;

/**
 *
 * @author Stijn
 */
public class ContinentenBeheer {

    private ObservableList<Continent> continenten;
    private ContinentenMapper continentenMapper = new ContinentenMapper();
    private Connection connection;
    public ContinentenBeheer() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=HOGENT1415_11";   //database specific url.
        String user = "sa";
        String password = "root";
       connection
                = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        String sql = "select * from Continents";
        ResultSet result = statement.executeQuery(sql);
        
        List continentenList = new ArrayList<>();
        
        while(result.next()) {
            
            continentenList.add(new Continent(result.getString("name"),result.getInt("ContinentID")));
        }
        
        continenten = FXCollections.observableArrayList(
                continentenList);
    }

    public ObservableList<Continent> getContinenten()
    {
        return continenten;
    }
    public boolean noContinenten(){
        return continenten.isEmpty();
    }
    
    public void addContinent(String naam){
        
        if(naam != null && !naam.trim().isEmpty()){
          
            
            try {
                connection.createStatement().execute("insert into continents values('"+naam+"')");
                ResultSet r = connection.createStatement().executeQuery("select MAX(Continents.ContinentID) from Continents");
                r.next();
                continenten.add(new Continent(naam,r.getInt(1)));
            } catch (SQLException ex) {
                Logger.getLogger(ContinentenBeheer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void removeContinent(Continent cont){
        continenten.remove(cont);
        try {
            connection.createStatement().execute("delete from Continents where ContinentID = "+cont.getId());
        } catch (SQLException ex) {
            Logger.getLogger(ContinentenBeheer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
