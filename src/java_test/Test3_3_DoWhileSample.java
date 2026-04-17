package java_test;

public class Test3_3_DoWhileSample {
	public static void main (String[] args) {
		char c = 'a';
		do {
		System.out.print(c);
		c = (char) (c + 1);
		} while (c <= 'z'); 
	}
}
