package ast;

public class Vname extends Abstract_Vname {
    public String Spelling;
    
    public Vname (String Spelling) {
    	this.Spelling = Spelling;
    }
    
    public void visitor(String preTxt) {
    	System.out.print(preTxt + this.Spelling);
    }
}