package domain;

public class Clause extends ClauseComponent {

    public ClauseComponent YesClause;
    public ClauseComponent NoClause;
    public String Name;

    public Parameter Par1;
    public Parameter Par2;
    public int Waarde;
    public String Operator;

    public Clause() {
    }

    public Clause(String name, Parameter par1, String op, int waarde) {
        this.Name = name;
        this.Par1 = par1;
        this.Operator = op;
        this.Waarde = waarde;
    }

    public Clause(String name, Parameter par1, Parameter par2) {
        this.Name = name;
        this.Par1 = par1;
        this.Par2 = par2;
    }
}
