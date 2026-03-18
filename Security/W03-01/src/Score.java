/* 컴퓨터학과 20221590 이지민 */

public enum Score {
	WIN, LOSE, EQUAL, ERROR;
	
	public static Score whoswin(Game com, Game you) {
		int flag = com.ordinal() - you.ordinal();
		
		if (flag == 0)
			return EQUAL;
		else if (flag == -1 || flag == 2)
			return WIN;
		else if (flag == 1 || flag == -2)
			return LOSE;
		else
			return ERROR;
	}
}