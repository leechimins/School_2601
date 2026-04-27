// 컴퓨터학과 20221590 이지민

import java.io.*;
import java.util.Scanner;

public class Read {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Scanner sf = null;

        System.out.print("\n정보를 읽어올 파일 이름을 입력하세요: ");
        String fname = sc.next();

        sf = new Scanner(new BufferedReader(new FileReader(fname)));
        sf.close();
        Student s1 = Student.readFromFile(fname);

        System.out.println("\n생성된 학생 객체의 정보입니다.");
        System.out.println(s1);

        sc.close();
    }
}