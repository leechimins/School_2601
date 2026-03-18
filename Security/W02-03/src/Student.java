/* 컴퓨터학과 20221590 이지민 */

public class Student implements Comparable<Student> {
	String major;
    int id;
    String name;

    public Student(String major, int id, String name) {
        this.major = major;
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return major + "/" + id + "/" + name;
    }

    @Override
    public int compareTo(Student o) {
        return this.id - o.id;
    }
}