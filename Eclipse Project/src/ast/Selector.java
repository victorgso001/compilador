package ast;

public class Selector extends Abstract_Identifier {
    public Abstract_Identifier AI;
    public Abstract_Expression AE;
    
    public Selector (Abstract_Identifier AI, Abstract_Expression AE) {
    	this.AI = AI;
    	this.AE = AE;
    }
    
    public void visitor(String preTxt) {
    	this.AI.visitor(preTxt);
    	System.out.println(preTxt + "[");
    	this.AE.visitor(preTxt + "|");
    	System.out.println(preTxt + "]");
    	
    }
}