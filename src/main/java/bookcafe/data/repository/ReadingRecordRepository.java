package bookcafe.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.ReadingRecord;

@Repository
public interface ReadingRecordRepository extends JpaRepository<ReadingRecord, Long>{

}
