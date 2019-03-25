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
    
    public void visitor(String preTxt) {
    	if (this.AI != null) {
    		this.AI.visitor(preTxt);
    	}
    	if (this.AVN != null) {
    		this.AVN.visitor(preTxt);
    		System.out.println();
    	}
    	if (this.AE != null) {
    		this.AE.visitor(preTxt);
    	}
	}
}