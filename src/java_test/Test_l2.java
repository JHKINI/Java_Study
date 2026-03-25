package java_test;

import java.util.Scanner;
public class Test_l2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("금액을 입력하시오.");
		Scanner scanner = new Scanner(System.in);
		
		int money =scanner.nextInt();  
		int oman = money /50000;       
		int man = (money / 10000) % 5;
		int chen = (money / 1000) % 10;
		int bek = (money / 100) % 10;
		int ohshib = (money / 50) % 2;
		int shib = (money / 10) % 5;
		int il = money % 10;
		
		System.out.print("오만원권" + oman + "매");
		System.out.print("만원권" +man+ "매");
		System.out.print("천원권" +chen+ "매");
		System.out.print("백원" +bek+ "개");
		System.out.print("오십원" +ohshib+ "개");
		System.out.print("십원" +shib+ "개");
		System.out.print("일원"+il + "개");
		
		scanner.close();
	}

}
