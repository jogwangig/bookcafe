package bookcafe.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bookcafe.data.dto.BookClubDetailDto;
import bookcafe.data.dto.display.BookClubDisplayDto;
import bookcafe.data.entity.BookClub;

@Repository
public interface BookClubRepository extends JpaRepository<BookClub, Long>{
	
	@Query("SELECT new bookcafe.data.dto.display.BookClubDisplayDto(bc.id ,bc.name ,bc.bookInfo.title , "+
			"CASE WHEN bcp IS NULL THEN false ELSE true END) "+
			"FROM BookClub bc "+
			"LEFT JOIN BookClubParticipant bcp ON bcp.bookClub.id = bc.id and bcp.user.id = :userId ")
	List<BookClubDisplayDto> findAllDisplayDtos(@Param("userId")long userId);
	
	@Query("SELECT new bookcafe.data.dto.display.BookClubDisplayDto( bc.id ,bc.name , null , false ) "+
			"FROM BookClub bc "+
			"WHERE bc.id = :id ")
	BookClubDisplayDto findDisplayDtoByIdForOnlyName(@Param("id")Long id);
	
	
	@Query("SELECT new bookcafe.data.dto.BookClubDetailDto(bc.id, bc.name, bc.bookInfo.title, bc.bookInfo.ISBN, bc.coverImage) "
			+"FROM BookClub bc WHERE bc.id = :id")
	BookClubDetailDto findDetailDtoById(@Param("id")Long id);
}
