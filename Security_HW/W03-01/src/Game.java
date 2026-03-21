// 컴퓨터학과 20221590 이지민

public enum Game {
    ROCK, PAPER, SCISSORS, ERROR;

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