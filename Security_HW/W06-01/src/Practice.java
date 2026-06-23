// 컴퓨터학과 20221590 이지민

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Practice {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, FileNotFoundException {
        // 1. 대칭키 생성
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        Key secretKey = keyGen.generateKey();

        // 2. 데이터 가공
        String plainText = "Hello World!";
        byte[] input = plainText.getBytes();
        System.out.println("Plain text (" + input.length + "): ");
        System.out.println(plainText);

        // 3. 암호화 객체 생성
        Cipher c1 = Cipher.getInstance("AES");
        c1.init(Cipher.ENCRYPT_MODE, secretKey);

        // 4. 암호화 스트림
        try (FileOutputStream fos = new FileOutputStream("test.txt");
             CipherOutputStream cos = new CipherOutputStream(fos, c1)) {
            cos.write(input);
            cos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}