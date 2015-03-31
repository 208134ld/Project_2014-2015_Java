/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Stijn
 */
public class MyNode {
        public String value;
        public String type;
        public int id;

        public MyNode(String value, String type, int id) {
            this.type = type;
            this.value = value;
            this.id = id;
        }

        public boolean isContinent() {
            return type.equalsIgnoreCase("Continent");
        }

        public boolean isCountry() {
            return type.equalsIgnoreCase("Country");
        }

        public boolean isClimateChart() {
            return type.equalsIgnoreCase("ClimateChart");
        }

        @Override
        public String toString() {
            return value;

        }
    
}
