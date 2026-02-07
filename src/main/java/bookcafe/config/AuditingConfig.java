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
				
			
			Authentication p = SecurityContextHolder.getContext().getAuthentication();
//			userRepo.getReferenceById(userId);
			
			if(p == null || p.getName().equals("anonymousUser")) {
//				System.out.println("Authentication이 존재하지 않아 AuditorAware<SiteUser> 를 반환 할 수 없습니다.");
				return Optional.empty();
			}
			
			if(p.getPrincipal() instanceof CustomUserDetails) {
//				System.out.println("현재 반환된 AuditorAware<SiteUser> 는 인증된 사용자입니다");
				
				CustomUserDetails a = (CustomUserDetails)p.getPrincipal();
				SiteUser u = userRepo.findByUsername(a.getUsername());
				return Optional.ofNullable(u);
			}
			
			System.out.println("AuditorAware<SiteUser> 반환에 문제가 생겼습니다");
			return Optional.empty();
				
		}
	}
	
 
}
