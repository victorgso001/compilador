public class Token {
  public byte kind;
  public String spelling;
  public int line, column;

  public Token (byte kind, String spelling, int line, int column) {
    this.kind = kind;
    this.spelling = spelling;
    this.line = line;
    this.column = column;

    //Check which identifier to use, gives right identifier value
    if(this.kind == ASSIGN){
      for(byte x = BEGIN; x <= COMMA; x++){
        if(this.spelling.equals(SPELLINGS[x])){
            if(this.spelling.compareTo("true") == 0 ||
               this.spelling.compareTo("false") == 0){ //Verifica se o identificador é boolean
                 this.kind = BOOLLIT;
               }else if(this.spelling.compareTo("and")==0){ //Verifica se o identificador é de op-mul
                 this.kind = OPMUL;
               }else if(this.spelling.compareTo("or")==0){ //Verifica se o identificador é de op-ad
                 this.kind = OPAD;
               }else {
                 this.kind = x;
               }

        }
      }
    }
  }

  public final static byte
    ASSIGN = 0,
    BEGIN = 1,
    END = 2,
    IF = 3,
    THEN = 4,
    ELSE = 5,
    LPAREN = 6,
    RPAREN = 7,
    VAR = 8,
    COLON = 9,
    INTLIT = 10,
    DOT = 11,
    DOTDOT = 12,
    SEMICOLON = 13,
    FLOATLIT = 14,
    ID = 15,
    WHILE = 16,
    DO = 17,
    PLUS = 18,
    MINUS = 19,
    OR = 20,
    TIMES = 21,
    SLASH = 22,
    AND = 23,
    GREATERTHAN = 24,
    LESSTHAN = 25,
    GREATEREQUAL = 26,
    LESSEQUAL = 27,
    EQUAL = 28,
    DIFFERENT = 29,
    ERRORTOKEN = 30,
    LBRACKET = 31,
    RBRACKET = 32,
    INTEGER = 33,
    REAL = 34,
    BOOLEAN = 35,
    TRUE = 36,
    FALSE = 37,
    OF = 38,
    ARRAY = 39,
    COMMA = 40,
    EOF = 41,
    ERROR = 42,
    BOOLLIT = 43,
    OPMUL = 44,
    OPAD = 45;

}

public final static String[] SPELLINGS = {"ASSIGN", "BEGIN", "END", "IF", "THEN", "ELSE", "LPAREN", "RPAREN", "VAR", "COLON",
                                          "INTLIT", "DOT", "DOTDOT", "SEMICOLON", "FLOATLIT", "ID", "WHILE", "DO", "PLUS",
                                          "PLUS", "MINUS", "OR", "TIMES", "SLASH", "AND", "GREATERTHAN", "LESSTHAN",
                                          "GREATEREQUAL", "LESSEQUAL", "EQUAL", "DIFFERENT", "ERRORTOKEN", "LBRACKET",
                                          "RBRACKET", "INTEGER", "REAL", "BOOLEAN", "TRUE", "FALSE", "OF", "ARRAY", "COMMA",
                                          "EOF", "ERROR"};
