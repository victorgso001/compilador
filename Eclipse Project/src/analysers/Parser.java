//package analysers;
//
//public class Parser {
//	  private Token curentToken;
//	  private Scanner scanner;
//
//	  
//	  public Parser(String path) {
//		  scanner = new Scanner(path);
//	  }
//	  
//	  
//	  
//	  private accept (byte expectedKind) {
//	    if (this.currentToken.kind == expectedKind) {
//	      this.currentToken = scanner.scan();
//	    }
//	    else {
//	      // REPORTAR ERRO AQUI
//	    }
//	  }
//
//	  private void acceptIt () {
//	    this.curentToken = scanner.scan();
//	  }
//
//	  public void parse () {
//	    this.currentToken = scanner.scan();
//	    this.parseProgram();
//
//	    if (currentToken.kind != Token.DOT) {
//	      // REPORTAR ERRO AQUI
//	    }
//	  }
//
//	  private void parseIdentifier () {
//	    if (this.currentToken.kind == Token.ID) {
//	      this.acceptIt();
//	    }
//	    else {
//	      // REPORTAR ERRO AQUI
//	    }
//	  }
//	}
