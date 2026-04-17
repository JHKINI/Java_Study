package java_test;

public class Test3_11_2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int intArray[][] = new int[5][];
		intArray[0] = new int[1];
		intArray[1] = new int[3];
		intArray[2] = new int[5];
		intArray[3] = new int[7];
		intArray[4] = new int[9];
		
		for (int i = 0; i < intArray.length; i++)
		for (int j = 0; j < intArray[i].length; j++)
		intArray[i][j] = (i+1)*10+j;
		//j가 0이거나  intArray.lenth이면 1 나머지는 0
		//i가 4면 1 
		//1이면 *아니면""
		
		for (int i = 0; i < intArray.length; i++) {
			for (int j = 0; j < intArray[i].length; j++)
			System.out.print("*");
			System.out.println();
		}
	}
}
