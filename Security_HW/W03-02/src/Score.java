// 컴퓨터학과 20221590 이지민

public enum Score {
    WIN("당신이 이겼습니다."),
    LOSE("컴퓨터가 이겼습니다."),
    EQUAL("비겼습니다."),
    ERROR("오류 발생");

    private final String info;

    Score(String value) {
        info = value;
    }

    public String getInfo() {
        return info;
    }

    public static Score whoswin(Game com, Game you) {
        if (com == Game.ERROR || you == Game.ERROR)
            return ERROR;

        Score[][] scoreBoard = {
            { EQUAL, WIN, LOSE },
            { LOSE, EQUAL, WIN },
            { WIN, LOSE, EQUAL }
        };
        return scoreBoard[com.getValue()][you.getValue()];
    }

    public static String print(Score result) {
        return result.getInfo();
    }
}
