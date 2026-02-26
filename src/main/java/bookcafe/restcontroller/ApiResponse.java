package bookcafe.restcontroller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse <T>{
	
	private Status status;
	
	private String msg;
	
	private T data;
	
	
	public static enum Status{
		SUCCESS, FAIL
	}

}
