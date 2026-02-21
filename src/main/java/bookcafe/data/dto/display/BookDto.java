package bookcafe.data.dto.display;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class BookDto {
	private Long id;
	private String title;
}
