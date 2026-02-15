package bookcafe.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@AllArgsConstructor
public class BookShelfDto {
	private long id;
	private String name;
	private long bookId;
	private String bookTitle;

}
