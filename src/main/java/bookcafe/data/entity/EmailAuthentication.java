package bookcafe.data.entity;

import bookcafe.data.Base;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class EmailAuthentication extends Base{
	
	private String emailAddress;
	
	private String authCode;
	
	private boolean isAuthenticated;
}
