// 컴퓨터학과 20221590 이지민

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

public class MakeSecretKey {
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		Key secretKey = keyGen.generateKey();
		
		FileOutputStream fos = new FileOutputStream("skey.bin");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(secretKey);
	}
}
