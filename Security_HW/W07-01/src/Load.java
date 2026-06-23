// 컴퓨터학과 20221590 이지민

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.Key;
import java.util.Scanner;

public class Load {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner s = new Scanner(System.in);

        System.out.print("공개키를 저장한 파일 이름: ");
        String puname = s.next();
        System.out.println("암호화 알고리즘: RSA");
        System.out.println("[복구된 공개키 정보]");

        try (FileInputStream fis = new FileInputStream(puname);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            Key publicKey = (Key)obj;

            byte[] byteKey = publicKey.getEncoded();
            System.out.println("키의 길이(bytes): " + byteKey.length);
            for (byte b: byteKey) {
                System.out.print(String.format("%02X ", b));
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.print("개인키를 저장한 파일 이름: ");
        String prname = s.next();
        System.out.println("암호화 알고리즘: RSA");
        System.out.println("[복구된 개인키 정보]");

        try (FileInputStream fis = new FileInputStream(prname);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            Key privateKey = (Key)obj;

            byte[] byteKey = privateKey.getEncoded();
            System.out.println("키의 길이(bytes): " + byteKey.length);
            for (byte b: byteKey) {
                System.out.print(String.format("%02X ", b));
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

        s.close();
    }
}