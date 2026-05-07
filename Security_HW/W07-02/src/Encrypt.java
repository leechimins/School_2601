// 컴퓨터학과 20221590 이지민

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Scanner;

public class Encrypt {
    private static final int RSA_KEY_SIZE = 128;
    private static final int RSA_BLOCK_SIZE = RSA_KEY_SIZE;

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, ClassNotFoundException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Scanner s = new Scanner(System.in);
        
        // 1. 암호화 객체
        Cipher c1 = Cipher.getInstance("RSA/ECB/NoPadding");

        System.out.print("암호화 키 파일: ");
        String keyName = s.next();

        Key eKey = null;
        try (FileInputStream fis = new FileInputStream(keyName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            eKey = (Key) obj;
        } catch (IOException e) {
            e.printStackTrace();
        }

        c1.init(Cipher.ENCRYPT_MODE, eKey);
        
        // 2. 데이터 읽어오기
        System.out.print("데이터 파일 이름: ");
        String dataName = s.next();
        byte[] plainText = new byte[0];
        try (FileInputStream fis = new FileInputStream(dataName)) {
            plainText = fis.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3. 암호 쓰기
        System.out.print("암호 데이터 출력 파일: ");
        String cipherName = s.next();

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             FileOutputStream fos = new FileOutputStream(cipherName)) {
            int offset = 0;
            while (offset < plainText.length) {
                int length = Math.min(plainText.length - offset, RSA_BLOCK_SIZE);
                byte[] cipherBlock = c1.doFinal(plainText, offset, length);
                bos.write(cipherBlock);
                offset += length;
            }

            fos.write(bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        s.close();
    }
}