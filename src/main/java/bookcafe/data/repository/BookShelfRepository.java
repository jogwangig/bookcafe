package bookcafe.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bookcafe.data.dto.BookShelfWithBooksDto;
import bookcafe.data.dto.BookShelfWithBooksFlatDto;
import bookcafe.data.entity.BookShelf;

@Repository
public interface BookShelfRepository extends JpaRepository<BookShelf, Long>{
	@Query("SELECT bs FROM BookShelf bs LEFT JOIN FETCH bs.books WHERE bs.user.id = :userId")
	List<BookShelf> findByUserId(@Param("userId") Long userId);
	
	
	@Query("SELECT new bookcafe.data.dto.BookShelfWithBooksDto(bs.id, bs.name, null) "
			+ "FROM BookShelf bs WHERE bs.user.id = :userId")
	List<BookShelfWithBooksDto> findDtoByUserId(@Param("userId") Long userId);
	
	
	@Query("SELECT new bookcafe.data.dto.BookShelfWithBooksFlatDto(bs.id, bs.name, b.id, b.bookInfo.title) "
			+"FROM BookShelf bs LEFT JOIN bs.books b "
			+"WHERE bs.user.id = :userId")
	List<BookShelfWithBooksFlatDto> findByUserIdWithBooks(@Param("userId") Long userId);
}
