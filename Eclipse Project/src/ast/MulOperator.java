package ast;

public class MulOperator extends Abstract_Operator {
    public String Spelling;
    
    public MulOperator (String Spelling) {
    	this.Spelling = Spelling;
    }
    
    public void visitor(String preTxt) {
    	System.out.println(preTxt + this.Spelling);
    }
}