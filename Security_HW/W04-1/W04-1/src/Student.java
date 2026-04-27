// 컴퓨터학과 20221590 이지민

public class Student {
    private int sid;
    private String name, major;

    public Student() {
        sid = 0;
        name = "모름";
        major = "모름";
    }

    public Student(int sid, String name, String major) {
        this.sid = sid;
        this.name = name;
        this.major = major;
    }

    static Student readFromFile(String fname) {
        return new Student();
    }

    boolean writeToFile(String fname) {
        return true;
    }

    @Override
    public String toString() {
        return "Student[" + name + ", " + sid + ", " + major + "]";
    }
}