package bookcafe.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import bookcafe.config.AuditingConfig;
import bookcafe.data.repository.SiteUserRepository;

@DataJpaTest
@Import(AuditingConfig.class)
public class SiteUserTest {
	
	@Autowired
	private SiteUserRepository siteUserRepo;
	
	@Test
	void siteUserSaveAndFindTest() {
		SiteUser user = SiteUser.builder()
								.userId("id")
								.password("pwd")
								.nickName("nickName").build();
		
		siteUserRepo.save(user);
		Optional<SiteUser> op = siteUserRepo.findById(1L);
		
		if(op.isPresent()) {
			SiteUser foundUser = op.get();
			assertEquals(1, foundUser.getId());
			System.out.println(foundUser);
			
		}
	}

}
