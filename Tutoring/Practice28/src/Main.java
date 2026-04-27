import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String name = sc.next();
        int age = sc.nextInt();
        String address = sc.next();
        long sid = sc.nextLong();
        String major = sc.next();

        Student a = new Student(name, sid, major);
        System.out.println(a);
        System.out.println(a.printSafely());

        sc.close();
    }
}

class Person {
    private String name;
    private int age;
    private String address;

    public Person() {
        this("모름", -1, "모름");
    }

    public Person(String name) {
        this(name, -1, "모름");
    }

    public Person(String name, int age) {
        this(name, age, "모름");
    }

    public Person(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "이름: " + name + ", 나이: " + age + "세, 주소: " + address;
    }
}

class Student extends Person {
    private long sid;
    private String major;

    public Student() {
        super();
        this.sid = -1;
        this.major = "모름";
    }

    public Student(String name) {
        super(name);
        this.sid = -1;
        this.major = "모름";
    }

    public Student(String name, long sid) {
        super(name, -1);
        this.sid = sid;
        this.major = "모름";
    }

    public Student(String name, long sid, String major) {
        super(name, -1, "모름");
        this.sid = sid;
        this.major = major;
    }

    public long getSid() { return sid; }

    public void setSid(long sid) { this.sid = sid; }

    public String getMajor() { return major; }

    public void setMajor(String major) { this.major = major; }

    @Override
    public String toString() {
        return super.toString() + ", 학번: " + sid + ", 전공: " + major;
    }

    public String printSafely() {
        return "이름: " + super.getName() + ", 학번: " + sid + ", 전공: " + major;
    }
}