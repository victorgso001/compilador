package ast;

public class AggregateType extends Abstract_Type {
    public Abstract_Vname AVN1;
    public Abstract_Vname AVN2;
    public Abstract_Type AType;
    
    public AggregateType (Abstract_Vname AVN1, Abstract_Vname AVN2, Abstract_Type AType) {
    	this.AVN1 = AVN1;
    	this.AVN2 = AVN2;
    	this.AType = AType;
    }
    
    public void visitor (String preTxt) {
    	System.out.print(preTxt + "[");
    	this.AVN1.visitor("");
    	System.out.print("..");
    	this.AVN2.visitor("");
    	System.out.print("]");
    }
}