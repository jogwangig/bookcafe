package bookcafe.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.BookShelf;

@Repository
public interface BookShelfRepository extends JpaRepository<BookShelf, Long>{
	List<BookShelf> findBySiteUserId(Long id);
}
