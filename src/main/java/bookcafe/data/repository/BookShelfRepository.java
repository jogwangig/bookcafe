package bookcafe.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bookcafe.data.dto.creation.BookShelfCreationDto;
import bookcafe.data.dto.display.BookShelfWithBooksDisplayDto;
import bookcafe.data.dto.display.BookShelfWithBooksDisplayFlatDto;
import bookcafe.data.entity.BookShelf;

@Repository
public interface BookShelfRepository extends JpaRepository<BookShelf, Long>{
	@Query("SELECT bs FROM BookShelf bs LEFT JOIN FETCH bs.books WHERE bs.user.id = :userId")
	List<BookShelf> findByUserId(@Param("userId") Long userId);
	
	
	@Query("SELECT new bookcafe.data.dto.display.BookShelfWithBooksDisplayDto(bs.id, bs.name, null) "
			+ "FROM BookShelf bs WHERE bs.user.id = :userId")
	List<BookShelfWithBooksDisplayDto> findAllDisplayDtosByUserIdForOnlyName(@Param("userId") Long userId);
	
	
	@Query("SELECT new bookcafe.data.dto.display.BookShelfWithBooksDisplayFlatDto(bs.id, bs.name, "
			+ "CASE WHEN b IS NOT NULL THEN b.id ELSE null END, "
			+ "CASE WHEN b IS NOT NULL THEN b.bookInfo.title ELSE null END "
			+ ") "
			+"FROM BookShelf bs LEFT JOIN bs.books b "
			+"WHERE bs.user.id = :userId")
	List<BookShelfWithBooksDisplayFlatDto> findAllDisplayDtosWithBooksByUserId(@Param("userId") Long userId);
	
	
	@Query("SELECT new bookcafe.data.dto.display.BookShelfWithBooksDisplayFlatDto(bs.id, bs.name, "
			+ "CASE WHEN b IS NOT NULL THEN b.id ELSE null END, "
			+ "CASE WHEN b IS NOT NULL THEN b.bookInfo.title ELSE null END "
			+ ") "
			+"FROM BookShelf bs LEFT JOIN bs.books b "
			+"WHERE bs.id = :id")
	List<BookShelfWithBooksDisplayFlatDto> findDisplayDtosWithBooksById(@Param("id") Long id);
	
	
	@Query("SELECT new bookcafe.data.dto.creation.BookShelfCreationDto(bs.name) FROM BookShelf bs WHERE bs.id = :id")
	BookShelfCreationDto findCreationDtoById(@Param("id") Long id);
}
