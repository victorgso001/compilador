package ast;

public class Identifier extends Abstract_Identifier {
    public String Spelling;
    
    public Identifier (String Spelling) {
    	this.Spelling = Spelling;
    }
    
    public void visitor(String preTxt) {
    	System.out.println(preTxt + this.Spelling);
    }
}