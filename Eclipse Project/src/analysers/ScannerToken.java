package analysers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.FileInputStream;


public class ScannerToken {
  private FileInputStream file;
  private InputStreamReader fopen;
  private PushbackReader reader;
  private int row = 1, column =0;

  private Character actualChar;
  private byte actualCode;
  private StringBuffer actualSpelling;

  //Constructor will receive path of the code and try to open it
  public ScannerToken(String path) throws IOException {
	  try {
		  this.file = new FileInputStream(path);
		  this.fopen = new InputStreamReader(this.file);
		  this.reader = new PushbackReader(this.fopen);
		  this.actualChar = getChar();
	  } catch(IOException e) {
		  System.out.println(e.getMessage());
	  }
  }

  //Method that will consume a character and returns next one
  public Character getChar() throws IOException{
	  Character c = null;
	  int i = (int)this.reader.read();
	  if(i != -1) {
		  c = (char)i;
	  }
	  column++;
	  return c;
  }

  //Method that verifies if current char is null. If so, returns false. If not, returns the char back
  public boolean compare(char c) {
	  if(actualChar == null) {
		  return false;
	  }else {
		  return actualChar == c;
	  }
  }
  
  //Separator control
  private void lookForSeparator() throws IOException{
	  switch(actualChar) {
	  case ' ': case '\n': case (char)9:
		  if(compare('\n')) {//If \n, go to next row - EOL
			  row++;
			  column = 0;
		  }
	  //takeIt();
	  actualChar = getChar();
	  break;
	  }
  }

  //Lookahead Method
  private boolean lookahead(int c) throws IOException{
	  int nextchar = this.reader.read();
	  if(nextchar == -1) { //If EOF, there is no lookahead
		  return false;
	  } if(nextchar == c) { //If lookahead is EOL
		  this.reader.unread(nextchar);
		  return true;
	  } else {
		  this.reader.unread(nextchar);
	  }
	  return false;
  }

  //Checks if is digit
  private boolean isDigit(Character c) {
	  if(c == null) { //if empty, returns null
		  return false;
	  }

	  int ascii = (int)c;
	  if(ascii >=48 && ascii <= 57) { //Values between 0 - 9
		  return true;
	  }
	  //if is not a digit,
	  return false;
  }

  //Check if is letter
  private boolean isLetter(Character c) {
	  if(c==null) {
		  return false;
	  }
	  int ascii = (int)c;
	  if((ascii >= 'a' && ascii <= 'z') || (ascii >= 'A' && ascii <= 'Z')) {
		  return true;
	  }
	  return false;
  }
  
  //Accept a token not matter what it is
  private void takeIt() throws IOException{
	  this.actualSpelling.append(actualChar);
	  this.actualChar = getChar();
	  //this.column++;
  }
  
  //Method that returns a token
  private byte tokenScanner () throws IOException{
	  switch(actualChar) {
	  //Lower case letters
	  case 'a': case 'b': case 'c': case 'd': case 'e': case 'f': case 'g': case 'h': case 'i':	  
	  case 'j':  case 'k':  case 'l':  case 'm': case 'n': case 'o': case 'p': case 'q': case 'r':
	  case 's': case 't': case 'u': case 'v': case 'w': case 'x': case 'y': case 'z':
	  //Upper case letters
	  case 'A': case 'B': case 'C': case 'D': case 'E': case 'F': case 'G': case 'H': case 'I':
	  case 'J': case 'K': case 'L': case 'M': case 'N': case 'O': case 'P': case 'Q': case 'R':
	  case 'S': case 'T': case 'U': case 'V': case 'W': case 'X': case 'Y': case 'Z':
		  takeIt();
		  while(isLetter(actualChar) || isDigit(actualChar)) {
			  takeIt();
		  }
		  return Token.IDENTIFIER;
	  
	  //Numbers acceptance
	  case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8':
	  case '9':
		  takeIt();
		  while(isDigit(actualChar)) {
			  takeIt();
			  }
			  //If its int-lit (1--2)
			  if(compare('.')) {
				  if (lookahead('.')) {
					  return Token.INTLIT;
				  }
				  takeIt();
				  //If its float-lit (a.b || a. || .a)
				  if(isDigit(actualChar)) {
					  takeIt();
					  while(isDigit(actualChar)) {
						  takeIt();
					  }
						  return Token.FLOATLIT;
				  }
					  return Token.FLOATLIT;
			  }
			  //If not, its int-lit kind (1--0)
			  else {
				  return Token.INTLIT;
			  }
	  //If starts with '.', see if it's double dot (..), or float-lit
	  case '.':
		  takeIt();
		  //For float-lit
		  if (isDigit(actualChar)) {
			  takeIt();
			  while(isDigit(actualChar)) {
				  takeIt();
			  }
		  return Token.FLOATLIT;
		  }
		  else if(compare('.')) {
			  takeIt();
			  return Token.DOTDOT;
		  }
		  else {
			  return Token.DOT;
		  }
	  //Case sum operation
	  case '+': 
		  takeIt();
		  return Token.PLUS;
	 
	  //Case subtraction
	  case '-':
		  takeIt();
		  return Token.MINUS;
		  
	  //Multiplication
	  case '*':
		  takeIt();
		  return Token.TIMES;
		  
	  //Division
	  case '/':
		  takeIt();
		  return Token.SLASH;
		  
	  //Equality
	  case '=':
		  takeIt();
		  return Token.EQUAL;
		  
	  //Greater
	  case '>':
		  takeIt();
		  //Bigger equal
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
		  
	  default:
		  takeIt();
		  return Token.ERROR;
	  }
  } 
  
  public Token scan() throws IOException{
	  if(currentNull()) {
		  System.exit(0);
		  return new Token (Token.EOF, "EOF", this.row, this.column);
		  }
	  while (compare (' ') || compare ('\n') || compare ('\t') || compare((char)9)) {
		  lookForSeparator();
		  if(currentNull()) {
			  return new Token(Token.EOF, "EOF", this.row, this.column);
		  }
	  }
  	  actualSpelling = new StringBuffer("");
	  actualCode = tokenScanner();
	  
	  if(actualCode == Token.EOF) {
		  System.out.println("Erro: Não existe o arquivo");
	  }
	  
	  if(actualCode == Token.ERROR) {
		  System.out.println("Erro na linha: " + row + ", coluna: " + (column-this.actualSpelling.toString().length()) + ". Tipo de identificador" + actualSpelling.toString() + "não aceito.");
	  }
	  
	  return new Token(actualCode, actualSpelling.toString(), row, column-this.actualSpelling.toString().length()); 
  }
  
  private boolean currentNull() {
	  return actualChar == null;
  }
  
}