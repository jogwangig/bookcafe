package bookcafe.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bookcafe.data.dto.creation.BookCreationDto;
import bookcafe.data.dto.display.BookDisplayDto;
import bookcafe.data.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
	
	Optional<Book> findById(long id);
	List<Book> findByBookShelfId(long id);
	List<Book> findByUserId(Long id);
	long countByBookShelfId(Long bookShelfId);
	
	@Query("SELECT new bookcafe.data.dto.display.BookDisplayDto(b.id, b.bookInfo.title) FROM Book b WHERE b.user.id = :userId")
	List<BookDisplayDto> findDisplayDtosByUserId(@Param("userId")long userId);
	
	@Query("SELECT new bookcafe.data.dto.creation.BookCreationDto(b.bookInfo.title, b.bookInfo.ISBN, null, b.bookShelf.id) "+
			"FROM Book b WHERE b.id = :id")
	BookCreationDto findCreationDtoById(@Param("id")Long id);
	
	
}
