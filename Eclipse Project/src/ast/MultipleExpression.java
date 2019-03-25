package ast;

public class MultipleExpression extends Abstract_Expression {
	Abstract_Expression SE1, SE2;
	Abstract_Operator AO;
	
	public MultipleExpression (Abstract_Expression SE1, Abstract_Operator AO, Abstract_Expression SE2) {
		this.SE1 = SE1;
		this.AO = AO;
		this.SE2 = SE2;
	}
	
	public void visitor(String preTxt) {
		if (this.AO != null) {
			this.AO.visitor(preTxt);
		}
    	if (this.SE1 != null) {
    		this.SE1.visitor(preTxt);
    	}
    	if (this.SE2 != null) {
    		this.SE2.visitor(preTxt);
    	}
	}
}
