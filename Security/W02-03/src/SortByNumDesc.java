/* 컴퓨터학과 20221590 이지민 */

import java.util.Comparator;

public class SortByNumDesc implements Comparator<Student> {

	@Override
	public int compare(Student o1, Student o2) {
		return o2.id - o1.id;
	}
	
}
