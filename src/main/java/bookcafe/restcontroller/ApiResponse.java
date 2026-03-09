package bookcafe.restcontroller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse <T>{
	
	private String msg;
	
	private T data;
	

}
