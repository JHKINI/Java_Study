package java_test;
import java.util.Scanner;
public class Test_l6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
Scanner scanner = new Scanner(System.in);

String grade;
System.out.print("달을 입력하세요(1~12):");
int month = scanner.nextInt();

switch (month) {
case 3: case 4: case 5:
	grade= "봄";
	break;
case 6: case 7: case 8:
	grade= "여름";
	break;
case 9: case 10: case 11: 
	grade="가을";
	break;
default: 	
	grade= "겨울";

	}
				System.out.println(grade);
				scanner.close();
			
	}

}
