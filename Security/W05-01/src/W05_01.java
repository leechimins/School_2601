// 컴퓨터학과 20221590 이지민

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class W05_01 {
	
	public static double matchRate(byte[] a, byte[] b) {
		if (a.length != b.length) {
			return -1;
		}
		int count = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == b[i]) {
				count++;
			}
		}
		return (double)count / a.length;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String text1 = "The Road Not Taken by Robert Frost";
		String text2 = "The Road Not Taken by Robert FrosT";
		
		byte[] data1 = text1.getBytes();
		byte[] data2 = text2.getBytes();

		System.out.println("원본 문장: " + text1);
		System.out.println("대조 문장: " + text2);
		System.out.println("문장 유사도: " + matchRate(data1, data2));

		MessageDigest md1 = MessageDigest.getInstance("SHA-1");
		md1.update(data1);
		byte[] mdSHA1 = md1.digest();
		
		MessageDigest md2 = MessageDigest.getInstance("SHA-1");
		md2.update(data2);
		byte[] mdSHA2 = md2.digest();
		
		System.out.println("\n계산된 해시 값");
		System.out.print("원본: ");
		for (byte bytes: mdSHA1) {
			System.out.print(String.format("%02x  ",  bytes));
		}
		System.out.println();

		System.out.print("대조: ");
		for (byte bytes: mdSHA2) {
			System.out.print(String.format("%02x  ",  bytes));
		}
		System.out.println();
		
		System.out.println("해시 유사도: " + matchRate(mdSHA1, mdSHA2));
		
		/*byte[] data = plainTxt.getBytes();
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(data);
		byte[] mdMD5 = md.digest();
		
		System.out.println("plain text: " + plainTxt);
		System.out.println("length of digest (bytes): " + mdMD5.length);
		System.out.print("digestedMD5 (hex): ");
		for (byte bytes: mdMD5) {
			System.out.print(String.format("%02x  ", bytes));
		}*/
	}

}
