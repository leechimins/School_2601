// 컴퓨터학과 20221590 이지민

import java.util.Scanner;

public class Write {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.print("학생 정보를 입력하세요: ");
        int sid = sc.nextInt();
        String name = sc.next();
        String major = sc.next();
        Student s1 = new Student(sid, name, major);

        System.out.println("생성된 학생 객체의 정보입니다.");
        System.out.println(s1);

        System.out.print("\n정보를 저장할 파일 이름을 입력하세요: ");
        String fname = sc.next();
        s1.writeToFile(fname);
        
        System.out.println("프로그램을 종료합니다.");
        sc.close();
    }
}
