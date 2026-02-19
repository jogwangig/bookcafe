package bookcafe.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@AllArgsConstructor
public class BookShelfWithBooksFlatDto {
	private Long id;
	private String name;
	private Long bookId;
	private String bookTitle;

}
