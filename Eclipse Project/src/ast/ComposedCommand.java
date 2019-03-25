package ast;

public class ComposedCommand extends Abstract_Command {
    public Abstract_Command AC;
    
    public ComposedCommand (Abstract_Command AC) {
    	this.AC = AC;
    }
    
    public void visitor(String preTxt) {
    	this.AC.visitor(preTxt);
    }
}