package java_test;
import java.util.Scanner;
public class Test2_11 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
Scanner in =new Scanner(System.in);

System.out.print("수를 입력하시오:");
int number =in.nextInt();

if (number%3 == 0)
	System.out.println("3의 배수입니다.");
else
	System.out.println("3의 배수가 아닙니다.");

in. close();
	}

}
