// 컴퓨터학과 20221590 이지민

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

public class Save {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException {
		System.out.println("암호화 알고리즘: AES");
		System.out.println("키의 길이 (bytes): 16");
		System.out.print("비밀키를 저장할 파일 이름: ");
		Scanner sc = new Scanner(System.in);
		String fname = sc.next();
		sc.close();
		
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		Key secretKey = keyGen.generateKey();
		
		byte[] byteKey = secretKey.getEncoded();
		for (byte k : byteKey) {
			System.out.print(String.format("%02x ", k));
		}
		
		FileOutputStream fos = new FileOutputStream(fname);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(secretKey);	// key 객체를 통으로 저장
	}

}