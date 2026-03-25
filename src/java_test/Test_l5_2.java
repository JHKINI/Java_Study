package java_test;
import java.util.Scanner;
public class Test_l5_2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		        Scanner scanner = new Scanner(System.in);

		        System.out.print("원의 중심(x, y)과 반지름(r)을 입력하시오: ");
		        int cx = scanner.nextInt();
		        int cy = scanner.nextInt();
		        int r = scanner.nextInt();

		        System.out.print("점을 입력하시오(x, y): ");
		        int x = scanner.nextInt();
		        int y = scanner.nextInt();

		        // 거리 비교
		        if ((x - cx)*(x - cx) + (y - cy)*(y - cy) <= r*r) {
		            System.out.println("점(x,y)는 원 안에 있습니다.");
		        } else {
		            System.out.println("점(x,y)는 원 밖에 있습니다.");
		        }

		        scanner.close();
		    }
	}

