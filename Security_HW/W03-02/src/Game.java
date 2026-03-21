// 컴퓨터학과 20221590 이지민

public enum Game {
    ROCK(0), PAPER(1), SCISSORS(2), ERROR(-1);

    private final int value;

    Game(int i) {
        value = i;
    }

    public int getValue() {
        return value;
    }

    static Game encode (String str) {
        switch (str) {
            case "바위":
                return ROCK;
            case "보":
                return PAPER;
            case "가위":
                return SCISSORS;
            default:
                return ERROR;
        }
    }

}