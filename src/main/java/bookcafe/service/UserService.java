package bookcafe.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bookcafe.data.entity.BookShelf;
import bookcafe.data.entity.SiteUser;
import bookcafe.data.entity.SiteUserAuthority;
import bookcafe.data.entity.SiteUser.SiteUserDTO;
import bookcafe.data.entity.SiteUserAuthority.AuthorityType;
import bookcafe.data.repository.BookShelfRepository;
import bookcafe.data.repository.SiteUserAuthorityRepository;
import bookcafe.data.repository.SiteUserRepository;
import bookcafe.security.CustomUserDetailsService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	
	private SiteUserRepository userRepo;
	
	private SiteUserAuthorityRepository userAuthorityRepo;
	
	private PasswordEncoder passwordEncoder;
	
	private BookShelfRepository bookShelfRepo;
	
	private CustomUserDetailsService userDetailsService;
	
	public void createNewUser(SiteUserDTO userDTO) {
		
		userDTO.setPassword(
				passwordEncoder.encode(userDTO.getPassword())
				);
		
		SiteUser newUser = SiteUser.newSiteUserFromDTO(userDTO);
	
		
		SiteUserAuthority userAuthority = SiteUserAuthority.builder()
											.authorityType(AuthorityType.NORMAL).build();
				
		userRepo.save(newUser);
		
		setSecurityContextByNewUser(newUser);
		
		userAuthorityRepo.save(userAuthority);
		
		bookShelfRepo.save(BookShelf.builder().name("default").build());
		
	}
	
	private void setSecurityContextByNewUser(SiteUser newUser) {
		
		SecurityContext ctx = SecurityContextHolder.createEmptyContext();
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getUsername());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "" ,userDetails.getAuthorities());
		ctx.setAuthentication(authentication);
		SecurityContextHolder.setContext(ctx);
	}

}
