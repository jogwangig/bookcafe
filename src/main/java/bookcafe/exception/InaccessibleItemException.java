package bookcafe.exception;

public class InaccessibleItemException extends RuntimeException{
	public InaccessibleItemException() {
		super("접근이 불가능한 아이템입니다");
	}
	
	public InaccessibleItemException(String msg) {
		super(msg);
	}

}
