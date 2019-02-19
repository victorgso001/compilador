/*
 * 	PARA TESTAR, O C�DIGO DEVE ESTAR ESCRITO NUM ARQUIVO COMO "d:\\code.txt" 
 */

package main;

import java.util.Queue;

import analysers.Scanner;
import analysers.Parser;
import analysers.Token;


public class Main {
	
	private static String filePath = "C:\\Users\\Victor\\source\\repos\\victorgso001\\compilador\\Result\\code.txt";
	private static Scanner scanner;
	private static Parser parser;

	public static void main (String[] args) {

		try {
			scanner = new Scanner(filePath);
			Queue<Token> tokenQueue = scanner.run();
			
			parser = new Parser(tokenQueue);
			parser.run();
			
//			for (Token token : tokenQueue) {
//				System.out.println(token.kind + " => " + token.spelling + "(" + token.line + "," + token.column + ")");
//			}

		} catch (Exception e) {
	        System.out.printf(e.getMessage());
	    }
		
	}

}
