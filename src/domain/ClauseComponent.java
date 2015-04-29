
package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    @ManyToOne
    @JoinColumn(name = "DeterminateTableId")
    private DeterminateTable determinateTableId;
    private String discriminator;
    private String Name;
    @OneToOne
    @JoinColumn(name = "YesClause_ClauseComponentId")
    private ClauseComponent YesClause_ClauseComponentId;
    @OneToOne
    @JoinColumn(name = "NoClause_ClauseComponentId")
    private ClauseComponent NoClause_ClauseComponentId;
    private String Climatefeature;
    private int Waarde;
    private String Operator;
    
    @ManyToOne
    @JoinColumn(name = "Par1_ParameterId")
    private Parameter Par1_ParameterId;
    @ManyToOne
    @JoinColumn(name = "Par2_ParameterId")
    private Parameter Par2_ParameterId;

    
    private String Vegetationfeature;
    
    public ClauseComponent()
    {

    }
    
    public ClauseComponent(String name, String discriminator, String operator, Parameter parameter1Id, Parameter parameter2Id, DeterminateTable determinateTable)
    {
        this.Name = name;
        this.discriminator = discriminator;
        this.Operator = operator;
        this.Par1_ParameterId = parameter1Id;
        this.Par2_ParameterId = parameter2Id;
        this.determinateTableId = determinateTable;
        this.NoClause_ClauseComponentId = null;
        this.YesClause_ClauseComponentId = null;
    }
    
    public ClauseComponent(String name, String discriminator, int waarde, String operator, Parameter parameter1Id, DeterminateTable determinateTable)
    {
        this.Name = name;
        this.discriminator = discriminator;
        this.Waarde = waarde;
        this.Operator = operator;
        this.Par1_ParameterId = parameter1Id;
        this.determinateTableId = determinateTable;
        this.NoClause_ClauseComponentId = null;
        this.YesClause_ClauseComponentId = null;
    }

    public String getVegetationfeature() {
        return Vegetationfeature;
    }

    public void setVegetationfeature(String Vegetationfeature) {
        this.Vegetationfeature = Vegetationfeature;
    }

    public Parameter getPar1_ParameterId() {
        return Par1_ParameterId;
    }

    public void setPar1_ParameterId(Parameter Par1_ParameterId) {
        this.Par1_ParameterId = Par1_ParameterId;
    }

    public Parameter getPar2_ParameterId() {
        return Par2_ParameterId;
    }

    public void setPar2_ParameterId(Parameter Par2_ParameterId) {
        this.Par2_ParameterId = Par2_ParameterId;
    }
    
    public DeterminateTable getDeterminateTable() {
        return determinateTableId;
    }

    public void setDeterminateTableId(DeterminateTable determinateTable) {
        this.determinateTableId = determinateTable;
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

    public ClauseComponent getYesClause() {
        return YesClause_ClauseComponentId;
    }

    public void setYesClause(ClauseComponent YesClause_ClauseComponentId) {
        this.YesClause_ClauseComponentId = YesClause_ClauseComponentId;
    }

    public ClauseComponent getNoClause() {
        return NoClause_ClauseComponentId;
    }

    public void setNoClause(ClauseComponent NoClause_ClauseComponentId) {
        this.NoClause_ClauseComponentId = NoClause_ClauseComponentId;
    }

    public void setClimatefeature(String Climatefeature) {
        this.Climatefeature = Climatefeature;
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

    public int getWaarde() {
        return Waarde;
    }

    public void setWaarde(int Waarde) {
        this.Waarde = Waarde;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String Operator) {
        this.Operator = Operator;
    }
    
    @Override
    public String toString(){
        return Name;
    }
    
}