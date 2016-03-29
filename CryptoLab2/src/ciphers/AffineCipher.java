package ciphers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import analysis.Bigrams;
import analysis.Key;
import analysis.RussianText;
import mathematical.Operations;

public class AffineCipher {
	public static Map<Integer, String> bigramsByInteger = new HashMap<>();
	public static Map<String, Integer> bigramsByString = new HashMap<>();

	static{
		for(int i = 0; i < 31; i++)
			for(int j = 0; j < 31; j++)
				bigramsByInteger.put(i*31 + j, "" + Bigrams.ALPHABET[i] + Bigrams.ALPHABET[j]);
		
		for(int i = 0; i < 31; i++)
			for(int j = 0; j < 31; j++)
				bigramsByString.put("" + Bigrams.ALPHABET[i] + Bigrams.ALPHABET[j], i*31 + j);
	}
	
	public static void encrypt(int a, int b, String line){
		char[] array = line.toCharArray();
		String newLine = "";
		
		for(int i = 0; i < array.length; i += 2){
			Character prevSymbol = array[i];
			Character currentSymbol = array[i+1];
			String bigram = "" + prevSymbol + currentSymbol;
			
			int newBigram = (a*bigramsByString.get(bigram) + b)%(31*31);
			
			newLine += bigramsByInteger.get(newBigram);
		}
		
		System.out.println(newLine);
	}
	
	public static void decrypt(String pathToFile){
		List<Character> text = new ArrayList<>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader(pathToFile), 1024*64)){
			int currentSymbol = reader.read();
			while((currentSymbol= reader.read()) != -1){
				if(currentSymbol == '\n')
					break;
				text.add((char)currentSymbol);
			}
		}catch (IOException e){
			System.out.println("Problem with file " + pathToFile);
			e.printStackTrace();
		} 
		
		List<String> keys = Key.getKeys(Key.getComparisonBigrams());
		
		for(String key : keys){
			String[] aAndB = key.split(":");
			tryKey(Integer.parseInt(aAndB[0]), Integer.parseInt(aAndB[1]), 31, text); 
		}
	}
	
	public static void tryKey(int a, int b, int n, List<Character> text){
		List<Character> opentext = new ArrayList<Character>();
		
		for(int i = 0; i < text.size(); i += 2){
			Character prevSymbol = text.get(i);
			Character currentSymbol = text.get(i+1);
			String bigram = "" + prevSymbol + currentSymbol;
			int openBigramValue = (Operations.inverse(a, n*n) * (bigramsByString.get(bigram) - b))%(n*n);
			if(openBigramValue < 0)
				openBigramValue = n*n + openBigramValue;
			String openBigram = bigramsByInteger.get(openBigramValue);
			opentext.add(openBigram.charAt(0));
			opentext.add(openBigram.charAt(1));
		}
		
		if(RussianText.analysis(opentext)){
			try(BufferedWriter writer = new BufferedWriter(new FileWriter("/home/sergey/Java/workspace/eclipse/CryptoLab2/resources/opentext-" + a + "-" + b), 1024*64)){
				for(Character c : opentext)
					writer.write(c);
			}catch (IOException e){
				System.out.println("Problem with file ");
				e.printStackTrace();
			} 
		}
	}
}
