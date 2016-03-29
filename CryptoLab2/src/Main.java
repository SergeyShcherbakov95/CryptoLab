import ciphers.AffineCipher;

public class Main {
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		
		AffineCipher.decrypt("/home/sergey/Java/workspace/eclipse/CryptoLab2/resources/05");
		
		System.out.println(System.currentTimeMillis() - startTime);
	}
}
