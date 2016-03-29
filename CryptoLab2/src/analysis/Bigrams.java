package analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bigrams {
	public static char [] ALPHABET = new char[31];
	
	static{
		for(int i = 0, j = 1072; j <= 1103; i++, j++){
			if((char)j == 'ъ')
				j++;
			if((char) j == 'ы'){
				ALPHABET[i] = 'ь';
				continue;
			}
			if((char) j == 'ь'){
				ALPHABET[i] = 'ы';
				continue;
			}
			ALPHABET[i] = (char) j;
		}
	}

	public static List<String> getFiveBigrams(Map<String, Integer> bigrams){
		List<String> fiveBigrams = new ArrayList<String>();
		String maxBigram = "";
		int maxValue = 0;
		int prevMaxValue = Integer.MAX_VALUE;
		
		for(int i = 0; i < 5; i++){
			for(String bigram : bigrams.keySet()){
				if(bigrams.get(bigram) > maxValue && bigrams.get(bigram) <= prevMaxValue && !fiveBigrams.contains(bigram)){
					maxValue = bigrams.get(bigram);
					maxBigram = bigram;
				}
			}
			prevMaxValue = maxValue;
			maxValue = 0;
			fiveBigrams.add(maxBigram);
		}
				
		
		return fiveBigrams;
	}
	
	public static Map<String, Integer> countBigrams(String pathToFile){
		//Заполним мапу ключами(биграммы)
		Map<String, Integer> countLetters = new HashMap<>();
		for(int i = 0; i < 31; i++)
			for(int j = 0; j < 31; j++)
				countLetters.put("" + ALPHABET[i] + ALPHABET[j], 0);
		
		//Считаем кол-во вхождений каждого ключа в файл 
		try(BufferedReader reader = new BufferedReader(new FileReader(pathToFile), 1024*64)){
			int currentSymbol;
			int prevSymbol = reader.read();
			prevSymbol = reader.read();
			while((currentSymbol= reader.read()) != -1){
				String bigram = "" + (char)prevSymbol + (char)currentSymbol;
				if((char)currentSymbol == '\n')
					break;
				countLetters.put(bigram, countLetters.get(bigram) + 1);
				prevSymbol = currentSymbol;
			}
		}catch (IOException e){
			System.out.println("Problem with file " + pathToFile);
			e.printStackTrace();
		}
		
		return countLetters;
	}
}
