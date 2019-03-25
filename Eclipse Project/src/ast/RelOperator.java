package ast;

public class RelOperator extends Abstract_Operator {
    public String Spelling;
    
    public RelOperator (String Spelling) {
    	this.Spelling = Spelling;
    }
    
    public void visitor(String preTxt) {
    	System.out.println(preTxt + this.Spelling);
    }
}