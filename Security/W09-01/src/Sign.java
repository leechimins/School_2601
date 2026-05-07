// 컴퓨터학과 20221590 이지민

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.util.Scanner;

import javax.crypto.KeyGenerator;

public class Sign {

	public static void main(String[] args)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
		String plainText = "Software security of Dongduk University";
		byte[] data = plainText.getBytes();
		System.out.println("서명에 사용할 데이터: " + plainText);
		
		System.out.print("개인키 파일 이름: ");
		Scanner sc = new Scanner(System.in);
		String fName = sc.next();
		
		KeyGenerator keyGen = KeyGenerator.getInstance("RSA");
		keyGen.init(1024);
		Key secretKey = keyGen.generateKey();
		
		byte[] byteKey = secretKey.getEncoded();
		System.out.println("생성된 서명 정보: 1024bytes");
		for (byte k : byteKey) {
			System.out.print(String.format("%02x ", k));
		}
		
		
		
		
		
		/*
		String keyAlgorithm = "RSA";
		String signAlgorithm = "SHA1withRSA";
		MyKeyPair MKP = MyKeyPair.getInstance(1024);
		MKP.createKeys();
		byte[] data = { 1,2,3,4,15 };
		Signature sig = Signature.getInstance(signAlgorithm);
		sig.initSign(MKP.getPrivateKey());
		sig.update(data);
		byte[] signature = sig.sign();
		System.out.println(sig);
		*/
		
		sc.close();
	}

}
