package ast;

public class WhileCommand extends Abstract_Command {
    public Abstract_Expression AE;
    public Abstract_Command AC;
    
    public WhileCommand (Abstract_Expression AE, Abstract_Command AC) {
    	this.AE = AE;
    	this.AC = AC;
    }
    
    public void visitor(String preTxt) {
    	System.out.println(preTxt + "while");
    	this.AE.visitor(preTxt + "|");
    	this.AC.visitor(preTxt + "|");
    }
}