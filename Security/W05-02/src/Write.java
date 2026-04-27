// 컴퓨터학과 20221590 이지민

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Write {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		Scanner sc = new Scanner(System.in);
		System.out.print("입력 파일 이름: ");
		String fname = sc.next();
		
		byte[] b = HashTest.calcHashVal(fname);
		
		System.out.println("계산된 해시값");
		HashTest.printBytes(b);
		
		System.out.print("해시값을 저장할 파일 이름: ");
		fname = sc.next();
		HashTest.saveBytes(fname, b);
		
		sc.close();
	}

}
