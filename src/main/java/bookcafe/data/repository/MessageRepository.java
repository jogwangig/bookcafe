package bookcafe.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	long countByReceipientId(long receipientId);
	List<Message> findByReceipientId(long receipientId);
}
