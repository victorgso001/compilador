package ast;

public class AdOperator extends Abstract_Operator {
    public String Spelling;
    
    public AdOperator (String Spelling) {
    	this.Spelling = Spelling;
    }
    
    public void visitor(String preTxt) {
    	System.out.println(preTxt + this.Spelling);
    }
}