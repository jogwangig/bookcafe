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
	
	private static SiteUser user;
	
	@Bean
	public AuditorAware<SiteUser> siteUserAuditorProvider(){
		return new SiteUserAuditorAware();
	}
	
	public static void setNewUser(SiteUser newUser) {
		user = newUser;
	}
	
	public static void flushNewUser() {
		user = null;
	}
	
	private static boolean isNewUser() {
		return user != null;
	}
	

	
	private static class SiteUserAuditorAware implements AuditorAware<SiteUser>{
		@Autowired
		SiteUserRepository userRepo;
		
		@Override
		public Optional<SiteUser> getCurrentAuditor() {
				
			
			if(isNewUser()) {
				System.out.println("현재 반환된 AuditorAware<SiteUser> 는 새롭게 생성된 사용자입니다");
				return Optional.ofNullable(user);
			}
			
			Authentication p = SecurityContextHolder.getContext().getAuthentication();
		
			if(p.getName().equals("anonymousUser")) {
				System.out.println("현재 반환된 AuditorAware<SiteUser> 는 익명의 사용자입니다");
				return Optional.empty();
			}
			
			if(p.getPrincipal() instanceof CustomUserDetails) {
				System.out.println("현재 반환된 AuditorAware<SiteUser> 는 인증된 사용자입니다");
				
				CustomUserDetails a = (CustomUserDetails)p.getPrincipal();
				SiteUser u = userRepo.findByUsername(a.getUsername());
				return Optional.ofNullable(u);
			}
			System.out.println("AuditorAware<SiteUser> 반환에 문제가 생겼습니다");
			return Optional.empty();
				
		}
	}
	
 
}
