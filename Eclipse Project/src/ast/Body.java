package ast;

public class Body extends Abstract_Body {
    public Abstract_Declaration AD;
    public Abstract_Command AC;
    
    public Body (Abstract_Declaration AD, Abstract_Command AC) {
    	this.AD = AD;
    	this.AC = AC;
    }
}