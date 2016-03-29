package analysis;

import java.util.ArrayList;
import java.util.List;

import mathematical.Operations;

public class Key {
	public static List<String> bigramsRussian = new ArrayList<>();
	public static List<String> bigramsCiphertext = new ArrayList<>();
	
	private static List<Character> ALPHABET = new ArrayList<>();
	
	static{
		for(int j = 1072; j <= 1103; j++){
			if((char)j == 'ъ')
				j++;
			if((char) j == 'ы'){
				ALPHABET.add('ь');
				continue;
			}
			if((char) j == 'ь'){
				ALPHABET.add('ы');
				continue;
			}
			ALPHABET.add((char) j );
		}
	}
	
	static{
		bigramsRussian.add("ст");
		bigramsRussian.add("но");
		bigramsRussian.add("то");
		bigramsRussian.add("на");
		bigramsRussian.add("ен");

		bigramsCiphertext = Bigrams.getFiveBigrams(Bigrams.countBigrams("/home/sergey/Java/workspace/eclipse/CryptoLab2/resources/05"));
	}
	
	public static List<String> getComparisonBigrams(){
		List<String> bigrams = new ArrayList<>();
		
		for(String firstBigramRussian : bigramsRussian)
			for(String secondBigramRussian : bigramsRussian){
				if(secondBigramRussian.equals(firstBigramRussian))
					continue;
				for(String firstBigramCipher : bigramsCiphertext)
					for(String secondBigramCipher : bigramsCiphertext){
						if(firstBigramCipher.equals(secondBigramCipher))
							continue;
						String bigramsComparison = "";
						if(bigramsRussian.indexOf(firstBigramRussian) < bigramsRussian.indexOf(secondBigramRussian))
							bigramsComparison += firstBigramRussian + ":" + secondBigramRussian;
						else
							bigramsComparison += secondBigramRussian + ":" + firstBigramRussian;
						bigramsComparison += ":" + firstBigramCipher + ":" + secondBigramCipher;
						if(!bigrams.contains(bigramsComparison))
							bigrams.add(bigramsComparison);
					}
			}

		return bigrams;
	}
	
	//Map<a,b>
	public static List<String> getKeys(List<String> allBigrams){
		List<String> keys = new ArrayList<>();
		
		for(String bigrams : allBigrams){

			String[] bigramsArray = bigrams.split(":");
			int firstBigramOpen = ALPHABET.indexOf(bigramsArray[0].charAt(0)) * 31 + ALPHABET.indexOf(bigramsArray[0].charAt(1));
			int firstBigramCipher = ALPHABET.indexOf(bigramsArray[2].charAt(0)) * 31 + ALPHABET.indexOf(bigramsArray[2].charAt(1));
			int secondBigramOpen = ALPHABET.indexOf(bigramsArray[1].charAt(0)) * 31 + ALPHABET.indexOf(bigramsArray[1].charAt(1));
			int secondBigramCipher = ALPHABET.indexOf(bigramsArray[3].charAt(0)) * 31 + ALPHABET.indexOf(bigramsArray[3].charAt(1));
			int m = 31*31;
			int[] aValues, bValues;
			
			aValues = Operations.findX(firstBigramOpen - secondBigramOpen, firstBigramCipher - secondBigramCipher, m);

			if(aValues.length == 0)
				continue;
			
			bValues = new int[aValues.length];
			for(int i = 0; i < aValues.length; i++){
				bValues[i] = (firstBigramCipher - firstBigramOpen*aValues[i])%(m);
				if(bValues[i] < 0)
					bValues[i] = m + bValues[i];
			}

			for(int i = 0; i < aValues.length; i++)
				keys.add(aValues[i] + ":" + bValues[i]);
		}

		return keys;
	}
}
