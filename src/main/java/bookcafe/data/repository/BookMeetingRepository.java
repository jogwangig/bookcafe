package bookcafe.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.BookMeeting;

@Repository
public interface BookMeetingRepository extends JpaRepository<BookMeeting, Long>{

}
