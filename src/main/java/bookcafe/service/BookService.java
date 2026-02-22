package bookcafe.service;

import org.springframework.stereotype.Service;

import bookcafe.data.dto.creation.BookCreationDto;
import bookcafe.data.entity.Book;
import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.BookShelfRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookService {
	
	BookRepository bookRepo;
	
	BookShelfRepository bookShelfRepo;
	
//	public void createNewBook(BookInfo bookInfo, long bookShelfId, MultipartFile coverImg) {
//		
//		try {
//			
//			Book newBook =	Book.builder().bookInfo(bookInfo).
//										bookShelf(bookShelfRepo.getReferenceById(bookShelfId)).build();
//			
//			if(!coverImg.isEmpty()) newBook.setCoverImage(coverImg.getBytes());
//			
//			bookRepo.save(newBook);
//	
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void createNewBook(BookCreationDto creationDto) {
		
		Book book = creationDto.toEntity();
		
		book.setBookShelf(bookShelfRepo.getReferenceById(creationDto.getBookShelfId()));
		
		bookRepo.save(book);
		
	}
	
	
	public void modifyBookInfo(BookCreationDto creationDto, long bookId ) {
		
			
			Book book =	bookRepo.findById(bookId).get();
					
			Book b = creationDto.toEntity();
			
			book.setBookInfo(b.getBookInfo());
			
			if(!creationDto.getCoverImg().isEmpty())
				book.setCoverImage(b.getCoverImage());
			
			book.setBookShelf(bookShelfRepo.getReferenceById(creationDto.getBookShelfId()));
			
			bookRepo.save(book);
	
	}

}
