package bookcafe.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import bookcafe.data.entity.SiteUser;


@Configuration
@EnableJpaAuditing
public class AuditingConfig {
	
	@Bean
	public AuditorAware<SiteUser> siteUserAuditorProvider(){
		return new SiteUserAuditorAware();
	}
	

	
	private static class SiteUserAuditorAware implements AuditorAware<SiteUser>{
		
		@Override
		public Optional<SiteUser> getCurrentAuditor() {
			return Optional.ofNullable(SiteUser.builder().build());
		}
	}
 
}
