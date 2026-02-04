package bookcafe.config;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import bookcafe.data.entity.SiteUser;
import bookcafe.data.repository.SiteUserRepository;


@Configuration
@EnableJpaAuditing
public class AuditingConfig {
	
	public static SiteUser user;
	
	@Bean
	public AuditorAware<SiteUser> siteUserAuditorProvider(){
		return new SiteUserAuditorAware();
	}
	

	
	private static class SiteUserAuditorAware implements AuditorAware<SiteUser>{
		@Autowired
		SiteUserRepository userRepo;
		
		@Override
		public Optional<SiteUser> getCurrentAuditor() {
				
			Authentication p = SecurityContextHolder.getContext().getAuthentication();

			if(p == null || p.getName().equals("anonymousUser") || user != null) {
				SiteUser userTemp = user;
				user = null;
				return Optional.ofNullable(userTemp);
				}
			
				return Optional.ofNullable(p)
		            .filter(Authentication::isAuthenticated)
		            .map(Authentication::getPrincipal)
		            .map((c)->userRepo.findByUsername(((UserDetails)c).getUsername()));
				
		}
	}
 
}
