package bookcafe.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.BookShelf;

@Repository
public interface BookShelfRepository extends JpaRepository<BookShelf, Long>{

}
