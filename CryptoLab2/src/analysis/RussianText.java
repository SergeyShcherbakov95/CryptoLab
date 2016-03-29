package analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RussianText {
	public static List<Character> charListText = new ArrayList<>();
	public static char[] maxProbLetters = {'о', 'а', 'е', 'и', 'н'};
	public static char[] minProbLetters = {'э', 'ф', 'щ', 'ц', 'ш'};
	public static String[] maxBigrams = {"ст", "но", "то", "на" ,"ен"};
	
	public static void setCharListText(List<Character> text){
		charListText = text;
	}
	
	public static void setCharListText(String pathToFile){
		try(BufferedReader reader = new BufferedReader(new FileReader(pathToFile), 1024*64)){
			int currentSymbol = reader.read();
			while((currentSymbol= reader.read()) != -1){
				if(currentSymbol == '\n')
					break;
				charListText.add((char)currentSymbol);
			}
		}catch (IOException e){
			System.out.println("Problem with file " + pathToFile);
			e.printStackTrace();
		} 
	}
	
	public static boolean analysis(List<Character> text){
		int numberAnalys = 0;
		
		if(lettersMaxProb(text))
			numberAnalys++;
		if(lettersMinProb(text))
			numberAnalys++;
		if(bigrams(text))
			numberAnalys++;
		
		if(numberAnalys >= 3)
			return true;
		return false;
	}
	
	public static boolean lettersMaxProb(List<Character> text){
		Map<Character, Integer> letters = new HashMap<>();
		
		for(char c : Bigrams.ALPHABET)
			letters.put(c, 0);
		
		for(Character c : text)
			letters.put(c, letters.get(c) + 1);
		
		List<Character> maxLetters = new ArrayList<>();
		
		int prevMaxValue = Integer.MAX_VALUE;
		for(int i = 0; i < 5; i++){
			char maxLetter = '!';
			int maxValue = 0;
			for(Character c : letters.keySet()){
				if(letters.get(c) > maxValue && letters.get(c) <= prevMaxValue && !maxLetters.contains(c)){
					maxLetter = c;
					maxValue = letters.get(c);
				}	
			}
			prevMaxValue = maxValue;
			maxLetters.add(maxLetter);
		}
		
		int numberLetters = 0;
		for(char letter : maxProbLetters)
			if(maxLetters.contains(letter))
				numberLetters++;
		
		if(numberLetters > 2)
			return true;
		return false;
	}
	
	public static boolean lettersMinProb(List<Character> text){
		Map<Character, Integer> letters = new HashMap<>();
		
		for(char c : Bigrams.ALPHABET)
			letters.put(c, 0);
		
		for(Character c : text)
			letters.put(c, letters.get(c) + 1);

		List<Character> minLetters = new ArrayList<>();
		
		int prevMinValue = 0;
		for(int i = 0; i < 5; i++){
			char minLetter = '!';
			int minValue = Integer.MAX_VALUE;
			for(Character c : letters.keySet()){
				if(letters.get(c) >= prevMinValue && letters.get(c) < minValue && !minLetters.contains(c)){
					minLetter = c;
					minValue = letters.get(c);
				}	
			}
			prevMinValue = minValue;
			minLetters.add(minLetter);
		}
		int numberLetters = 0;
		for(char letter : minProbLetters)
			if(minLetters.contains(letter))
				numberLetters++;
		if(numberLetters > 2)
			return true;
		return false;
	}
	
	public static boolean bigrams(List<Character> text){
		Map<String, Integer> bigrams = new HashMap<>();
		
		for(char c : Bigrams.ALPHABET)
			for(char s : Bigrams.ALPHABET)
				bigrams.put("" + c + s, 0);

		Character currentSymbol;
		Character prevSymbol = text.get(0);
		for(int i = 1; i < text.size(); i++){
			currentSymbol = text.get(i);
			String bigram = "" + prevSymbol + currentSymbol;
			bigrams.put(bigram, bigrams.get(bigram) + 1);
			prevSymbol = currentSymbol;
		}
		
		List<String> fiveBigrams = Bigrams.getFiveBigrams(bigrams);

		int numberBigrams = 0;
		for(String bigram : maxBigrams)
			if(fiveBigrams.contains(bigram))
				numberBigrams++;
		
		if(numberBigrams > 2)
			return true;
		return false;
	}
}
