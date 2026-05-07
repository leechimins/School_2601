// 컴퓨터학과 20221590 이지민

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class MyKeyPair {
	private static final String keyAlgoritm = "RSA";
	
	private KeyPairGenerator keyGen;
	private KeyPair pair;
	
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public static MyKeyPair getInstance(int keylength)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		MyKeyPair result = new MyKeyPair();
		result.keyGen = KeyPairGenerator.getInstance(keyAlgoritm);
		result.keyGen.initialize(keylength);
		return result;
	}
	
	public void createKeys() {
		this.pair = this.keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}
	
	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}
	
	public PublicKey getPublicKey() {
		return this.publicKey;
	}
}