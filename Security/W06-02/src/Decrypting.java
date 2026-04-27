// 컴퓨터학과 20221590 이지민

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;

public class Decrypting {
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, InvalidKeyException, ClassNotFoundException {
		Scanner s = new Scanner(System.in);
		System.out.print("비밀키를 저장한 파일 이름: ");
		String keyFname = s.next();

		System.out.print("암호화 파일 이름: ");
		String dataFname = s.next();
		s.close();
		
		Cipher cipher = Cipher.getInstance("AES");
		FileInputStream fis1 = new FileInputStream(keyFname);
		ObjectInputStream ois = new ObjectInputStream(fis1);
		Object obj = ois.readObject();
		Key secretKey = (Key) obj;
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		
		FileInputStream fis2 = new FileInputStream(dataFname);
		CipherInputStream cis = new CipherInputStream(fis2, cipher);
		
		Scanner sc = new Scanner(cis);
		String decrypted = new String();
		while (sc.hasNext()) {
			decrypted += sc.nextLine();
			decrypted += "\n";
		}
		sc.close();
		
		System.out.println(decrypted);
	}
}
