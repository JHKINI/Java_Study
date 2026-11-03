package java_test;

import java.util.Scanner;

public class Test_l4 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("점(x, y) 좌표를 입력하시오: ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();

        // 사각형 범위: (100,100) ~ (200,200)
        if ((100 <= x && x <= 200) && (100 <= y && y <= 200)) {
            System.out.println("(x, y)는 네모 안에 있습니다.");
        } else {
            System.out.println("(x, y)는 밖에 있습니다.");
        }

        scanner.close();
    }
}