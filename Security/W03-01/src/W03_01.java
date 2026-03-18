/* 컴퓨터학과 20221590 이지민 */

import java.util.Scanner;

public class W03_01 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("컴퓨터의 입력: ");
		String strCom = sc.next();
		System.out.print("당신의 입력: ");
		String strYou = sc.next();
		
		Game com = Game.encode(strCom);
		Game you = Game.encode(strYou);
		
		Score flag = Score.whoswin(com, you);
		if (flag == Score.WIN)
			System.out.println("당신이 이겼습니다.");
		else if (flag == Score.EQUAL)
			System.out.println("비겼습니다.");
		else if (flag == Score.LOSE)
			System.out.println("컴퓨터가 이겼습니다.");
		else
			System.out.println("에러 발생");
	}
}
