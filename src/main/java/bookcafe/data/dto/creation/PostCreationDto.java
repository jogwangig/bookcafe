package bookcafe.data.dto.creation;

import bookcafe.data.entity.Post;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostCreationDto implements CreationDto<Post>{
	private String anonymousUsername;
	
	private String anonymousUserPwd;
	
	private String title;
	
	@Column(length=1000)
	private String content;
	
	private long boardId;
	
	
	public Post toEntity() {
		return Post.builder().anonymousUsername(anonymousUsername)
							.anonymousUserPwd(anonymousUserPwd)
							.title(title).content(content).build();
	}
}
