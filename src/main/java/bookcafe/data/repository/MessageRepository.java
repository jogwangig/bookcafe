package bookcafe.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	
	@Query("SELECT count(*) FROM Message m WHERE m.isRead = false and m.receipient.id = :receipientId")
	long countUnreadMsgByReceipientId(@Param("receipientId") long receipientId);
	
	
	List<Message> findByReceipientId(long receipientId);
}
