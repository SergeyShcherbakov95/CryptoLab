import java.util.Map;

import analysis.Entropy;
import analysis.Index;
import analysis.Key;
import ciphers.CipherVigenere;
import filters.TextFilter;

public class Main {

	public static void main(String[] args) {		
		long startTime = System.currentTimeMillis();
		
		task1();
		
		System.out.println("Task1 time - " + (System.currentTimeMillis() - startTime));
		
		startTime = System.currentTimeMillis();
		
		task4();
		
		System.out.println("Task4 time - " + (System.currentTimeMillis() - startTime));
		
		startTime = System.currentTimeMillis();
		
		task5();
		
		System.out.println("Task5 time - " + (System.currentTimeMillis() - startTime));
		
		startTime = System.currentTimeMillis();
		
		task6();
		
		System.out.println("Task6 time - " + (System.currentTimeMillis() - startTime));
	}	

	public static void task1(){
		Entropy entropy = new Entropy();
		
		TextFilter.deleteOtherSymbolsInFile(entropy.pathToFile, entropy.pathToFileWithSpaces);
		TextFilter.deleteSpacesInFile(entropy.pathToFileWithSpaces, entropy.pathToFileWithoutSpaces);

		System.out.println("H1 with spaces : " + entropy.countEntropy(entropy.countLetters(entropy.pathToFileWithSpaces, true)));
		System.out.println("H2 with spaces : " + entropy.countEntropy(entropy.countBigrams(entropy.pathToFileWithSpaces, true)));
		System.out.println("H1 without spaces : " + entropy.countEntropy(entropy.countLetters(entropy.pathToFileWithoutSpaces, false)));
		System.out.println("H2 without spaces : " + entropy.countEntropy(entropy.countBigrams(entropy.pathToFileWithoutSpaces, false)));
	}
	
	public static void task4(){
		TextFilter.deleteOtherSymbolsInFileWithE("/home/sergey/Java/workspace/eclipse/CryptoLab1/resources/CipherVigenere/source",
					"/home/sergey/Java/workspace/eclipse/CryptoLab1/resources/CipherVigenere/plaintext");
		
		CipherVigenere cpVigenere = new CipherVigenere();
		
		String[] keys = {"до", "кот", "соль",
				"кровь", "информация", "автономность",
				"ответственность", "раздражительность", "антисоциалистический"};
		
		for(String key : keys)
			cpVigenere.encrypt(key, "/home/sergey/Java/workspace/eclipse/CryptoLab1/resources/CipherVigenere/plaintext",
				"/home/sergey/Java/workspace/eclipse/CryptoLab1/resources/CipherVigenere/ciphertextKey-" + key);
	}
	
	public static void task5(){
		Index index = new Index("/home/sergey/Java/workspace/eclipse/CryptoLab1/resources/CipherVigenere/plaintext",
				"/home/sergey/Java/workspace/eclipse/CryptoLab1/resources/CipherVigenere/ciphertext");
		
		System.out.println("Plaintext index - " + index.countIndexes(1, index.plaintext));
		
		String[] keys = {"до", "кот", "соль",
				"кровь", "информация", "автономность",
				"ответственность", "раздражительность", "антисоциалистический"};
		
		for(String key : keys){
			index.setCipherText("/home/sergey/Java/workspace/eclipse/CryptoLab1/resources/CipherVigenere/ciphertextKey-" + key);
			System.out.println("Ciphertext index with key length " + key.length() + " - " + index.countIndexes(1, index.ciphertext));
		}
	}
	
	public static void task6(){
		Index index = new Index("/home/sergey/Java/workspace/eclipse/CryptoLab1/resources/CipherVigenere/plaintext",
				"/home/sergey/Java/workspace/eclipse/CryptoLab1/resources/CipherVigenere/ciphertext");
		
		index.findKey();
		
		CipherVigenere cpVigenere = new CipherVigenere();
		
		cpVigenere.decrypt("делолисоборотней", 
				"/home/sergey/Java/workspace/eclipse/CryptoLab1/resources/CipherVigenere/plaintextKey-делолисоборотней", 
				"/home/sergey/Java/workspace/eclipse/CryptoLab1/resources/CipherVigenere/ciphertext");
	}
}
