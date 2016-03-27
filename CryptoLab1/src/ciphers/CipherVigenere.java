package ciphers;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CipherVigenere {
	private List<Character> ALPHABET = new ArrayList<Character>(32);
	
	{
		for(int j = 1072; j <= 1103; j++)
			ALPHABET.add((char)j);
	}
	
	public void encrypt(String key, String pathToPlaintext, String pathToCiphertext){
		try(BufferedReader reader = new BufferedReader(new FileReader(pathToPlaintext), 1024*64);
				BufferedWriter writer = new BufferedWriter(new FileWriter(pathToCiphertext), 1024*64)){
			int currentSymbol;
			int index = 0;
			while((currentSymbol= reader.read()) != -1){
				char newSymbol = ALPHABET.get((ALPHABET.indexOf((char)currentSymbol) + ALPHABET.indexOf((char)key.charAt(index)))%32);
				index = (index + 1)%key.length();
				writer.write(newSymbol);
			}
			writer.flush();
		}catch (IOException e){
			System.out.println("Problem with file " + pathToPlaintext
					+ " or " + pathToCiphertext);
			e.printStackTrace();
		}
	}
	
	public void decrypt(String key, String pathToPlaintext, String pathToCiphertext){
		try(BufferedReader reader = new BufferedReader(new FileReader(pathToCiphertext), 1024*64);
				BufferedWriter writer = new BufferedWriter(new FileWriter(pathToPlaintext), 1024*64)){
			int currentSymbol;
			int index = 0;
			while((currentSymbol= reader.read()) != -1){
				int newIndex = ALPHABET.indexOf((char)currentSymbol) - ALPHABET.indexOf((char)key.charAt(index));
				if(newIndex < 0)
					newIndex = 32 + newIndex;
				char newSymbol = ALPHABET.get(newIndex);
				index = (index + 1)%key.length();
				writer.write(newSymbol);
			}
			writer.flush();
		}catch (IOException e){
			System.out.println("Problem with file " + pathToPlaintext
					+ " or " + pathToCiphertext);
			e.printStackTrace();
		}
	}
}
