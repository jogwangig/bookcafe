package bookcafe.data.dto.display;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BookShelfWithBooksDto {
	private long id;
	private String name;
	private List<BookDto> books;
}
