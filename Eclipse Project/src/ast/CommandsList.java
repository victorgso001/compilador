package ast;

public class CommandsList extends Abstract_Command {
    public Abstract_Command AC1;
    public Abstract_Command AC2;
    
    public CommandsList (Abstract_Command AC1, Abstract_Command AC2) {
    	this.AC1 = AC1;
    	this.AC2 = AC2;
    }
}