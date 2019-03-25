package ast;

public class IDsList extends Abstract_Identifier {
    public Abstract_Identifier AI1;
    public Abstract_Identifier AI2;
    
    public IDsList (Abstract_Identifier AI1, Abstract_Identifier AI2) {
    	this.AI1 = AI1;
    	this.AI2 = AI2;
    }
    
    public void visitor(String preTxt) {
    	if (this.AI2 != null) {
    		this.AI2.visitor(preTxt + "|");
    	}
    	if (this.AI1 != null) {
    		this.AI1.visitor(preTxt + "|");
    	}
    }
}