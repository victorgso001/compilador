package analysers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.util.LinkedList;
import java.util.Queue;
import java.io.FileInputStream;


public class Scanner {
	private FileInputStream file;
	private InputStreamReader fOpen;
	private PushbackReader reader;

	private int row = 1;
	private int column = 0;
	private byte actualCode;

	private Character actualChar;
	private StringBuffer actualSpelling;

	// Construtor verifica se o arquivo pode ser aberto sem problemas
	public Scanner(String path) throws Exception {

		try {
			
			this.file = new FileInputStream(path);
			this.fOpen = new InputStreamReader(this.file);
			this.reader = new PushbackReader(this.fOpen);
			this.actualChar = getChar();
		} catch (IOException e) {
			
			System.out.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
			throw new Exception("COMPILAÇÃO FINALIZADA COM ERRO");
		}
	}

	// Function that reads the file and returns:
	// an Token ArrayList or a I/O exception
	// or a exception for a unexpected char
	public Queue<Token> run() throws Exception {

		Queue<Token> tokenQueue = new LinkedList<Token>();
		Token currentToken;

		try {
			
			do {
				
				currentToken = scan();
				if (currentToken.kind != Token.SEPARATOR) {
					tokenQueue.add(currentToken);					
				}
			} while (currentToken.kind != Token.EOF);
			
			return tokenQueue;
		} catch (IOException e) {
			
			System.out.printf(e.getMessage());
			throw new Exception("COMPILAÇÃO FINALIZADA COM ERRO");
		} catch (Exception e) {

			System.out.printf(e.getMessage());
			throw new Exception("COMPILAÇÃO FINALIZADA COM ERRO");
		}
	}

	// Method that reads the next character. If it reaches the EOF it returns null
	private Character getChar() throws IOException {

		int readChar = this.reader.read();

		if (readChar == -1) {
			return null;
		}
		
		this.column++;
		
		return (char) readChar;
	}

	// Method that verifies if current char is null. If so, returns false. If not, returns the comparison
	private boolean compare(char c) {

		if (this.actualChar == null) {
			return false;
		}
		return this.actualChar == c;
	}

	// Lookahead Method
	private boolean lookahead(int c) throws IOException{
		int nextChar = this.reader.read();
		if (nextChar == -1) {
			return false;
		}
		if (nextChar == c) {
			this.reader.unread(nextChar);
			return true;
		}
		this.reader.unread(nextChar);
		return false;
	}

	//Checks if is digit
	private boolean isDigit(Character c) {
		if (c == null) {
			return false;
		}

		if(c >= '0' && c <= '9') {
			return true;
		}

		return false;
	}

	//Check if is letter
	private boolean isLetter(Character c) {
		if (c == null) {
			return false;
		}

		if(Character.toLowerCase(c) >= 'a' && Character.toLowerCase(c) <= 'z') {
			return true;
		}
		return false;
	}

	//Accept a token not matter what it is
	private void takeIt() throws IOException {
		this.actualSpelling.append(this.actualChar);
		this.actualChar = getChar();
	}

	// Method that returns a token
	private byte tokenScanner () throws IOException {
		
		// Lower and Upper case letters
		if (Character.toLowerCase(this.actualChar) >= 'a' && Character.toLowerCase(this.actualChar) <= 'z') {

			takeIt();
			while (isLetter(actualChar) || isDigit(actualChar)) {
				takeIt();
			}
			return Token.IDENTIFIER;
		}
		
		// Numbers
		if (this.actualChar >= '1' && this.actualChar <= '9') {

			takeIt();
			while (isDigit(this.actualChar)) {
				takeIt();
			}
			
			if (!compare('.')) {
				return Token.INTLIT;
			}
			
			if (lookahead('.')) {
				return Token.INTLIT;
			}
			
			takeIt();
			if (isDigit(actualChar)) {
				
				takeIt();
				while (isDigit(actualChar)) {
					takeIt();
				}
				return Token.FLOATLIT;
			}
			return Token.FLOATLIT;
		}
		

		switch(actualChar) {
		
			// Special '.' starting
			case '.':

				takeIt();
				if (isDigit(actualChar)) {

					takeIt();
					while(isDigit(actualChar)) {
						takeIt();
					}
					return Token.FLOATLIT;
				} else if(compare('.')) {

					takeIt();
					return Token.DOTDOT;
				} else {

					return Token.DOT;
				}

			// Case SUM operation
			case '+': 
				takeIt();
				return Token.PLUS;
	
			// Case SUB operation
			case '-':
				takeIt();
				return Token.MINUS;
	
			// Case MULT operation
			case '*':
				takeIt();
				return Token.TIMES;
	
			// Case DIV operation
			case '/':
				takeIt();
				return Token.SLASH;
	
			// Case EQ operation
			case '=':
				takeIt();
				return Token.EQUAL;
	
			//Greater
			case '>':
				takeIt();
				if (compare('=')) {
					takeIt();
					return Token.GREATEREQUAL;
				} else {
					return Token.GREATERTHAN;	
				}
	
			//Lesser
			case '<':
				takeIt();
				if (compare('=')) {
					takeIt();
					return Token.LESSEQUAL;
				} else {
					return Token.LESSTHAN;	
				}
	
			case ':':
				takeIt();
				if(compare('=')) {
					takeIt();
					return Token.ASSIGN;
				} else {
					return Token.COLON;
				}


		case ';':
			takeIt();
			return Token.SEMICOLON;

		case ',':
			takeIt();
			return Token.COMMA;

		case '(':
			takeIt();
			return Token.LPAREN;

		case ')':
			takeIt();
			return Token.RPAREN;
		
		case '[':
			takeIt();
			return Token.LBRACKET;

		case ']':
			takeIt();
			return Token.RBRACKET;

		// Case NEWLINE Separator		
		case '\n':
			takeIt();
			this.row++;
			this.column = 0;
			return Token.SEPARATOR;

		// Case SPACE Separator		
		case ' ':
			takeIt();
			return Token.SEPARATOR;

		// Case HORIZONTAL_TAB Separator
		case '\t':
			takeIt();
			return Token.SEPARATOR;	
			
		default:
			takeIt();
			return Token.ERROR;
		}
	} 

	public Token scan() throws Exception {
		
		if (this.actualChar == null) {
			return new Token (Token.EOF, "EOF", this.row, this.column);
		}

		this.actualSpelling = new StringBuffer("");
		this.actualCode = tokenScanner();

		if (this.actualCode == Token.ERROR) {
			throw new Exception("Erro na linha: " + this.row + ", coluna: " + (this.column - this.actualSpelling.toString().length()) + ". Tipo de identificador " + this.actualSpelling.toString() + " n�o aceito.");
		}

		return new Token(this.actualCode, this.actualSpelling.toString(), this.row, this.column - this.actualSpelling.toString().length()); 
	}
}