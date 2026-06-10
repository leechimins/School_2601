// 컴퓨터학과 20221590 이지민

public class Date implements Cloneable {
	int year;
	int month;
	int date;
	
	public Date() {
		this.year = 2000;
		this.month = 1;
		this.date = 1;
	}
	
	public Date(int year, int month, int date) {
		this.year = year;
		this.month = month;
		this.date = date;
	}
	
	public int getYear() { return year; }
	public void setYear(int year) { this.year = year; }
	public int getMonth() { return month; }
	public void setMonth(int month) { this.month = month; }
	public int getDate() { return date; }
	public void setDate(int date) { this.date = date; }
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	@Override
	public String toString() {
		return year + "년 " + month + "월 " + date + "일";
	}
}