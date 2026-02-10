package bookcafe.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
	
	Optional<Book> findById(long id);
	List<Book> findByBookShelfId(long id);
	List<Book> findByUserId(Long id);
}
