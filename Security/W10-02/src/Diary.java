// 컴퓨터학과 20221590 이지민

import java.util.Arrays;

public class Diary implements Cloneable {
	Date day;
	Date[] listOfDates;
	
	public Diary() {
		day = null;
		listOfDates = null;
	}
	
	public Diary(Date day, Date[] listOfDates) {
		this.day = day;
		this.listOfDates = listOfDates;
	}

	public Date getDay() { return day; }
	public void setDay(Date day) { this.day = day; }
	public Date[] getListOfDates() { return listOfDates; }
	public void setListOfDates(Date[] listOfDates) { this.listOfDates = listOfDates; }
	
	public Object clone() throws CloneNotSupportedException {
		Diary result = (Diary)super.clone();
		result.listOfDates = (Date[])listOfDates.clone();
		return result;
	}

	@Override
	public String toString() {
		return day + " " + Arrays.toString(listOfDates);
	}
}