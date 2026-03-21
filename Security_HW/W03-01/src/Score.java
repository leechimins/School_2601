// 컴퓨터학과 20221590 이지민

public enum Score {
    WIN, LOSE, EQUAL, ERROR;

    public static Score whoswin(Game com, Game you) {
        int result = com.ordinal() - you.ordinal();
        if (result == 0)
            return EQUAL;
        else if (result == 1 || result == -2)
            return LOSE;
        else if (result == -1 || result == 2)
            return WIN;
        else
            return ERROR;
    }
}
