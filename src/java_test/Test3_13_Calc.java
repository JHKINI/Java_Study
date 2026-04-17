package java_test;
public class Test3_13_Calc {
public static void main(String[] args) {
	double sum = 0.0;
		
		
	// TODO Auto-generated method stub
	for (int i=0; i<args.length; i++) // 인자 개수만큼 반복
		sum += Double.parseDouble(args[i]); // 문자열을 실수(double 타입)로 변환하여 합산
		System.out.println("합계:" + sum);
	}

}
