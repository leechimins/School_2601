/* 컴퓨터학과 20221590 이지민 */

package week02;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StudentTest {

	public static void main(String[] args) {
		Student[] array = {
				new Student(2, "C"),
				new Student(1, "B"),
				new Student(3, "A")
		};
		List<Student> list = Arrays.asList(array);
		Collections.sort(list);
		System.out.println(list);
	}

}
