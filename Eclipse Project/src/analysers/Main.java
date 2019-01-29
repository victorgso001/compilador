/*
 * 	PARA TESTAR, O CÓDIGO DEVE ESTAR ESCRITO NUM ARQUIVO COMO "d:\\code.txt" 
 */

package analysers;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


public class Main {
	
	private static Scanner scan;

	public static void main(String[] args) throws IOException {
		
		String path = new String();
		
		try {
			FileReader arq = new FileReader("e:\\code.txt");
			BufferedReader lerArq = new BufferedReader(arq);
			scan = new Scanner("e:\\code.txt");
			
			String text = lerArq.readLine();			
			path = text + "\n";
			
			while(text != null) {
				text = lerArq.readLine();
				path = path + text + "\n";
				Token token = scan.scan();
				System.out.println("Column : " + token.column + " Line: " + token.line + " Spelling: " + token.spelling + " Code: " + token.kind);
			
			}

		}catch (IOException e) {
	        System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
	    }
		
	}

}
