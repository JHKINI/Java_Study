package java_test;

public class Test3_12_ReturnArray {
	static int[] makeArray() { // 정수형배열을리턴하는메소드
		int temp[] = new int[4]; // 배열생성
		for (int i=0; i<temp.length; i++)
		temp[i] = i; // 배열의원소를0, 1, 2, 3으로초기화
		return temp; // 배열리턴
		}
		public static void main (String[] args) {
		int intArray[]; // 배열레퍼런스변수선언
		intArray= makeArray(); // 메소드로부터배열전달받음
		for (int i=0; i<intArray.length; i++)
		System.out.print(intArray[i] + " "); // 배열모든원소출력
		}
		}

