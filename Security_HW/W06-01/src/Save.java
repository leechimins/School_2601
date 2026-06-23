// 컴퓨터학과 20221590 이지민

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Save {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Scanner sc = new Scanner(System.in);

        System.out.println("암호화 알고리즘: AES");
        System.out.println("키의 길이 (bytes): 16");

        // 1. 대칭키 생성
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        Key secretKey = keyGen.generateKey();
        byte[] byteKey = secretKey.getEncoded();

        for (byte b : byteKey) {
            System.out.print(String.format("%02x ", b));
        }

        // 2. 암호화 객체 생성
        Cipher c1 = Cipher.getInstance("AES");
        c1.init(Cipher.ENCRYPT_MODE, secretKey);

        System.out.print("\n비밀키를 저장할 파일 이름: ");
        String fname = sc.next();

        try (FileOutputStream fos = new FileOutputStream(fname);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(secretKey);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sc.close();
    }
}