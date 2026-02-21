package bookcafe.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	long countByReceipientId(long receipientId);
}
