// 컴퓨터학과 20221590 이지민

import java.io.*;
import java.security.*;
import java.util.Scanner;

public class MakeKey {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        save();
    }

    static void save() throws NoSuchAlgorithmException {
        Scanner s = new Scanner(System.in);

        // 1. KeyPair 생성
//        System.out.println("암호화 알고리즘: RSA");
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 2. 공개키
        PublicKey publicKey = keyPair.getPublic();
//        System.out.println("[생성된 공개키 정보]");
//        byte[] byteKey = publicKey.getEncoded();
//        System.out.println("키의 길이(bytes): " + byteKey.length);
//        for (byte b : byteKey) {
//            System.out.print(String.format("%02X ", b));
//        }
//        System.out.println();

        // 3. 개인키
        PrivateKey privateKey = keyPair.getPrivate();
//        System.out.println("[생성된 개인키 정보]");
//        byteKey = privateKey.getEncoded();
//        System.out.println("키의 길이(bytes): " + byteKey.length);
//        for (byte b : byteKey) {
//            System.out.print(String.format("%02X ", b));
//        }
//        System.out.println();

        // 4. 저장
        System.out.print("공개키를 저장할 파일 이름: ");
        String puname = s.next();
        try (FileOutputStream fos = new FileOutputStream(puname);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(publicKey);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("개인키를 저장할 파일 이름: ");
        String prname = s.next();
        try (FileOutputStream fos = new FileOutputStream(prname);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(privateKey);
        } catch (IOException e) {
            e.printStackTrace();
        }

        s.close();
    }

    static void load() throws ClassNotFoundException {
        Scanner s = new Scanner(System.in);

        System.out.print("공개키를 저장한 파일 이름: ");
        String puname = s.next();
//        System.out.println("암호화 알고리즘: RSA");
//        System.out.println("[복구된 공개키 정보]");

        try (FileInputStream fis = new FileInputStream(puname);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            Key publicKey = (Key)obj;

//            byte[] byteKey = publicKey.getEncoded();
//            System.out.println("키의 길이(bytes): " + byteKey.length);
//            for (byte b: byteKey) {
//                System.out.print(String.format("%02X ", b));
//            }
//            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.print("개인키를 저장한 파일 이름: ");
        String prname = s.next();
//        System.out.println("암호화 알고리즘: RSA");
//        System.out.println("[복구된 개인키 정보]");

        try (FileInputStream fis = new FileInputStream(prname);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            Key privateKey = (Key)obj;

//            byte[] byteKey = privateKey.getEncoded();
//            System.out.println("키의 길이(bytes): " + byteKey.length);
//            for (byte b: byteKey) {
//                System.out.print(String.format("%02X ", b));
//            }
//            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

        s.close();
    }
}