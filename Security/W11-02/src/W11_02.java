// 컴퓨터학과 20221590 이지민

import java.util.Random;
import java.util.Scanner;

public class W11_02 {
	public static int whoswin(String com, String you) {
		assert you.equals("가위") || you.equals("바위") || you.equals("보") : "pre 잘못된 입력";
		if (com.equals(you)) {
			return 0;
		}
		if ((you.equals("가위") && com.equals("보")) ||
			(you.equals("바위") && com.equals("가위")) ||
            (you.equals("보") && com.equals("바위"))) {
			return 1;
		}
		return -1;
	}
	
	public static String getInput(Scanner s) {
		System.out.print("당신의 입력: ");
		String you = s.next();
		
		return you;
	}
	
	public static void main(String[] args) {
		String[] arr = { "가위", "바위", "보" };
		Random rd = new Random();
		String com = arr[rd.nextInt(3)];
		System.out.println("컴퓨터의 생성: " + com);
		try (Scanner s = new Scanner(System.in)) {
            String you = getInput(s);
            int result = whoswin(com, you);
            
            if (result == 1) {
                System.out.println("당신이 이겼습니다.");
            }
            else if (result == 0) {
                System.out.println("비겼습니다.");
            }
            else {
                System.out.println("당신이 졌습니다.");
            }
            
        }
	}
}
