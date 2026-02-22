package bookcafe.data.dto.creation;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import bookcafe.data.entity.Book;
import bookcafe.data.valueobject.BookInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreationDto implements CreationDto<Book>{
	
	private String title;
	
	private String isbn;
	
	private MultipartFile coverImg;
	
	private Long bookShelfId;
	
	public Book toEntity() {
		BookInfo bookInfo = BookInfo.builder().title(title)
											  .ISBN(isbn).build();
		try {
			Book book = Book.builder().bookInfo(bookInfo).build();
			
			if(!coverImg.isEmpty())
				book.setCoverImage(coverImg.getBytes());
			
			return book;
			
		}catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

}
