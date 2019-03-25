package ast;

public class SimpleExpression extends Abstract_SimpleExpression {
    public Abstract_Term ATerm1;
    public Abstract_Operator AO;
    public Abstract_Term ATerm2;
    
    public SimpleExpression (Abstract_Term ATerm1, Abstract_Operator AO, Abstract_Term ATerm2) {
    	this.ATerm1 = ATerm1;
    	this.ATerm2 = ATerm2;
    	this.AO = AO;
    }
    
	public void visitor(String preTxt) {
		if (this.AO != null) {
			this.AO.visitor(preTxt);
    	}
    	if (this.ATerm1 != null) {
    		this.ATerm1.visitor(preTxt);
    	}
    	if (this.ATerm2 != null) {
    		this.ATerm2.visitor(preTxt);
    	}
    	
	}
}