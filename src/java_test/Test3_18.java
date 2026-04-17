package java_test;

public class Test3_18 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] stringNumber = {"23", "12", "3.141592", "998"}; 
		
		int i=0;
		try {
		 for (i=0; i<stringNumber.length; i++) {
		  int j = Integer.parseInt(stringNumber[i]); //“3.141592”를 정수로 변환할 때 NumberFormatException예외발생
		  System.out.println("숫자로 변환된 값은 " + j);
		 }
		}
		catch (NumberFormatException e) {
		 System.out.println(stringNumber[i] + "는 정수로 변환할 수 없습니다.");
		}
	}
}
