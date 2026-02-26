package bookcafe.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import bookcafe.data.entity.SiteUser;
import bookcafe.data.repository.SiteUserRepository;
import bookcafe.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;


@Configuration
@EnableJpaAuditing
public class AuditingConfig {
		
	@Bean
	public AuditorAware<SiteUser> siteUserAuditorProvider(){
		return new SiteUserAuditorAware();
	}
	

	
	@Slf4j
	private static class SiteUserAuditorAware implements AuditorAware<SiteUser>{
		@Autowired
		SiteUserRepository userRepo;
		
		@Override
		public Optional<SiteUser> getCurrentAuditor() {
				
			
			Authentication p = SecurityContextHolder.getContext().getAuthentication();
			
			if(p.getPrincipal() instanceof CustomUserDetails) {
				
				CustomUserDetails c = (CustomUserDetails)p.getPrincipal();
				SiteUser u = userRepo.getReferenceById(c.getId());
				return Optional.ofNullable(u);
			}
			log.info("익명 사용자가 아이템을 생성했습니다");
			return Optional.empty();
				
		}
	}
	
 
}
