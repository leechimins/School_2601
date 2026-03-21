// 컴퓨터학과 20221590 이지민

import java.util.Scanner;

public class W03_02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("컴퓨터의 입력: ");
        String comInput = sc.next();
        Game com = Game.encode(comInput);

        System.out.print("당신의 입력: ");
        String yourInput = sc.next();
        Game user = Game.encode(yourInput);

        Score result = Score.whoswin(com, user);
        System.out.println(Score.print(result));

        sc.close();
    }
}
