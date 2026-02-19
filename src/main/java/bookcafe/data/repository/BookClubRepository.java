package bookcafe.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.BookClub;

@Repository
public interface BookClubRepository extends JpaRepository<BookClub, Long>{

}
