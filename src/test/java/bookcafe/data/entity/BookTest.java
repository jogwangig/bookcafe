package bookcafe.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import bookcafe.config.AuditingConfig;
import bookcafe.data.repository.BookRepository;
import bookcafe.data.valueobject.BookInfo;

@DataJpaTest
@Import(AuditingConfig.class)
public class BookTest {
	
	@Autowired
	BookRepository bookRepo;
	
	@Test
	void bookSaveAndFindWithAuditTest() {

		
		Book book = new Book.BookBuilder()
				.numberOfReadingRecord(5).
				bookInfo(BookInfo.builder().title("title").ISBN("ISBN").build()).build();
		
		bookRepo.save(book);
		
		Optional<Book> foundBook = bookRepo.findById(1L);
		
		if(foundBook.isPresent()) {
			assertEquals(5, foundBook.get().getNumberOfReadingRecord());
			assertEquals("title", foundBook.get().getBookInfo().getTitle());
		}
		
		
	}

}
