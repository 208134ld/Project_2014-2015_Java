
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
    private int ClauseComponentId;
    private int determinateTableId;
    private String discriminator;
    private String Name;
    private int YesClause_ClauseComponentId;
    private int NoClause_ClauseComponentId;
    private String Climatefeature;
    
    public ClauseComponent()
    {

    }
    
    public int getDeterminateTableId() {
        return determinateTableId;
    }

    public void setDeterminateTableId(int determinateTableId) {
        this.determinateTableId = determinateTableId;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    
    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public int getYesClause_ClauseComponentId() {
        return YesClause_ClauseComponentId;
    }

    public void setYesClause_ClauseComponentId(int YesClause_ClauseComponentId) {
        this.YesClause_ClauseComponentId = YesClause_ClauseComponentId;
    }

    public int getNoClause_ClauseComponentId() {
        return NoClause_ClauseComponentId;
    }

    public void setNoClause_ClauseComponentId(int NoClause_ClauseComponentId) {
        this.NoClause_ClauseComponentId = NoClause_ClauseComponentId;
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