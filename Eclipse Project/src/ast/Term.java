package ast;

public class Term extends Abstract_Term {
    public Abstract_Factor AF1;
    public Abstract_Operator AO;
    public Abstract_Factor AF2;
    
    public Term (Abstract_Factor AF1, Abstract_Operator AO, Abstract_Factor AF2) {
    	this.AF1 = AF1;
    	this.AO = AO;
    	this.AF2 = AF2;
    }
}