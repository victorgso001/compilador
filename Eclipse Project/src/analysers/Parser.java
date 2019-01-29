package analysers;

import java.util.Queue;

public class Parser {
	private Token currentToken;
	private Queue<Token> tokenQueue;
	
	public Parser(Queue<Token> tokenQueue) {
		this.tokenQueue = tokenQueue;
	}
	
	private void accept(byte expectedKind) throws Exception {
		if (this.currentToken.kind == expectedKind) {
			this.currentToken = this.tokenQueue.peek();
		}
		else {
			throw new Exception("Token inesperado: " + this.tokenQueue.peek().spelling + "(" + this.tokenQueue.peek().line + ", " + this.tokenQueue.peek().column + ")");
		}
	}
	
	private void acceptIt() {
		this.currentToken = this.tokenQueue.poll();
	}
	
	public void run() throws Exception {
		acceptIt();
		
		parseProgram();
		
		if (currentToken.kind != Token.EOF) {
			throw new Exception("Token inesperado: " + this.tokenQueue.peek().spelling + "(" + this.tokenQueue.peek().line + ", " + this.tokenQueue.peek().column + ")");
		}
	}
	
	private void parseProgram() throws Exception {
		accept(Token.PROGRAM);
		acceptIt();
		accept(Token.ID);
		acceptIt();
		accept(Token.SEMICOLON);
		acceptIt();
		parseDeclaration();
		accept(Token.BEGIN);
		acceptIt();
		parseCommand();
		accept(Token.END);
		acceptIt();
		accept(Token.DOT);
		acceptIt();
	}
	
	private void parseDeclaration() {
		
	}
	
	private void parseSingleDeclaration() {
		
	}
	
	private void parseCommand() {
		
	}
	
	private void parseSingleCommand() {
		
	}
}
