package ast;

public class SimpleType extends Abstract_Type {
    public String Spelling;
    
    public SimpleType (String Spelling) {
    	this.Spelling = Spelling;
    }
    
    public void visitor(String preTxt) {
    	System.out.print(preTxt + this.Spelling);
    }
}