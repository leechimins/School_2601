// 컴퓨터학과 20221590 이지민

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Decrypt {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Scanner sc = new Scanner(System.in);

        // 1. 비밀키 가져오기
        System.out.print("\n비밀키 파일: ");
        String kfname = sc.next();
        Key secretKey = null;

        try (FileInputStream fis = new FileInputStream(kfname);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            secretKey = (Key)obj;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 암호화 객체 생성
        Cipher c2 = Cipher.getInstance("AES");
        c2.init(Cipher.DECRYPT_MODE, secretKey);

        // 3. 복호화 하기
        System.out.print("암호 파일: ");
        String dfname = sc.next();
        try (FileInputStream fis = new FileInputStream(dfname);
             CipherInputStream cis = new CipherInputStream(fis, c2);
             Scanner c = new Scanner(cis)) {
            String decryted = new String();
            while (c.hasNext()) {
                decryted += c.nextLine();
                decryted += "\n";
            }
            System.out.println(decryted);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sc.close();
    }
}