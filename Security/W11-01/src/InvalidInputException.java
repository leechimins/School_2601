// 컴퓨터학과 20221590 이지민

public class InvalidInputException extends Exception {
	public InvalidInputException() {
		super("입력값이 유효하지 않습니다.");
	}
	
	public InvalidInputException(String message) {
		super(message);
	}
}
