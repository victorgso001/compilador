package analysers;

import java.util.Queue;

public class Parser {
	private Token currentToken;
	private Queue<Token> tokenQueue;
	
	public Parser(Queue<Token> tokenQueue) {
		this.tokenQueue = tokenQueue;
	}
	
	private void accept(byte expectedKind) throws Exception {
		System.out.println("AC: " + expectedKind);
		if (this.currentToken.kind == expectedKind) {
			this.currentToken = this.tokenQueue.peek();
		}
		else {
			throw new Exception("Token inesperado: " + this.tokenQueue.peek().spelling + "(" + this.tokenQueue.peek().line + ", " + this.tokenQueue.peek().column + ")");
		}
	}
	
	private void acceptIt() {
		this.currentToken = this.tokenQueue.poll();
		System.out.println("ACit:" + this.currentToken.kind);
	}
	
	public void run() throws Exception {
		acceptIt();
		
		parseProgram();
		
		if (currentToken.kind != Token.EOF) {
			throw new Exception("Token inesperado: " + this.tokenQueue.peek().spelling + "(" + this.tokenQueue.peek().line + ", " + this.tokenQueue.peek().column + ")");
		}
		else {
			System.out.println("Parser completed");
		}
	}
	
	private void parseProgram() throws Exception {
		accept(Token.PROGRAM);
		acceptIt();
		accept(Token.IDENTIFIER);
		acceptIt();
		accept(Token.SEMICOLON);
		acceptIt();
		parseDeclaration();
		accept(Token.BEGIN);
		acceptIt();
//		parseCommand();
		accept(Token.END);
		acceptIt();
		accept(Token.DOT);
		acceptIt();
	}
	
	//
	private void parseDeclaration() throws Exception {
		while (this.currentToken.kind == Token.VAR) {
			parseSingleDeclaration();
		}
	}
	
	private void parseSingleDeclaration() throws Exception {
		accept(Token.VAR);
		acceptIt();
//		lista de ids
		accept(Token.COLON);
		acceptIt();
		parseType();
		accept(Token.SEMICOLON);
		acceptIt();
	}
	
	private void parseType() throws Exception {
		if (currentToken.kind == Token.INTEGER) {
			accept(Token.INTEGER);
			acceptIt();
		} else if (currentToken.kind == Token.REAL) {
			accept(Token.REAL);
			acceptIt();
		} else if (currentToken.kind == Token.BOOLEAN) {
			accept(Token.BOOLEAN);
			acceptIt();
		} else if (currentToken.kind == Token.ARRAY) {
			parseComposedType();
		} else {
			throw new Exception("Token inesperado: " + this.tokenQueue.peek().spelling + "(" + this.tokenQueue.peek().line + ", " + this.tokenQueue.peek().column + ")");
		}
	}
	
	private void parseComposedType() throws Exception {
		accept(Token.ARRAY);
		acceptIt();
		accept(Token.LBRACKET);
		acceptIt();
		parseLiteral();
		accept(Token.DOTDOT);
		acceptIt();
		parseLiteral();
		accept(Token.RBRACKET);
		acceptIt();
		accept(Token.OF);
		acceptIt();
		parseType();
	}
	
	private void parseLiteral() throws Exception {
		if (currentToken.kind == Token.BOOLLIT) {
			accept(Token.BOOLLIT);
			acceptIt();
		} else if (currentToken.kind == Token.INTLIT) {
			accept(Token.INTLIT);
			acceptIt();
		} else if (currentToken.kind == Token.FLOATLIT) {
			accept(Token.FLOATLIT);
			acceptIt();
		} else {
			throw new Exception("Token inesperado: " + this.tokenQueue.peek().spelling + "(" + this.tokenQueue.peek().line + ", " + this.tokenQueue.peek().column + ")");
		}
	}
	
	private void parseCommand() throws Exception {
		accept(Token.IDENTIFIER);
		acceptIt();
		parseExpression();
	}
	
	private void parseSingleCommand() throws Exception {
		
	}
	
	private void parseExpression() throws Exception {
		
	}
	
	/* M�todo para implementar a chamada de erro caso exista um erro de sintaxe */
	private static class syntaticError extends Exception {
		syntaticError(String message){
			super(message);
		}
	}
	

}
