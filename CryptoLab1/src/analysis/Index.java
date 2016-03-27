package analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Index {
	public List<Character> plaintext = new ArrayList<>();
	public List<Character> ciphertext = new ArrayList<>();
	
	private List<Character> ALPHABET = new ArrayList<Character>(32);
	
	{
		for(int j = 1072; j <= 1103; j++)
			ALPHABET.add((char)j);
	}
	
	public Index(String pathPlain, String pathCipher){
		readFromFileToArray(plaintext, pathPlain);
		readFromFileToArray(ciphertext, pathCipher);
	}
	
	public void setCipherText(String pathCipher){
		readFromFileToArray(ciphertext, pathCipher);
	}
	
	public void readFromFileToArray(List<Character> array, String pathToFile){
		try(BufferedReader reader = new BufferedReader(new FileReader(pathToFile), 1024*64)){
			int currentSymbol;
			while((currentSymbol= reader.read()) != -1)
				array.add((char)currentSymbol);
		}catch (IOException e){
			System.out.println("Problem with file " + pathToFile);
			e.printStackTrace();
		} 
	}
	
	public void findKey(){
		
		int step = this.findStep();
		
		List<Character> firstLetters = new ArrayList<Character>();
		List<Character> secondLetters = new ArrayList<Character>();
		List<Character> thirdLetters = new ArrayList<Character>();
		
		List<List<Character>> blocks = new ArrayList<>();
		
		for(int i = 0; i < step; i++)
			blocks.add(new ArrayList<Character>());
		
		for(int i = 0; i < ciphertext.size(); i++)
			blocks.get(i%blocks.size()).add(ciphertext.get(i));
		
		for(List<Character> block : blocks){
			Map<Character, Integer> chars = new HashMap<Character, Integer>();
			
			for(int i = 0; i < this.ALPHABET.size(); i++)
				chars.put(this.ALPHABET.get(i), 0);
			
			for(Character symbol : block){
				if(symbol == '\n')
					break;
				chars.put(symbol, chars.get(symbol) + 1);
			}
			
			char maxChar = 'а';
			
			for(Character keyChar : chars.keySet())
				if(chars.get(keyChar) > chars.get(maxChar))
					maxChar = keyChar;
			
			int keySymbol = this.ALPHABET.indexOf(maxChar) - this.ALPHABET.indexOf('о');
			if(keySymbol > 0)
				keySymbol %= 32;
			if(keySymbol < 0)
				keySymbol = 32 + keySymbol;
			firstLetters.add(this.ALPHABET.get(keySymbol));
			
			keySymbol = this.ALPHABET.indexOf(maxChar) - this.ALPHABET.indexOf('а');
			if(keySymbol > 0)
				keySymbol %= 32;
			if(keySymbol < 0)
				keySymbol = 32 + keySymbol;
			secondLetters.add(this.ALPHABET.get(keySymbol));
			
			keySymbol = this.ALPHABET.indexOf(maxChar) - this.ALPHABET.indexOf('е');
			if(keySymbol > 0)
				keySymbol %= 32;
			if(keySymbol < 0)
				keySymbol = 32 + keySymbol;
			thirdLetters.add(this.ALPHABET.get(keySymbol));
		}
		System.out.println(firstLetters);
		System.out.println(secondLetters);
		System.out.println(thirdLetters);
		//System.out.println(new Key().getKeyList(firstLetters, secondLetters));
	}
	
	public int findStep(){
		Map<Integer, Integer> krokener = new HashMap<>();
		
		for(int i = 2; i <= 30; i++)
			krokener.put(i, this.bigStep(i, ciphertext));
		
		int maxValue = 0;
		
		for(int i = 2; i <= 30; i++)
			if(krokener.get(i) > maxValue)
				maxValue = krokener.get(i);
		
		List<Integer> steps = new ArrayList<>();
		
		for(int i = 2; i <= 30; i++)
			if(krokener.get(i) > 0.8*maxValue)
				steps.add(i);
		
		int step = 0;
		
		for(Integer i : steps)
			for(int j = 0; j < steps.size(); j++){
				if(steps.get(j)%i != 0)
					break;
				if(j == steps.size() - 1)
					step = i;
			}
		
		return step;
	}
	
	public int bigStep(int step, List<Character> text){
		int number = 0;
		
		for(int i = 0; i < text.size() - step; i++){
			if(text.get(i).equals(text.get(i + step)))
				number++;
		}
		
		return number;
	}
	
	public List<Double> countIndexes(int step, List<Character> text){
		List<List<Character>> blocks = new ArrayList<>();
		List<Double> indexes = new ArrayList<>();
		
		for(int i = 0; i < step; i++)
			blocks.add(new ArrayList<Character>());
		
		for(int i = 0; i < text.size(); i++)
			blocks.get(i%blocks.size()).add(text.get(i));
		
		for(int i = 0; i < step; i++)
			indexes.add(countIndex(blocks.get(i)));
		
		return indexes;
	}
	
	public double countIndex(List<Character> block){
		double index = 0;
		List<Character> letters = new ArrayList<>();
		
		for(Character letter : block){
			if(letters.contains(letter))
				continue;
			int numberLetter = countLetterInArray(letter, block);
			letters.add(letter);
			index += numberLetter*(numberLetter - 1);
		}

		index /= block.size()*(block.size() - 1);
		
		return index;
	}

	public int countLetterInArray(Character letter, List<Character> block){
		int number = 0;
		
		for(int i = 0; i < block.size(); i++){
			if(block.get(i).equals(letter)){
				number++;
			}
		}
		
		return number;
	}
}
