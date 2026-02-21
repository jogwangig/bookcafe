package bookcafe.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bookcafe.data.dto.display.BoardDto;
import bookcafe.data.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
	Board findByName(String name);
	
	@Query("SELECT new bookcafe.data.dto.display.BoardDto(b.id, b.name) FROM Board b WHERE b.id = :boardId")
	BoardDto findDtoById(@Param("boardId")long boardId);
	
	@Query("SELECT new bookcafe.data.dto.display.BoardDto(b.id, b.name) FROM Board b")
	List<BoardDto> findAllDtos();
}
