// 컴퓨터학과 20221590 이지민

import java.io.*;
import java.util.Scanner;

public class Read {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Student s2;
        String fname;

        System.out.print("정보를 읽어올 파일 이름을 입력하세요: ");
        fname = sc.next();
        
        s2 = Student.readFromFile(fname);
        
        System.out.println("\n생성된 학생 객체의 정보입니다.");
        System.out.println(s2);

        sc.close();
    }
}