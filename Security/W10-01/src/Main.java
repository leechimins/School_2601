// 컴퓨터학과 20221590 이지민

import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws CloneNotSupportedException {
		Scanner sc = new Scanner(System.in);
		
		Date d0 = new Date(3000, 20,1);
		Date d1 = new Date(2019, 5, 13);
		Date d2 = new Date();
		Date d3 = new Date(1988, 12, 25);
		Date[] arr1 = { d1, d2, d3 };
		
		Diary[] diary = { null, null };
		diary[0] = new Diary(d0, arr1);
		diary[1] = (Diary)diary[0].clone();

		System.out.println("Original:\t" + diary[0]);
		System.out.println("Cloned:\t\t" + diary[1] + "\n");
		
		System.out.print("변경하고 싶은 배열을 선택하세요(0-original/1-cloned): ");
		int type = sc.nextInt();
		System.out.print("변경하고 싶은 날짜의 인덱스를 입력하세요(1/2): ");
		int index = sc.nextInt();
		System.out.print("년: ");
		int year = sc.nextInt();
		System.out.print("월: ");
		int month = sc.nextInt();
		System.out.print("일: ");
		int date = sc.nextInt();

		diary[type].day.year = 2003;
		diary[type].day.month = 6;
		diary[type].day.date = 5;
		diary[type].listOfDates[index].year = year;
		diary[type].listOfDates[index].month = month;
		diary[type].listOfDates[index].date = date;
		diary[type].listOfDates[0] = new Date(9999, 9, 99);

		System.out.println("Original:\t" + diary[0]);
		System.out.println("Cloned:\t\t" + diary[1]);
		System.out.println("=> [Case1] 셋 다 얕은 복사가 일어난다.");
		System.out.println("\n* date(가장 앞)\t\t객체의 값을 변경");
		System.out.println("  listOfDates[0]\t객체를 교체");
		System.out.println("  listOfDates[1 또는 2]\t객체의 값을 변경");
		
		sc.close();
	}
}