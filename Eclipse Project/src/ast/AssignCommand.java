package ast;

public class AssignCommand extends Abstract_Command {
    public Abstract_Identifier AI;
    public Abstract_Expression AE1;
    public Abstract_Expression AE2;
    
    public AssignCommand (Abstract_Identifier AI, Abstract_Expression AE1, Abstract_Expression AE2) {
    	this.AI = AI;
    	this.AE1 = AE1;
    	this.AE2 = AE2;
    }
}