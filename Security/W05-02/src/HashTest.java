// 컴퓨터학과 20221590 이지민

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HashTest {
	
	private static final int BLOCK_SIZE = 64;
	
	public static byte[] calcHashVal(String fname) throws NoSuchAlgorithmException {
		// BufferedInputStream로 감싸는게 좋다.
		try (FileInputStream fis = new FileInputStream(fname)) {
			byte[] data = fis.readAllBytes();
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			
			for (int i = 0; i < data.length; i += BLOCK_SIZE) {
				byte[] block = Arrays.copyOfRange(data, i, i + BLOCK_SIZE);
				md.update(block);
			}
			byte[] mdSHA1 = md.digest();
			return mdSHA1;
    	} catch (IOException e) {
    		e.printStackTrace();
    		return null;
    	}
	}
	
	public static void saveBytes(String fname, byte[] b) {
		try (FileOutputStream fos = new FileOutputStream(fname)) {
			fos.write(b);
		} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	public static byte[] readHashVal(String fname) {
		try (FileInputStream fis = new FileInputStream(fname)) {
			byte[] result = fis.readAllBytes();
			return result;
    	} catch (IOException e) {
    		e.printStackTrace();
    		return null;
    	}
	}
	
	public static boolean compareHashVal(byte[] h1, byte[] h2) {
		if (h1.length != h2.length) {
			return false;
		}
		for (int i = 0; i < h1.length; i++) {
			if (h1[i] != h2[i]) {
				return false;
			}
		}
		return true;
	}
	
	public static void printBytes(byte[] b) {
		for (byte bytes: b) {
			System.out.print(String.format("%02x  ",  bytes));
		}
		System.out.println();
	}

}