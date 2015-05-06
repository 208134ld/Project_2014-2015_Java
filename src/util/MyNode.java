package util;

public class MyNode {

    private String value;
    private String type;
    private int id;

    public MyNode(String value, String type, int id) {
        this.type = type;
        this.value = value;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public boolean isClause() {
        return type.equalsIgnoreCase("Clause");
    }

    public boolean isClassgroup() {
        return type.equalsIgnoreCase("classgroup");
    }

    @Override
    public String toString() {
        return value;

    }

}
