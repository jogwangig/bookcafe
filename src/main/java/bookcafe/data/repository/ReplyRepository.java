package bookcafe.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long>{

}
