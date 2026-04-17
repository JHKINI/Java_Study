package java_test;
import java.util.Scanner;

	public class Rectangle {
		int width;
		int height;
		//클레스안에 생성자가 없으면 기본생성자를 시스템이 만들어줌 public Rectangle() { }
		public int getArea() {
			return width*height;
}
public static void main(String[] args) {
	Rectangle rect= new Rectangle();// 객체생성
	Scanner scanner= new Scanner(System.in);
	System.out.print(">> ");

	rect.width= scanner.nextInt();
	rect.height= scanner.nextInt();

	System.out.println("사각형의면적은"+ rect.getArea());

	scanner.close();

	}

}
