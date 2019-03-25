package ast;

public class MultipleExpression extends Abstract_Expression {
	Abstract_SimpleExpression SE1, SE2;
	
	public MultipleExpression (Abstract_SimpleExpression SE1, Abstract_SimpleExpression SE2) {
		this.SE1 = SE1;
		this.SE2 = SE2;
	}
	
	public void visitor(String preTxt) {
    	if (this.SE1 != null) {
    		this.SE1.visitor(preTxt);
    	}
    	if (this.SE2 != null) {
    		this.SE2.visitor(preTxt);
    	}
	}
}
