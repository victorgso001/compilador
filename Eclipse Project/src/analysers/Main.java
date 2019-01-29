/*
 * 	PARA TESTAR, O CÓDIGO DEVE ESTAR ESCRITO NUM ARQUIVO COMO "d:\\code.txt" 
 */

package analysers;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.io.File;
import java.util.Scanner;


public class Main {
	
	private static ScannerToken scan;

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		
		String path = new String();
		
		try {
			FileReader arq = new FileReader("e:\\code.txt");
			File file = new File("e:\\code.txt");
			Scanner input = new Scanner(file);
			BufferedReader lerArq = new BufferedReader(arq);
			scan = new ScannerToken("e:\\code.txt");
			
			String text = lerArq.readLine();			
			path = text + "\n";
			
			while(input.hasNext()) {
//				path = path + text + "\n";
				Token token = scan.scan();
				System.out.println("Column : " + token.column + " Line: " + token.line + " Spelling: " + token.spelling + " Code: " + token.kind);
			}

		}catch (IOException e) {
	        System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
	    }
		
	}

}
