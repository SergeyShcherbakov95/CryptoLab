package analysis;

import java.util.ArrayList;
import java.util.List;

public class Key {
	public List<Character> vowels = new ArrayList<>();
	public List<Character> consonants = new ArrayList<>();

	{
		vowels.add('а');
		vowels.add('у');
		vowels.add('е');
		vowels.add('о');
		vowels.add('и');
		vowels.add('ы');
		vowels.add('э');
		vowels.add('ю');
		vowels.add('я');
		vowels.add('ь');
		vowels.add('ъ');
		
		for(int j = 1072; j <= 1103; j++)
			if(!vowels.contains((char) j))
				consonants.add((char)j);
	}
	
	public List<String> getKeyList(List<Character> firstLetters, List<Character> secondLetters){
		
		int[] binaryKey = new int[firstLetters.size()];
		List<String> keys = new ArrayList<String>();
		
		for(int i = 0; i < binaryKey.length; i++)
			binaryKey[i] = 0;
		
		for(int i = 1; i <= Math.pow(2, binaryKey.length); i++){
			String key = "";
			for(int j = 0; j < binaryKey.length; j++){
				if(binaryKey[j] == 0)
					key += firstLetters.get(j);
				else if(binaryKey[j] == 1)
					key += secondLetters.get(j);
			}
			
			if(isCorrectKey(key))
				keys.add(key);
			
			for(int j = binaryKey.length - 1; j >= 0; j--){
				if(binaryKey[j] == 0){
					binaryKey[j] = 1;
					break;
				} else
					binaryKey[j] = 0;
			}
		}
			
		System.out.println(keys.size());
		return keys;
	}
	
	public boolean isCorrectKey(String key){
		char[] keyChars = key.toCharArray();
		
		int numberOfNeighborVowels = 0;
		int numberOfNeighborConsonants = 0;
		
		char currentSymbol;
		char prevSymbol = keyChars[0];
		
		for(int i = 1; i < keyChars.length; i++){
			currentSymbol = keyChars[i];
			
			if(consonants.contains(prevSymbol) && consonants.contains(currentSymbol))
				numberOfNeighborConsonants++;
			
			if(vowels.contains(prevSymbol) && vowels.contains(currentSymbol))
				numberOfNeighborVowels++;
			
			if(vowels.contains(prevSymbol) && (currentSymbol == 'ъ' || currentSymbol == 'ь'))
				return false;

			if(vowels.contains(currentSymbol) && currentSymbol == prevSymbol)
				return false;
			
			prevSymbol = currentSymbol;
		}
		
		if(numberOfNeighborConsonants > keyChars.length/7 || numberOfNeighborVowels > keyChars.length/7)
			return false;
		
		return true;
	}
}
