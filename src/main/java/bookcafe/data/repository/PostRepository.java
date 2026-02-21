package bookcafe.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bookcafe.data.dto.PostDto;
import bookcafe.data.dto.display.PostPageDto;
import bookcafe.data.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findByBoardId(long id);
	
	@Query("SELECT new bookcafe.data.dto.display.PostPageDto(p.id, p.cratedAt, u.username, p.anonymousUsername, p.anonymousUserPwd, p.title) "
			+"FROM Post p LEFT JOIN p.user u "
			+"WHERE p.board.id = :boardId")
	Page<PostPageDto> findPostPageByBoardId(@Param("boardId") long id, Pageable pageable);
	
	@Query("SELECT new bookcafe.data.dto.PostDto(p.id, p.cratedAt , p.board.id , "
			+ "CASE WHEN u IS NOT NULL THEN u.username ELSE null END , "
			+ "CASE WHEN u IS NOT NULL THEN null ELSE p.anonymousUsername END ,"
			+ "p.title , p.content ) "
			+"FROM Post p LEFT JOIN p.user u "
			+"WHERE p.id = :postId")
	PostDto findPostDtoById(@Param("postId")long postId);
}
