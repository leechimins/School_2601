// 컴퓨터학과 20221590 이지민

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.NoSuchPaddingException;

public class Load {

	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		Scanner sc = new Scanner(System.in);
		System.out.print("비밀키를 저장한 파일 이름: ");
		String fname = sc.next();
		sc.close();
		
		FileInputStream fis = new FileInputStream(fname);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		Key secretKey = (Key) obj;
		
		byte[] byteKey = secretKey.getEncoded();
		for (byte k : byteKey) {
			System.out.print(String.format("%02x ", k));
		}
	}

}