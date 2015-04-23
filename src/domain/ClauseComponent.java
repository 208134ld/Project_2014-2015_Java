
package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity(name="ClauseComponents")
@Table(name = "ClauseComponents")
@NamedQueries({
    @NamedQuery(name="ClauseComponent.findClauseComponentsByDeterminateTableId",query= "select c from ClauseComponents c WHERE c.determinateTableId = :determinateTableId"),
    @NamedQuery(name="Clause.findClausesByDeterminateTableId",query= "select c from ClauseComponents c WHERE c.determinateTableId = :determinateTableId AND c.discriminator = :discriminator"),
    @NamedQuery(name="Clause.findClauseById",query= "select c from ClauseComponents c WHERE c.ClauseComponentId = :clauseId")
}) 
public class ClauseComponent implements Serializable
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int ClauseComponentId;
    private int determinateTableId;
    private String discriminator;
    private String Name;
    private int YesClause_ClauseComponentId;
    private int NoClause_ClauseComponentId;
    private String Climatefeature;

    public ClauseComponent()
    {

    }
    
    public String getClimatefeature() {
        return Climatefeature;
    }
    
    public int getClauseComponentId() {
        return ClauseComponentId;
    }
    
    public String getName() {
        return Name;
    }
    
    public int getYesClause() {
        return YesClause_ClauseComponentId;
    }

    public int getNoClause() {
        return NoClause_ClauseComponentId;
    }
}