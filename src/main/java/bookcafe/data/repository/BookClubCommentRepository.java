package bookcafe.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.BookClubComment;

@Repository
public interface BookClubCommentRepository extends JpaRepository<BookClubComment, Long>{

}
