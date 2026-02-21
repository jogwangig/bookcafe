package bookcafe.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.BookClubComment;

@Repository
public interface BookClubCommentRepository extends JpaRepository<BookClubComment, Long>{
	List<BookClubComment> findByBookClubId(long bookClubId);
}
