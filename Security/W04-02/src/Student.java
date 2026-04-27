// 컴퓨터학과 20221590 이지민

import java.io.*;
import java.util.Scanner;

public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
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

    static Student readFromFile(String fname) throws Exception {
    	try (Scanner sf = new Scanner(new FileReader(fname))) {
    		int sid = sf.nextInt();
    		String name = sf.next();
    		String major = sf.next();
    		
    		Student result = new Student(sid, name, major);
        	
        	return result;
    	} catch(FileNotFoundException e) {
    		e.printStackTrace();
    		return null;
    	}
    }

    boolean writeToFile(String fname) throws Exception {
    	try (PrintWriter out = new PrintWriter(new FileWriter(fname))) {
            out.println(sid);
            out.println(name);
            out.println(major);
        	return true;
    	} catch(IOException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    static Student readObject(String fname) throws IOException {
    	try (FileInputStream fis = new FileInputStream(fname)) {
    		try (ObjectInputStream ois = new ObjectInputStream(fis)) {
    			Object obj = ois.readObject();
    			Student s = (Student) obj;
    			return s;
    		}
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    	return null;
	}
    
    static boolean writeObject(String fname, Student s) {
    	try (FileOutputStream fos = new FileOutputStream(fname)) {
    		try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
    			oos.writeObject(s);
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return true;
    }

    @Override
    public String toString() {
        return String.format("Student[%s, %d, %s]", name, sid, major);
    }
}