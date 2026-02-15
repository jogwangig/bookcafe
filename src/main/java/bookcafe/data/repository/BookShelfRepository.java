package bookcafe.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bookcafe.data.dto.BookShelfDto;
import bookcafe.data.entity.BookShelf;

@Repository
public interface BookShelfRepository extends JpaRepository<BookShelf, Long>{
	@Query("SELECT bs FROM BookShelf bs LEFT JOIN FETCH bs.books WHERE bs.user.id = :userId")
	List<BookShelf> findByUserId(@Param("userId") Long userId);
	
	@Query("SELECT new bookcafe.data.dto.BookShelfDto(bs.id, bs.name, b.id, b.bookInfo.title) "
			+"FROM BookShelf bs LEFT JOIN bs.books b "
			+"WHERE bs.user.id = :userId")
	List<BookShelfDto> findByUserIdWithBooks(@Param("userId") Long userId);
}
