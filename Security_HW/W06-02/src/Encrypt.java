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

public class Encrypt {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Scanner sc = new Scanner(System.in);

        // 1. 비밀키 파일에 저장
        System.out.print("비밀키 파일: ");
        String kfname = sc.next();

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        Key secretKey = keyGen.generateKey();

        try (FileOutputStream fos = new FileOutputStream(kfname);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(secretKey);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2. 데이터 파일에서 가져오기
        System.out.print("데이터 파일: ");
        String dfname = sc.next();
        byte[] data;

        try (FileInputStream fis = new FileInputStream(dfname)) {
            data = fis.readAllBytes();
        } catch (IOException e) {
            data = null;
            e.printStackTrace();
        }

        // 3. 암호화 파일 저장
        System.out.print("암호화 파일: ");
        String efname = sc.next();

        Cipher c1 = Cipher.getInstance("AES");
        c1.init(Cipher.ENCRYPT_MODE, secretKey);

        try (FileOutputStream fos = new FileOutputStream(efname);
             CipherOutputStream cos = new CipherOutputStream(fos, c1)) {
            cos.write(data);
            cos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sc.close();
    }
}