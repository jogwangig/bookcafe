package bookcafe.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.SiteUser;

@Repository
public interface SiteUserRepository extends JpaRepository<SiteUser, Long>{

	Optional<SiteUser> findById(Long id);
	SiteUser findByUsername(String username);
}
