package bookcafe.data.dto.display;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BookWithBookShelfIdDto {
	
	private Long bookShelfId;
	
	private Long id;
	
	private String title;
}
