/* 컴퓨터학과 20221590 이지민 */

import java.util.Comparator;

public class SortByMajorNum implements Comparator<Student> {

	@Override
	public int compare(Student o1, Student o2) {
		if (o1.major.compareTo(o2.major) == 0)
			return o1.id - o2.id;
		
		return o1.major.compareTo(o2.major);
	}
	
}
