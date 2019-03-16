package ast;

public class MultipleExpression extends Abstract_Expression {
	Abstract_SimpleExpression SE1, SE2;
	
	public MultipleExpression (Abstract_SimpleExpression SE1, Abstract_SimpleExpression SE2) {
		this.SE1 = SE1;
		this.SE2 = SE2;
	}
}
