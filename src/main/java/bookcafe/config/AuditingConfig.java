package bookcafe.config;

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
	
	@Bean
	public AuditorAware<SiteUser> siteUserAuditorProvider(){
		return new SiteUserAuditorAware();
	}
	

	
	private static class SiteUserAuditorAware implements AuditorAware<SiteUser>{
		@Autowired
		SiteUserRepository userRepo;
		
		@Override
		public Optional<SiteUser> getCurrentAuditor() {
		    return Optional.ofNullable(SecurityContextHolder.getContext())
		            .map(SecurityContext::getAuthentication)
		            .filter(Authentication::isAuthenticated)
		            .map(Authentication::getPrincipal)
		            .map((p)->userRepo.findByUserId(((UserDetails)p).getUsername()));
		}
	}
 
}
