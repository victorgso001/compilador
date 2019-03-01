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
}