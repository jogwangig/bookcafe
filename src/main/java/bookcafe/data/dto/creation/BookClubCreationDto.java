package bookcafe.data.dto.creation;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import bookcafe.data.entity.BookClub;
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
public class BookClubCreationDto implements CreationDto<BookClub>{
	private String name;
	
	private String title;
	
	private String isbn;
	
	private MultipartFile coverImg;
	
	public BookClub toEntity() {
		
		BookInfo bookInfo = BookInfo.builder().title(title)
												.ISBN(isbn).build();
		
		try {
			BookClub bookClub = BookClub.builder().name(name)
							.bookInfo(bookInfo).build();
			
			if(!coverImg.isEmpty())
				bookClub.setCoverImage(coverImg.getBytes());
			
			return bookClub;
			
		}catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
}
