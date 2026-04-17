package java_test;

public class Circle_1 {
	int radius;
	String name;
	public Circle_1() { //매개 변수 없는 생성자
		radius = 1; name =""; //radius 의 초기값은 1
	}
	public Circle_1 (int r, String n) {//매개 변수를 가진 생성자
		radius = r; name = n;
	}
	public double getArea() {
		return 3.14*radius*radius;
	}
	public static void main(String[]args) {
		Circle_1 pizza = new Circle_1 (10,"자바피자");//Circle_1객체생성, 반지름10
		
		double area = pizza.getArea();
		System.out.println(pizza.name+"의 면적은"+area);
		
		Circle_1 donut = new Circle_1();//Circle 객체생성, 반지름1
		donut.name="도넛피자";
		area = donut.getArea();
		System.out.println(donut.name+"의 면적은"+area);
	}

	}


