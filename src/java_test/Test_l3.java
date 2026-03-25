package java_test;

import java.util.Scanner;

public class Test_l3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("정수3개를 입력하세요:");
	
		
		int a =scanner.nextInt();  
		int b =scanner.nextInt(); 
		int c =scanner.nextInt(); 
		
		if(a+b>c&& a+c>b&&b+c>a) {
			System.out.println("삼각형가능");
		}else {
			System.out.println("삼각형 불가능");
		}
		scanner.close();
	}

}
