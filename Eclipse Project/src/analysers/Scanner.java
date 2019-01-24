package analysers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.EOFException;
import java.io.FileInputStream;


public class Scanner {
  private FileInputStream file;
  private InputStreamReader fopen;
  private PushbackReader reader;
  private int line = 1, column =0;
  
  private Character actualChar;
  private byte actualCode;
  private StringBuffer actualSpelling;
  
  //Constructor will receive path of the code and try to open it
  public Scanner(String path) {
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
  
  //Separator control
  private void lookForSeparator() throws IOException{
	  switch(actualChar) {
	  case ' ': case '\n': case (char)9:
		  if(compare('\n')) {//If \n, go to next line - EOL
			  line++;
			  column = 0;
		  }
	  actualChar = getChar();
	  break;
	  }
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
	  return false
  }
  
  
}