package bookcafe.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bookcafe.data.entity.SiteUser;
import bookcafe.data.entity.SiteUserAuthority;

public interface SiteUserAuthorityRepository extends JpaRepository<SiteUserAuthority, Long>{
	List<SiteUserAuthority> findByUser(SiteUser user);
}
