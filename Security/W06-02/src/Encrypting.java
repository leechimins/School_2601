// 컴퓨터학과 20221590 이지민

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

public class Encrypting {
	public static void main(String[] args) throws ClassNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		Scanner sc = new Scanner(System.in);
		System.out.print("비밀키를 저장한 파일 이름: ");
		String keyFname = sc.next();

		System.out.print("데이터 파일 이름: ");
		String dataFname = sc.next();

		System.out.print("암호화 출력 파일 이름: ");
		String outputFname = sc.next();
		sc.close();
		
		Cipher cipher = Cipher.getInstance("AES");
		FileInputStream fis1 = new FileInputStream(keyFname);
		ObjectInputStream ois = new ObjectInputStream(fis1);
		Object obj = ois.readObject();
		Key secretKey = (Key) obj;
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		
		FileInputStream fis2 = new FileInputStream(dataFname);
		byte[] input = fis2.readAllBytes();
		String txt = new String(input);
		System.out.print(txt);
		
		FileOutputStream fos = new FileOutputStream(outputFname);
		CipherOutputStream cos = new CipherOutputStream(fos, cipher);
		
		cos.write(input);
		cos.flush();
	}
}
