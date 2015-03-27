/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

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

    public ContinentenBeheer() {
        //creatie en opvullen van de ObservableList
        continenten = FXCollections.observableArrayList(
                continentenMapper.geefContinenten()); //de
        //mapper klasse geeft een lijst van continenten terug
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
