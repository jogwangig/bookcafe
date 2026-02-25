package bookcafe.data.dto.creation;

import bookcafe.data.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreationDto implements CreationDto<Comment>{
	
	private String anonymousUsername;
	
	private String anonymousUserPwd;
	
	private String content;
	
	public Comment toEntity() {
		return Comment.builder().anonymousUsername(anonymousUsername)
				.anonymousUserPwd(anonymousUserPwd).content(content).build();
	}
}
