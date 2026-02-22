package bookcafe.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BookClubDetailDto {
	
	private Long id;

	private String name;
	
	private String bookTitle;
	
	private String bookIsbn;
	
	private byte[] coverImg;
}
