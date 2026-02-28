package bookcafe.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bookcafe.data.dto.creation.CommentCreationDto;
import bookcafe.data.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPostId(long id);
	
	@Query("SELECT new bookcafe.data.dto.creation.CommentCreationDto(null , null , c.content) "+
			"FROM Comment c WHERE c.id = :id")
	CommentCreationDto findCreationDtoById(@Param("id")Long id);
}
