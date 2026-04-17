package java_test_2;

class Point {
	private int x, y; // 한 점을 구성하는 x, y 좌표
	public Point() {
		this.x = this.y = 0;
//x=5, y=6전달
	}

	public Point(int x, int y) {
		this.x = x;this.y = y;
	}

	public void showPoint() { // 점의 좌표 출력
		System.out.println("(" + x + "," + y + ")");
	}
}

class ColorPoint extends Point {
	private String color; // 점의 색
    public ColorPoint(int x, int y, String color) {
		super(x, y); // Point의 생성자 Point(x, y) 호출
		this.color = color;
}

    public void showColorPoint() { // 컬러 점의 좌표 출력
    	System.out.print(color);
    	showPoint(); // Point 클래스의 showPoint() 호출
    }
}
public class SuperEX {
	public static void main(String[] args) {
		ColorPoint cp = new ColorPoint(5, 6, "blue");
		cp.showColorPoint();
		}		
}
