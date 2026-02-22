package bookcafe.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bookcafe.data.entity.Book;
import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.BookShelfRepository;
import bookcafe.data.valueobject.BookInfo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookService {
	
	BookRepository bookRepo;
	
	BookShelfRepository bookShelfRepo;
	
	public void createNewBook(BookInfo bookInfo, long bookShelfId, MultipartFile coverImg) {
		
		try {
			
			Book newBook =	Book.builder().bookInfo(bookInfo).
										bookShelf(bookShelfRepo.getReferenceById(bookShelfId)).build();
			
			if(!coverImg.isEmpty()) newBook.setCoverImage(coverImg.getBytes());
			
			bookRepo.save(newBook);
	
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void modifyBookInfo(BookInfo bookInfo, long bookShelfId, long bookId,MultipartFile coverImg) {
		
		try {
			
			Book book =	bookRepo.findById(bookId).get();
					
					//Book.builder().bookInfo(bookInfo).
						//				bookShelf(bookShelfRepo.getReferenceById(bookShelfId)).build();
			
			if(!coverImg.isEmpty()) book.setCoverImage(coverImg.getBytes());
			
			book.setBookInfo(bookInfo);
			
			book.setBookShelf(bookShelfRepo.getReferenceById(bookShelfId));
			
			bookRepo.save(book);
	
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
