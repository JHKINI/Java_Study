package java_test;
import java.util.Scanner;

public class Test_last {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("원화를 입력하세요(단위 원) >> ");
		Scanner scanner = new Scanner(System.in);
		double won = scanner.nextDouble(); // 정수 읽기
		System.out.print(won+"원은 $"+won/1500 +"입니다. ");
		
		scanner.close(); // scanner 닫기
	}
}
