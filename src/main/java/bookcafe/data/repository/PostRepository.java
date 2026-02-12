package bookcafe.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findByBoardId(long id);
}
