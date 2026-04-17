package java_test_2;

import java.util.Scanner;

class CurrencyConverter {
	private static double rate; // 한국원화에대한환율

	public static double toDollar(double won) {
		return won / rate; // 한국원화를달러로변환
	}

	public static double toKWR(double dollar) {
		return dollar * rate; // 달러를한국원화로변환
	}

	public static void setRate(double r) {
		rate = r; // 환율설정. KWR/$1
	}
}

public class StaticMember {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("환율(1달러)>> ");
		double rate = scanner.nextDouble();
		CurrencyConverter.setRate(rate);// 미국달러환율설정
		System.out.println("백만원은$" + CurrencyConverter.toDollar(1000000) + "입니다.");
		System.out.println("$100는" + CurrencyConverter.toKWR(100) + "원입니다.");
		scanner.close();
	}
}
