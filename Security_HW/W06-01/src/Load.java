// 컴퓨터학과 20221590 이지민

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.*;
import java.security.Key;
import java.util.Scanner;

public class Load {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n비밀키를 저장한 파일 이름: ");
        String fname = sc.next();

        System.out.println("암호화 알고리즘: AES");
        System.out.println("키의 길이 (bytes): 16");

        try (FileInputStream fis = new FileInputStream(fname);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            Key secretKey = (Key)obj;

            byte[] byteKey = secretKey.getEncoded();
            for (byte b: byteKey) {
                System.out.print(String.format("%02x ", b));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        sc.close();
    }
}
