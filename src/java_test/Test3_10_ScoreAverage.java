package java_test;

public class Test3_10_ScoreAverage {
	public static void main(String[] args) {
		double score[][] = {{3.3, 3.4}, // 1학년1, 2학기평점
				{3.5, 3.6}, // 2학년1, 2학기평점
				{3.7, 4.0}, // 3학년1, 2학기평점
				{4.1, 4.2} }; // 4학년1, 2학기평점
				double sum=0;
				for(int year=0; year<score.length; year++) // 각학년별로반복
				for(int term=0; term<score[year].length; term++) // 각학년의학기별로반복
				sum += score[year][term]; // 전체평점합
				int n=score.length; // 배열의행개수, 4
				int m=score[0].length; // 배열의열개수, 2
				System.out.println("4년 전체 평점 평균은" + sum/(n*m));
		
	}
}
