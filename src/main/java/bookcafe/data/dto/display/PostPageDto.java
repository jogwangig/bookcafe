package bookcafe.data.dto.display;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class PostPageDto {
	
	private long id;
	
	private LocalDateTime createdAt;
	
	private String username;
	
	private String anonymousUsername;
	
	private String anonymousUserPwd;
	
	private String title;
}
