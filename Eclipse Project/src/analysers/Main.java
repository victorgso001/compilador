package analysers;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) throws IOException {
		Scanner ler = new Scanner(System.in);	// just to read data entry
		String path;
		
		System.out.printf("Entre com o código:\n");
		path = ler.nextLine();
		
		FileWriter arq = new FileWriter("d:\\programa.txt");
		PrintWriter gravarArq = new PrintWriter(arq);
		
		gravarArq.printf(path);
		
		arq.close();
		
		
	}

}
