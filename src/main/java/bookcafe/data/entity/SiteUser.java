package bookcafe.data.entity;

import bookcafe.data.Base;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class SiteUser extends Base {
	
	private String username;
	
	private String password;
	
	private String nickName;
	
	private String emailAddress;

}
