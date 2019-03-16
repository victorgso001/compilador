package analysers;

import java.util.Queue;

import com.sun.javafx.fxml.expression.Expression;

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
		DeclarationList AD = parseDeclaration();
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
		Declaration D1 = null, D2;

		if (this.currentToken.kind == Token.VAR) {
			D1 = parseSingleDeclaration();
			while (this.currentToken.kind == Token.VAR) {
				D2 = parseSingleDeclaration();
				DL1 = new DeclarationList(DL1, D2);
			}
		}
		DL2 = new DeclarationList(DL1, D1);
		return DL2;
	}
	
	private Declaration parseSingleDeclaration() throws Exception {
		Declaration Declaration = null;
		IDsList IdsList = null;
		SimpleType Type = null;

		accept(Token.VAR);
		acceptIt();
		IdsList = parseIdList();
		Identifier ID = new Identifier(this.currentToken.spelling);
		accept(Token.COLON);
		acceptIt();
		Type = (SimpleType) parseType();
		accept(Token.SEMICOLON);
		acceptIt();
		
		//Aqui acho que a gente vai precisar fazer uma pilha e ir descarregando de IDsList e os Types
		return new Declaration(ID, Type);
	}
	
	private IDsList parseIdList() throws Exception {
		IDsList IDL1 = null, IDL2;
		Identifier ID1, ID2;
		
		accept(Token.IDENTIFIER);
		ID1 = new Identifier(this.currentToken.spelling);
		acceptIt();
		while(this.currentToken.kind == Token.COMMA) {
			acceptIt();
			accept(Token.IDENTIFIER);
			ID2 = new Identifier(this.currentToken.spelling);
			IDL1 = new IDsList(IDL1, ID2);
			acceptIt();
		}
		IDL2 = new IDsList(IDL1, ID1);
		return IDL2;
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
		ST1 = (SimpleType) parseType();

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
		ComposedCommand CC1 = null, CC2;
		CommandsList CL1 = null, CL2;
		if (this.currentToken.kind == Token.IDENTIFIER ||
				this.currentToken.kind == Token.IF ||
				this.currentToken.kind == Token.WHILE ||
				this.currentToken.kind == Token.BEGIN) {
			CC1 = parseSingleCommand();
			while (this.currentToken.kind == Token.IDENTIFIER ||
					this.currentToken.kind == Token.IF ||
					this.currentToken.kind == Token.WHILE ||
					this.currentToken.kind == Token.BEGIN) {
				CC2 = parseSingleCommand();
				CL1 = new CommandsList(CL1, CC2);
				accept(Token.SEMICOLON);
				acceptIt();
			}
		}
		CL2 = new CommandsList (CL1, CC1);
		return CL2;
		
	}
	
	private ComposedCommand parseSingleCommand() throws Exception {
		ComposedCommand CC = null;
		if (currentToken.kind == Token.IDENTIFIER) {
			parseAttribuition();
		} else if (currentToken.kind == Token.IF) {
			parseConditional();
		} else if (currentToken.kind == Token.WHILE) {
			parseIteration();
		} else {
			CC = new ComposedCommand (parseComposedCommand());
		}
		
		return CC;
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
	
	private IfCommand parseConditional() throws Exception {
		Abstract_Expression AE1;
		ComposedCommand CC1, CC2 = null;
		accept(Token.IF);
		acceptIt();
		 AE1 = parseExpression();
		accept(Token.THEN);
		acceptIt();
		CC1 = parseSingleCommand();
		if(currentToken.kind == Token.ELSE) {
			acceptIt();
			CC2 = parseSingleCommand();			
		}
		
		return new IfCommand(AE1, CC1, CC2);
	}
	
	private void parseIteration() throws Exception {
		accept(Token.WHILE);
		acceptIt();
		parseExpression();
		accept(Token.DO);
		acceptIt();
		parseSingleCommand();
	}
	
	private CommandsList parseComposedCommand() throws Exception {
		CommandsList CC;
		accept(Token.BEGIN);
		acceptIt();
		CC = parseCommand();
		accept(Token.END);
		acceptIt();
		return CC;
	}
	
	private void parseArrayPosition() throws Exception {
		acceptIt();
		parseExpression();
		accept(Token.RBRACKET);
		acceptIt();
	}
	
	private MultipleExpression parseExpression() throws Exception {
		SimpleExpression SE1, SE2 = null;
		SE1 = parseSingleExpression();
		if(isOpRel()) {
			acceptIt();
			SE2 = parseSingleExpression();
		}
		return new MultipleExpression(SE1, SE2);
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
	
	private SimpleExpression parseSingleExpression() throws Exception {
		Term T1, T2 = null;
		AdOperator AO = null;
		T1 = parseTerm();
		if(isOpAd()) {
			AO = new AdOperator(this.currentToken.spelling);
			acceptIt();
			T2 = parseTerm();
		}
		return new SimpleExpression(T1, AO, T2);
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
	
	private Term parseTerm() throws Exception {
		Factor F1, F2 = null;
		MulOperator M1 = null;
		F1 = parseFactor();
		if (isOpMul()) {
			M1 = new MulOperator(this.currentToken.spelling);
			acceptIt();
			F2 = parseFactor();
		}
		
		return new Term(F1, M1, F2);
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
	
	private Factor parseFactor() throws Exception {
		Identifier ID1 = null;
		Vname VN1 = null;
		MultipleExpression ME1 = null;
		if (this.currentToken.kind == Token.IDENTIFIER) {
			ID1 = new Identifier(this.currentToken.spelling);
			acceptIt();
			if (this.currentToken.kind == Token.LBRACKET) {
				parseArrayPosition();			
			}
		} else if (this.currentToken.kind == Token.INTLIT ||
			this.currentToken.kind == Token.BOOLLIT ||
			this.currentToken.kind == Token.FLOATLIT) {
			VN1 = new Vname(this.currentToken.spelling);
			parseLiteral();
		} else {
			ME1 = parseExpression();
		}
		
		return new Factor(ID1, VN1, ME1);
	}
}
