package java_test;
import java.util.Scanner;
public class Test2_12 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
char grade;
Scanner scanner = new Scanner(System.in);

System.out.print("점수를 입려하세요(0~100):");
int score= scanner.nextInt();//점수 읽기
if(score>=90)//score가 90이상
	
	grade='A';
else if (score>=80)// score가 80 이상 90미만
	grade='b';
else if(score>=70)//score가 70이상 80미만
	grade='c';
else if(score>=60)//score가 60이상 70미만
	grade='d';
else // score 가 60미만
	grade='f';
System.out.println("학점은"+grade+"입니다.");
 
scanner.close();
	}
}
