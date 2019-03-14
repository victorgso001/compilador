package ast;

public class DeclarationList extends Abstract_Declaration {
    public Abstract_Declaration AD1;
    public Abstract_Declaration AD2;
    
    public DeclarationList (Abstract_Declaration AD1, Abstract_Declaration AD2) {
    	this.AD1 = AD1;
    	this.AD2 = AD2;
    }
}