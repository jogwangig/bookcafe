package bookcafe.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bookcafe.data.dto.ReadingRecordDto;
import bookcafe.data.dto.creation.ReadingRecordCreationDto;
import bookcafe.data.entity.ReadingRecord;

@Repository
public interface ReadingRecordRepository extends JpaRepository<ReadingRecord, Long>{
	List<ReadingRecord> findByBookId(Long id);
	List<ReadingRecord> findByUserId(Long id);
	
	@Query("SELECT new bookcafe.data.dto.ReadingRecordDto(rr.id ,rr.cratedAt, rr.content, b.bookInfo.title) FROM ReadingRecord rr "+
		      "LEFT JOIN rr.book b WHERE rr.user.id = :userId")
	Page<ReadingRecordDto> findByUserId(@Param("userId") Long id , Pageable pageable);
	
	@Query("SELECT new bookcafe.data.dto.ReadingRecordDto(rr.id , rr.cratedAt, rr.content, b.bookInfo.title) FROM ReadingRecord rr "+
	      "LEFT JOIN rr.book b WHERE b.id = :bookId")
	Page<ReadingRecordDto> findByBookId(@Param("bookId") Long id , Pageable pageable);
	
	@Query("SELECT new bookcafe.data.dto.creation.ReadingRecordCreationDto(rr.content, rr.book.id) "
			+"FROM ReadingRecord rr WHERE rr.id = :id")
	ReadingRecordCreationDto findCreationDtoById(@Param("id")Long id);

}
