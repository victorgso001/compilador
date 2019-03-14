package analysers;

import java.util.Queue;
import ast.*;

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
			throw new Exception("Token inesperado: " + this.tokenQueue.peek().spelling + "(Linha: " + this.tokenQueue.peek().line + ", Coluna: " + this.tokenQueue.peek().column + ")");
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
			throw new Exception("Token inesperado: " + this.tokenQueue.peek().spelling + "(Linha " + this.tokenQueue.peek().line + ", Coluna " + this.tokenQueue.peek().column + ")");
		}
		else {
			System.out.println("Parser completed");
		}
	}
	
	private Program parseProgram() throws Exception {
		Program Program;
		accept(Token.PROGRAM);
		acceptIt();
		accept(Token.IDENTIFIER);
		Identifier AI = new Identifier(currentToken.spelling);
		acceptIt();
		accept(Token.SEMICOLON);
		acceptIt();
		Declaration AD = parseDeclaration();
		accept(Token.BEGIN);
		acceptIt();
		CommandsList AC = parseCommand();
		Body AB = new Body(AD, AC);
		accept(Token.END);
		acceptIt();
		accept(Token.DOT);
		acceptIt();
		Program = new Program(AI, AB);
		return Program;
	}

	private DeclarationList parseDeclaration() throws Exception {
		DeclarationList DL1 = null, DL2;
		Declaration DTemp;

		if (this.currentToken.kind == Token.VAR) {
			DL1 = parseSingleDeclaration();
			while (this.currentToken.kind == Token.VAR) {
				DL2 = parseSingleDeclaration();
				DL1 = new DeclarationList(DL1, DL2);
			}
		}
		return DL1;
	}
	
	private Declaration parseSingleDeclaration() throws Exception {
		Declaration Declaration = null;
		IDsList IdsList = null;
		SimpleType Type = null;

		accept(Token.VAR);
		acceptIt();
		IdsList = parseIdList();
		accept(Token.COLON);
		acceptIt();
		Type = parseType();
		accept(Token.SEMICOLON);
		acceptIt();

		return new Declaration(this.currentToken.spelling, AType);
	}
	
	private IDsList parseIdList() throws Exception {
		IDsList IDL1 = null, IDL2;
		
		accept(Token.IDENTIFIER);
		Identifier IDL1 = new Identifier(this.currentToken.spelling);
		acceptIt();
		while(this.currentToken.kind == Token.COMMA) {
			acceptIt();
			accept(Token.IDENTIFIER);
			IDL2 = new Identifier(this.currentToken.spelling);
			IDL1 = new IDsList(IDL1, IDL2);
			acceptIt();
		}

		return IDL1;
	}
	
	private Abstract_Type parseType() throws Exception {
		Abstract_Type Type = null;
		if (currentToken.kind == Token.INTEGER) {
			accept(Token.INTEGER);
			Type = new SimpleType(this.currentToken.spelling);
			acceptIt();
		} else if (currentToken.kind == Token.REAL) {
			accept(Token.REAL);
			Type = new SimpleType(this.currentToken.spelling);
			acceptIt();
		} else if (currentToken.kind == Token.BOOLEAN) {
			accept(Token.BOOLEAN);
			Type = new SimpleType(this.currentToken.spelling);
			acceptIt();
		} else if (currentToken.kind == Token.ARRAY) {
			Type = parseComposedType();
		} else {
			throw new Exception("Token inesperado: " + this.tokenQueue.peek().spelling + "(Linha " + this.tokenQueue.peek().line + ", Coluna " + this.tokenQueue.peek().column + ")");
		}

		return Type;
	}
	
	private AggregateType parseComposedType() throws Exception {
		Vname VN1, VN2;
		SimpleType ST1;
		accept(Token.ARRAY);
		acceptIt();
		accept(Token.LBRACKET);
		acceptIt();
		VN1 = parseLiteral();
		accept(Token.DOTDOT);
		acceptIt();
		VN2 = parseLiteral();
		accept(Token.RBRACKET);
		acceptIt();
		accept(Token.OF);
		acceptIt();
		ST1 = parseType();

		return new AggregateType(VN1, VN2, ST1);
	}
	
	private Vname parseLiteral() throws Exception {
		Vname VN;
		if (currentToken.kind == Token.BOOLLIT) {
			accept(Token.BOOLLIT);
			VN = new Vname(this.currentToken.spelling);
			acceptIt();
		} else if (currentToken.kind == Token.INTLIT) {
			accept(Token.INTLIT);
			VN = new Vname(this.currentToken.spelling);
			acceptIt();
		} else {
			accept(Token.FLOATLIT);
			VN = new Vname(this.currentToken.spelling);
			acceptIt();
		}
		return VN;
	}
	
	private CommandsList parseCommand() throws Exception {
		while (this.currentToken.kind == Token.IDENTIFIER ||
				this.currentToken.kind == Token.IF ||
				this.currentToken.kind == Token.WHILE ||
				this.currentToken.kind == Token.BEGIN) {
			parseSingleCommand();
			accept(Token.SEMICOLON);
			acceptIt();
		}
	}
	
	private void parseSingleCommand() throws Exception {
		if (currentToken.kind == Token.IDENTIFIER) {
			parseAttribuition();
		} else if (currentToken.kind == Token.IF) {
			parseConditional();
		} else if (currentToken.kind == Token.WHILE) {
			parseIteration();
		} else {
			parseComposedCommand();
		}
	}
	
	private void parseAttribuition() throws Exception {
		accept(Token.IDENTIFIER);
		acceptIt();
		if(currentToken.kind == Token.LBRACKET) {
			parseArrayPosition();			
		}
		accept(Token.ASSIGN);
		acceptIt();
		parseExpression();
	}
	
	private void parseConditional() throws Exception {
		accept(Token.IF);
		acceptIt();
		parseExpression();
		accept(Token.THEN);
		acceptIt();
		parseSingleCommand();
		if(currentToken.kind == Token.ELSE) {
			acceptIt();
			parseSingleCommand();			
		}
	}
	
	private void parseIteration() throws Exception {
		accept(Token.WHILE);
		acceptIt();
		parseExpression();
		accept(Token.DO);
		acceptIt();
		parseSingleCommand();
	}
	
	private void parseComposedCommand() throws Exception {
		accept(Token.BEGIN);
		acceptIt();
		parseCommand();
		accept(Token.END);
		acceptIt();
	}
	
	private void parseArrayPosition() throws Exception {
		acceptIt();
		parseExpression();
		accept(Token.RBRACKET);
		acceptIt();
	}
	
	private void parseExpression() throws Exception {
		parseSingleExpression();
		if(isOpRel()) {
			acceptIt();
			parseSingleExpression();
		}
	}
	
	private boolean isOpRel() throws Exception {
		if (this.currentToken.kind == Token.GREATERTHAN) {
			accept(Token.GREATERTHAN);
			return true;
		} else if (this.currentToken.kind == Token.LESSTHAN) {
			accept(Token.LESSTHAN);
			return true;
		} else if (this.currentToken.kind == Token.GREATEREQUAL) {
			accept(Token.GREATEREQUAL);
			return true;
		} else if (this.currentToken.kind == Token.LESSEQUAL) {
			accept(Token.LESSEQUAL);
			return true;
		} else if (this.currentToken.kind == Token.EQUAL) {
			accept(Token.EQUAL);
			return true;
		} else if (this.currentToken.kind == Token.DIFFERENT) {
			accept(Token.DIFFERENT);
			return true;
		} else {
			return false;
		}
	}
	
	private void parseSingleExpression() throws Exception {
		parseTerm();
		if(isOpAd()) {
			acceptIt();
			parseTerm();
		}
	}
	
	private boolean isOpAd() throws Exception {
		if (this.currentToken.kind == Token.PLUS) {
			accept(Token.PLUS);
			return true;
		}else if (this.currentToken.kind == Token.MINUS) {
			accept(Token.MINUS);
			return true;
		} else if (this.currentToken.kind == Token.OR || this.currentToken.kind == Token.OPAD) {
			accept(Token.OR);
			return true;
		} else {
			return false;
		}
	}
	
	private void parseTerm() throws Exception {
		parseFactor();
		if (isOpMul()) {
			acceptIt();
			parseFactor();
		}
	}
	
	private boolean isOpMul() throws Exception {
		if (this.currentToken.kind == Token.TIMES) {
			accept(Token.TIMES);
			return true;
		} else if (this.currentToken.kind == Token.SLASH) {
			accept(Token.SLASH);
			return true;
		} else if (this.currentToken.kind == Token.AND) {
			accept(Token.AND);
			return true;
		} else {
			return false;
		}
	}
	
	private void parseFactor() throws Exception {
		if (this.currentToken.kind == Token.IDENTIFIER) {
			acceptIt();
			if (this.currentToken.kind == Token.LBRACKET) {
				parseArrayPosition();			
			}
		} else if (this.currentToken.kind == Token.INTLIT ||
			this.currentToken.kind == Token.BOOLLIT ||
			this.currentToken.kind == Token.FLOATLIT) {
			parseLiteral();
		} else {
			parseExpression();
		}
	}
}
