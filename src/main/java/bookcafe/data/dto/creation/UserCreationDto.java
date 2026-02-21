package bookcafe.data.dto.creation;

import bookcafe.data.entity.SiteUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDto implements CreationDto<SiteUser>{
	
	private String username;
	
	private String password;
	
	private String nickName;
	
	public static UserCreationDto fromEntity(SiteUser user) {
		return new UserCreationDto(user.getUsername(), user.getPassword(), user.getNickName());
	}
	
	
	public SiteUser toEntity() {
		
		return SiteUser.builder().username(username)
				.password(password)
				.nickName(nickName).build();
	}
}
