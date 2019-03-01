package ast;

public class Program extends Abstract_Program {
    public Abstract_Identifier AI;
    public Abstract_Body AB;
    
    public Program (Abstract_Identifier AI, Abstract_Body AB) {
    	this.AI = AI;
    	this.AB = AB;
    }
}