/* 컴퓨터학과 20221590 이지민 */

public class W02_01 {

	public static void main(String[] args) {
		calcSum(10, 20);
		calcSum(10, 20, 30);
		calcSum(10, 20, 30, 40, 50);
	}

	
	static void calcSum(int... nums) {
		int result = 0;
		for (int num: nums) {
			result += num;
		}
		System.out.println(result);
	}
}