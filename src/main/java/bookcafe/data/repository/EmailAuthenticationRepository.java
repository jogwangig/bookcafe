package bookcafe.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookcafe.data.entity.EmailAuthentication;

@Repository
public interface EmailAuthenticationRepository extends JpaRepository<EmailAuthentication, Long>{
	Optional<EmailAuthentication> findByEmailAddress(String emailAddress);
}
