// 컴퓨터학과 20221590 이지민

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.util.Scanner;

public class Decrypt {
    private static final int RSA_KEY_SIZE = 128;
    private static final int RSA_BLOCK_SIZE = RSA_KEY_SIZE;

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, ClassNotFoundException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Scanner s = new Scanner(System.in);

        // 1. 암호화 객체
        Cipher c2 = Cipher.getInstance("RSA/ECB/NoPadding");

        System.out.print("복호화 키 파일: ");
        String keyName = s.next();

        Key dKey = null;
        try (FileInputStream fis = new FileInputStream(keyName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            dKey = (Key) obj;
        } catch (IOException e) {
            e.printStackTrace();
        }

        c2.init(Cipher.DECRYPT_MODE, dKey);

        // 2. 복호화
        System.out.print("암호 데이터 파일: ");
        String cipherName = s.next();
        byte[] cipherText = new byte[0];
        try (FileInputStream fis = new FileInputStream(cipherName)) {
            cipherText = fis.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            int offset = 0;
            while (offset < cipherText.length) {
                int length = Math.min(cipherText.length - offset, RSA_BLOCK_SIZE);
                byte[] cipherBlock = c2.doFinal(cipherText, offset, length);
                bos.write(cipherBlock);
                offset += length;
            }

            System.out.println(bos.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        s.close();
    }
}