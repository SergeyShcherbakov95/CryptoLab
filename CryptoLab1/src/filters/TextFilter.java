package filters;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextFilter {
	
	//удаляет все небуквенные символы кроме пробелов. ё остается. С верхнего регистра все в нижний. Несколько пробелов заменяются одним
	public static void deleteOtherSymbolsInFile(String pathFrom, String pathTo){
		
		try(BufferedReader reader = new BufferedReader(new FileReader(pathFrom), 1024*64);
				BufferedWriter writer = new BufferedWriter(new FileWriter(pathTo), 1024*64)){
			int currentSymbol;
			int prevSymbol = 'a';
			while((currentSymbol= reader.read()) != -1){
				if(prevSymbol == ' ' && currentSymbol == ' ')
					continue;
				currentSymbol = Character.toLowerCase(currentSymbol);
				if((currentSymbol >= 1072 && currentSymbol <= 1105) || currentSymbol == ' '){
					writer.write(currentSymbol);
					prevSymbol = currentSymbol;
				}
			}
			writer.flush();
		}catch (IOException e){
			System.out.println("Problem with file " + pathFrom
					+ " or " + pathTo);
			e.printStackTrace();
		}
	}
	
	//удаляет все пробелы с файла
	public static void deleteSpacesInFile(String pathFrom, String pathTo){
		
		try(BufferedReader reader = new BufferedReader(new FileReader(pathFrom), 1024*64);
				BufferedWriter writer = new BufferedWriter(new FileWriter(pathTo), 1024*64)){
			int currentSymbol;
			while((currentSymbol= reader.read()) != -1){
				currentSymbol = Character.toLowerCase(currentSymbol);
				if(currentSymbol >= 1072 && currentSymbol <= 1105 ){
					writer.write(currentSymbol);
				}
			}
			writer.flush();
		}catch (IOException e){
			System.out.println("Problem with file " + pathFrom
					+ " or " + pathTo);
			e.printStackTrace();
		} 
	}
	
	//удаляет все небуквенные символы. ё заменяется символом е
	public static void deleteOtherSymbolsInFileWithE(String pathFrom, String pathTo){		
		try(BufferedReader reader = new BufferedReader(new FileReader(pathFrom), 1024*64);
				BufferedWriter writer = new BufferedWriter(new FileWriter(pathTo), 1024*64)){
			int currentSymbol;
			while((currentSymbol= reader.read()) != -1){
				currentSymbol = Character.toLowerCase(currentSymbol);
				if(currentSymbol == 'ё'){
					writer.write('е');
					continue;
				}
				if(currentSymbol >= 1072 && currentSymbol <= 1103 ){
					writer.write(currentSymbol);
				}
			}
			writer.flush();
		}catch (IOException e){
			System.out.println("Problem with file " + pathFrom
					+ " or " + pathTo);
			e.printStackTrace();
		}
	}
}
