package bookcafe.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bookcafe.data.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	
	Optional<Book> findById(long id);
}
