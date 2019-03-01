package ast;

public class IfCommand extends Abstract_Command {
    public Abstract_Expression AE;
    public Abstract_Command AC1;
    public Abstract_Command AC2;
    
    public IfCommand (Abstract_Expression AE, Abstract_Command AC1, Abstract_Command AC2) {
    	this.AE = AE;
    	this.AC1 = AC1;
    	this.AC2 = AC2;
    }
}