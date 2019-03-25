package ast;

public class AssignCommand extends Abstract_Command {
    public Abstract_Identifier AI;
    public Abstract_Expression AE;
    
    public AssignCommand (Abstract_Identifier AI, Abstract_Expression AE) {
    	this.AI = AI;
    	this.AE = AE;
    }
    
    public void visitor(String preTxt) {
    	this.AI.visitor(preTxt);
    	this.AE.visitor(preTxt);
    }
}