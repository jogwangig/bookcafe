package bookcafe.data.entity;

import bookcafe.data.Base;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Entity
@Getter
@ToString(callSuper = true)
public class SiteUser extends Base {
	
	private String userId;
	
	private String password;
	
	private String nickName;
	

}
