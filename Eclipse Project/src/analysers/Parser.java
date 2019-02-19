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
//		parseDeclaration();
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
		accept(Token.VAR);
		acceptIt();
		if(this.currentToken.kind == Token.COMMA) {
			acceptIt();
						
		}
	}
	
	private void parseSingleDeclaration() {
		
	}
	
	private void parseCommand() throws Exception {
		accept(Token.IDENTIFIER);
		acceptIt();
		parseExpression();
	}
	
	private void parseSingleCommand() {
		
	}
	
	private void parseExpression() throws Exception{
		
	}
	
	/* M�todo para implementar a chamada de erro caso exista um erro de sintaxe */
	private static class syntaticError extends Exception {
		syntaticError(String message){
			super(message);
		}
	}
	

}
