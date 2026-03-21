// 컴퓨터학과 20221590 이지민

import java.util.Scanner;

public class W03_01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("컴퓨터의 입력: ");
        String comInput = sc.next();
        Game com = Game.encode(comInput);

        System.out.print("당신의 입력: ");
        String yourInput = sc.next();
        Game user = Game.encode(yourInput);

        Score result = Score.whoswin(com, user);
        switch (result) {
            case WIN:
                System.out.println("당신이 이겼습니다.");
                break;
            case EQUAL:
                System.out.println("비겼습니다.");
                break;
            case LOSE:
                System.out.println("컴퓨터가 이겼습니다.");
                break;
            default:
                System.out.println("오류 발생");
        }

        sc.close();
    }
}
