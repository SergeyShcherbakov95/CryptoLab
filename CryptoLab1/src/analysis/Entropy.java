package analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Entropy {
	public String pathToFile = "/home/sergey/Java/workspace/eclipse/CryptoLab1/resources/Entropy/sourceText";
	public String pathToFileWithSpaces = "/home/sergey/Java/workspace/eclipse/CryptoLab1/resources/Entropy/textWithSpaces";
	public String pathToFileWithoutSpaces  = "/home/sergey/Java/workspace/eclipse/CryptoLab1/resources/Entropy/textWithoutSpaces";
	
	private char [] ALPHABET = new char[33];
	
	{
		for(int i = 0, j = 1072; j <= 1105; i++, j++){
			if(j == 1104)
				j++;
			ALPHABET[i] = (char) j;
		}
	}
	
	public double countEntropy(Map<String, Integer> ngrams){
		//Общее кол-во символов
		int numberNgramInFile = this.countNgrams(ngrams);
		int ngramLength = 1;
		if(ngrams.containsKey("бв"))
			ngramLength = 2;
		
		double probability, entropy = 0;
		for ( Integer value : ngrams.values()){
			if(value == 0)
				continue;
			probability = (double)value / ((double)numberNgramInFile);
			entropy += probability * (Math.log(probability)/Math.log(2));
		}
		entropy /= -ngramLength;
		
		return entropy;
	}
	
	public Map<String , Double> countProbabilities(Map<String, Integer> ngrams){
		double numberNgrams = this.countNgrams(ngrams);
		Map<String, Double> probabilities = new HashMap<>();
		
		for(String key : ngrams.keySet()){
			probabilities.put(key, (double)ngrams.get(key)/numberNgrams);
		}
		
		return probabilities;
	}
	
	public int countNgrams(Map<?, Integer> ngrams){
		int numberOfNgrams = 0;
		
		for ( Integer value : ngrams.values())
			numberOfNgrams += value;
		
		return numberOfNgrams;
	}
		
	public Map<String, Integer> countBigrams(String pathToFile, boolean isFileWithSpaces){
		//Заполним мапу ключами(биграммы)
		Map<String, Integer> countLetters = new HashMap<>();
		for(int i = 0; i < 33; i++)
			for(int j = 0; j < 33; j++)
				countLetters.put("" + ALPHABET[i] + ALPHABET[j], 0);
		if(isFileWithSpaces)
			for(int i = 0; i < 33; i++){
				countLetters.put("" + ALPHABET[i] + ' ', 0);
				countLetters.put("" + ' ' + ALPHABET[i], 0);
			}
		
		//Считаем кол-во вхождений каждого ключа в файл 
		try(BufferedReader reader = new BufferedReader(new FileReader(pathToFile), 1024*64)){
			int currentSymbol;
			int prevSymbol = reader.read();
			while((currentSymbol= reader.read()) != -1){
				String bigram = "" + (char)prevSymbol + (char)currentSymbol;
				countLetters.put(bigram, countLetters.get(bigram) + 1);
				prevSymbol = currentSymbol;
			}
		}catch (IOException e){
			System.out.println("Problem with file " + pathToFile);
			e.printStackTrace();
		}
		
		return countLetters;
	}
	
	public Map<String, Integer> countLetters(String pathToFile, boolean isFileWithSpaces){
		//Заполняем мапу ключами(буквами)
		Map<String, Integer> countLetters = new HashMap<>();
		for(int i = 0; i < 33; i++)
			countLetters.put("" + ALPHABET[i], 0);
		if(isFileWithSpaces)
			countLetters.put(" ", 0);
		
		//Считаем кол-во букв и добавляем значения в мапу
		try(BufferedReader reader = new BufferedReader(new FileReader(pathToFile), 1024*64)){
			int currentSymbol;
			while((currentSymbol= reader.read()) != -1){
				countLetters.put("" + (char)currentSymbol, countLetters.get("" + (char)currentSymbol) + 1);
			}
		}catch (IOException e){
			System.out.println("Problem with file " + pathToFile);
			e.printStackTrace();
		}
		
		return countLetters;
	}
	
}
