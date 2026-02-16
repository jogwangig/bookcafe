package bookcafe.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.ReadingRecord;

@Repository
public interface ReadingRecordRepository extends JpaRepository<ReadingRecord, Long>{
	List<ReadingRecord> findByBookId(Long id);
	List<ReadingRecord> findByUserId(Long id);
	Page<ReadingRecord> findByUserId(Long id , Pageable pageable);

}
