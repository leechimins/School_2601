/* 컴퓨터학과 20221590 이지민 */

public enum Game {
	ROCK(0), PAPER(1), SCISSORS(2), ERROR(-1);
	
	
	
	public static Game encode(String input) {
		switch (input) {
		case "바위":
			return ROCK;
		case "보자기":
			return PAPER;
		case "가위":
			return SCISSORS;
		default:
			return ERROR;
		}
	}
}
