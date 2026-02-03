package bookcafe.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import bookcafe.config.AuditingConfig;
import bookcafe.data.entity.SiteUserAuthority.AuthorityType;
import bookcafe.data.repository.SiteUserAuthorityRepository;
import bookcafe.data.repository.SiteUserRepository;
import jakarta.persistence.EntityManager;

@DataJpaTest
@Import(AuditingConfig.class)
public class SiteUserAuthorityTest {
	
	@Autowired
	SiteUserRepository userRepo;
	
	@Autowired
	SiteUserAuthorityRepository userAuthorityRepo;
	
	@Autowired
	EntityManager em;
	
	@Test
	void createSiteUserAuthorityTest() {
		SiteUser user = SiteUser.builder().userId("a").password("b").nickName("c").build();
		userRepo.save(user);
		em.flush();
		em.clear();
		
		user = userRepo.findById(user.getId()).get();
		
		SiteUserAuthority userAuthority = SiteUserAuthority.builder().userOwningAuthority(user)
																		.authorityType(AuthorityType.NORMAL).build();
		
		userAuthorityRepo.save(userAuthority);
		
		em.flush();
		em.clear();
		
		userAuthority = userAuthorityRepo.findById(userAuthority.getId()).get();
		assertEquals(AuthorityType.NORMAL, userAuthority.getAuthorityType());
		
		
		
		
		
		
		
	}

}
