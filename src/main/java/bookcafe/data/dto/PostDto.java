package bookcafe.data.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class PostDto {
	
	private Long id;
	
	private LocalDateTime createdAt;
	
	private long boardId;
	
	private String username;
	
	private String anonymousUsername;
		
	private String title;
	
	private String content;
}
