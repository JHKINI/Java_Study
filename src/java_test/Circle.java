package java_test;

import java_test.Circle;

public class Circle {
	
	int radius; // 원의반지름필드
	String name; // 원의이름필드
	public Circle() { } // 원의생성자
	public double getArea() { // 원의면적계산메소드
		return 3.14*radius*radius;
}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Circle pizza; 
		pizza = new Circle(); // Circle 객체생성
		pizza.radius= 10; // 피자의반지름을10으로설정
		pizza.name = "자바피자"; // 피자의이름설정
		double area = pizza.getArea(); // 피자의면적알아내기
		System.out.println(pizza.name + "의면적은" + area);
		Circle donut = new Circle(); // Circle 객체생성
		donut.radius= 2; // 도넛의반지름을2로설정
		donut.name = "자바도넛"; // 도넛의이름설정
		area = donut.getArea(); // 도넛의면적알아내기
		System.out.println(donut.name + "의면적은" + area);
	}

}
