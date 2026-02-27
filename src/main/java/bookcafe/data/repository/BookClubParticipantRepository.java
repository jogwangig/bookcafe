package bookcafe.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bookcafe.data.entity.BookClubParticipant;

@Repository
public interface BookClubParticipantRepository extends JpaRepository<BookClubParticipant, Long> {
	Optional<BookClubParticipant> findByUserIdAndBookClubId(long userId, long bookClubId);
	
	List<BookClubParticipant> findByBookClubId(long bookClubId);
	
	@Transactional
	void deleteByUserIdAndBookClubId(long userId, long bookClubId);
}
