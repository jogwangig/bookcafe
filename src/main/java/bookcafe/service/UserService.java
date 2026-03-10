package bookcafe.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookcafe.data.dto.creation.UserCreationDto;
import bookcafe.data.entity.BookShelf;
import bookcafe.data.entity.SiteUser;
import bookcafe.data.entity.SiteUserAuthority;
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
	
	private EmailService emailService;
	
	
	@Transactional
	public void createNewUser(UserCreationDto userCreationDto) {
		
		if(userRepo.existsByUsername(userCreationDto.getUsername()))
			throw new RuntimeException("사용이 불가능한 아이디입니다.");
		
		if(!emailService.isVerifiedEmailAddress(userCreationDto.getEmailAddress()))
			throw new RuntimeException("인증되지 않은 이메일 입니다.");
		
		encodeUserPwd(userCreationDto);
		
		SiteUser newUser = userCreationDto.toEntity();
					
		userRepo.save(newUser);
		
		setSecurityContextByNewUser(newUser);
		
		doUserDefaultSetting();
		
	}
	
	public void modifyUserInfo(UserCreationDto userDTO, long userId) {
		
		encodeUserPwd(userDTO);
		
		SiteUser user = userRepo.findById(userId).get();
		
		user.setUsername(userDTO.getUsername());
		
		user.setPassword(userDTO.getPassword());
		
		user.setNickName(userDTO.getNickName());
				
		userRepo.save(user);
		
		
	}
	
	
	private void setSecurityContextByNewUser(SiteUser newUser) {
		
		SecurityContext ctx = SecurityContextHolder.createEmptyContext();
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getUsername());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "" ,userDetails.getAuthorities());
		ctx.setAuthentication(authentication);
		SecurityContextHolder.setContext(ctx);
	}
	
	private void encodeUserPwd(UserCreationDto userDto) {
		String pwd = userDto.getPassword();
		pwd = passwordEncoder.encode(pwd);
		userDto.setPassword(pwd);
		
	}
	
	private void doUserDefaultSetting() {
		
		SiteUserAuthority defaultAuthority = SiteUserAuthority.builder()
											.authorityType(AuthorityType.NORMAL).build();

		BookShelf defaultBookShelf = BookShelf.builder().name("default").build();


		userAuthorityRepo.save(defaultAuthority);
			
		bookShelfRepo.save(defaultBookShelf);
	}

}
