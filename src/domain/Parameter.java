package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity(name="Parameter")
@Table(name = "Parameter")
@NamedQueries({
    @NamedQuery(name="Parameter.findAll",query="select p from Parameter p"),
    @NamedQuery(name="Parameter.findById",query="select p from Parameter p where p.ParameterId = :parameterId"),
    @NamedQuery(name="Parameter.findByName",query="select p from Parameter p where p.Discriminator = :name")
})
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int ParameterId;
    public String Beschrijving;
    private String Discriminator;
    
    public Parameter(){
        
    }

    public String getDiscriminator() {
        return Discriminator;
    }

    public void setDiscriminator(String Discriminator) {
        this.Discriminator = Discriminator;
    }
    
    public int getParameterId() {
        return ParameterId;
    }

    public String getBeschrijving() {
        return Beschrijving;
    }

    public void setBeschrijving(String Beschrijving) {
        this.Beschrijving = Beschrijving;
    }
    
    @Override
    public String toString(){
        return Discriminator;
    }
}
