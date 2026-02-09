package bookcafe.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
	
	Optional<Book> findById(long id);
}
