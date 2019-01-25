/*
 * 	PARA TESTAR, O CÓDIGO DEVE ESTAR ESCRITO NUM ARQUIVO COMO "d:\\code.txt" 
 */

package analysers;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


public class Main {

	public static void main(String[] args) throws IOException {
		
		String path = new String();
		
		try {
			FileReader arq = new FileReader("d:\\code.txt");
			BufferedReader lerArq = new BufferedReader(arq);
			
			String text = lerArq.readLine();			
			path = text + "\n";
			
			while(text != null) {
				text = lerArq.readLine();
				path = path + text + "\n";
			}
			
			lerArq.close();
			
		}catch (IOException e) {
	        System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
	    }
		
		System.out.print(path);
		
	}

}
