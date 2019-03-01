package ast;

public class IDsList extends Abstract_Identifier {
    public Abstract_Identifier AI1;
    public Abstract_Identifier AI2;
    
    public IDsList (Abstract_Identifier AI1, Abstract_Identifier AI2) {
    	this.AI1 = AI1;
    	this.AI2 = AI2;
    }
}