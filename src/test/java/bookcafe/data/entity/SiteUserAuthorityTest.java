package bookcafe.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import bookcafe.config.AuditingConfig;
import bookcafe.config.SecurityConfig;
import bookcafe.data.entity.SiteUserAuthority.AuthorityType;
import bookcafe.data.repository.SiteUserAuthorityRepository;
import bookcafe.data.repository.SiteUserRepository;
import bookcafe.security.CustomUserDetailsService;
import jakarta.persistence.EntityManager;

@DataJpaTest
@Import({AuditingConfig.class, CustomUserDetailsService.class})
public class SiteUserAuthorityTest {
	
	@Autowired
	SiteUserRepository userRepo;
	
	@Autowired
	SiteUserAuthorityRepository userAuthorityRepo;
	
	@Autowired
	EntityManager em;
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Test
	void createSiteUserAuthorityTest() {
		SiteUser user = SiteUser.builder().username("a").password("b").nickName("c").build();
		userRepo.save(user);
		em.flush();
		em.clear();
		
		SecurityContext ctx = SecurityContextHolder.createEmptyContext();
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "" ,userDetails.getAuthorities());
		ctx.setAuthentication(authentication);
		SecurityContextHolder.setContext(ctx);
		
		SiteUserAuthority userAuthority = SiteUserAuthority.builder()
												.authorityType(AuthorityType.NORMAL).build();
		
		userAuthorityRepo.save(userAuthority);
		
		em.flush();
		em.clear();
		
		userAuthority = userAuthorityRepo.findById(userAuthority.getId()).get();
		assertEquals(AuthorityType.NORMAL, userAuthority.getAuthorityType());
		
		
		
		
		
		
		
	}

}
