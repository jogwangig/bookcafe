package bookcafe.data.dto;

import org.springframework.web.multipart.MultipartFile;

import bookcafe.data.valueobject.BookInfo;
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
public class BookMeetingDto {
	private String name;
	
	private BookInfo bookInfo;
	
	private MultipartFile coverImage;
}
