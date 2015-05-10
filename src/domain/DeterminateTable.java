package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity(name="DeterminateTable")
@Table(name = "DeterminateTable")
@NamedQueries({
    @NamedQuery(name="DeterminateTable.findById",query= "select d from DeterminateTable d WHERE d.determinateTableId = :id"),
    @NamedQuery(name="DeterminateTable.findAllTables",query= "select d from DeterminateTable d"),
    
}) 
public class DeterminateTable implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int determinateTableId;
    
    private String name;

    public DeterminateTable(){
        
    }
    
    public DeterminateTable(String name){
        this.name = name;
    }
    
    public int getDeterminateTableId() {
        return determinateTableId;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString()
    {
        return "DeterminatieTabel " + getDeterminateTableId();
    }
}
