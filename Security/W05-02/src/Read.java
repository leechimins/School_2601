// 컴퓨터학과 20221590 이지민

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Read {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		Scanner sc = new Scanner(System.in);
		System.out.print("입력 파일 이름: ");
		String fname = sc.next();
		
		byte[] b1 = HashTest.calcHashVal(fname);
		
		System.out.println("계산된 해시값");
		HashTest.printBytes(b1);
		
		System.out.print("저장된 해시값 파일 이름: ");
		fname = sc.next();
		byte[] b2 = HashTest.readHashVal(fname);
		
		boolean flag = HashTest.compareHashVal(b1, b2);
		System.out.println("비교 결과: " + flag);
		
		sc.close();
	}

}
