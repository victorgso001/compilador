package ast;

public class Factor extends Abstract_Factor {
    public Abstract_Identifier AI;
    public Abstract_Vname AVN;
    public Abstract_Expression AE;
    
    public Factor (Abstract_Identifier AI, Abstract_Vname AVN, Abstract_Expression AE) {
    	this.AI = AI;
    	this.AVN = AVN;
    	this.AE = AE;
    }
}