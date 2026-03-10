/* 컴퓨터학과 20221590 이지민 */

package week02;

public class Student implements Comparable<Student> {
	int number;
	String name;
	
	public Student(int number, String name) {
		this.number = number;
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
	public int compareTo(Student s) {
		return number - s.number;
	}
}
