package java_test;

public class TEST_2 {
	public static void main(String[] args) {
	for(int i =0; i <5; i++) {
		//for(int j = 0; j<5; j++) {
		for(int j =4; j>=0; j--) {
			if(j==0 ||i==4|| i==j)
				System.out.print("*");
			else
				System.out.print(" ");
		}
		System.out.println();
	}
	}
}
