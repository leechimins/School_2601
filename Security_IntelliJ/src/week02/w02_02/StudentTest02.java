/* 컴퓨터학과 20221590 이지민 */
package week02.w02_02;

import java.util.ArrayList;
import java.util.Collections;

public class StudentTest02 {
    public static void main(String[] args) {
        ArrayList<Student02> sarray = new ArrayList<Student02>();

        sarray.add(new Student02("computer",  20191234, "kim"));
        sarray.add(new Student02("business", 20182468, "lee"));
        sarray.add(new Student02("music", 20192468, "park"));

        System.out.println(sarray);

        Collections.sort(sarray);
        System.out.println(sarray);
    }
}
