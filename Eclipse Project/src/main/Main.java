/*
 * 	PARA TESTAR, O C�DIGO DEVE ESTAR ESCRITO NUM ARQUIVO COMO "d:\\code.txt" 
 */

package main;

import java.util.Queue;

import analysers.Scanner;
import analysers.Token;


public class Main {
	
	private static String filePath = "/home/alisson/univasf/compilador/Result/code.txt";
	private static Scanner scanner;

	public static void main (String[] args) {

		try {
			scanner = new Scanner(filePath);
			
			Queue<Token> tokenQueue = scanner.run();
			
			for (Token token : tokenQueue) {
				System.out.println(token.kind + " => " + token.spelling);
			}

		} catch (Exception e) {
	        System.out.printf(e.getMessage());
	    }
		
	}

}
