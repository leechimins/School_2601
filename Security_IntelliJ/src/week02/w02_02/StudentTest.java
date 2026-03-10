/* 컴퓨터학과 20221590 이지민 */
package week02.w02_02;

import java.util.ArrayList;
import java.util.Collections;

public class StudentTest {
    public static void main(String[] args) {
        ArrayList<Student> sarray = new ArrayList<Student>();

        sarray.add(new Student("computer",  20191234, "kim"));
        sarray.add(new Student("business", 20182468, "lee"));
        sarray.add(new Student("music", 20192468, "park"));

        System.out.println(sarray);

        Collections.sort(sarray);
        System.out.println(sarray);
    }
}
