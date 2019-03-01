package ast;

public class Declaration extends Abstract_Declaration {
    public Abstract_Identifier AI;
    public Abstract_Type AType;
    
    public Declaration (Abstract_Identifier AI, Abstract_Type AType) {
    	this.AI = AI;
    	this.AType = AType;
    }
}