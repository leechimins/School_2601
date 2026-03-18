/* 컴퓨터학과 20221590 이지민 */
package week02.w02_02;

public class Student02 implements Comparable<Student02> {
    String major;
    int id;
    String name;

    public Student02(String major, int id, String name) {
        this.major = major;
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return major + "/" + id + "/" + name;
    }

    @Override
    public int compareTo(Student02 o) {
        return this.id - o.id;
    }
}
