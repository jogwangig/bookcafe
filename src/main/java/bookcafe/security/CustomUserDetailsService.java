package bookcafe.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import bookcafe.data.entity.SiteUser;
import bookcafe.data.entity.SiteUserAuthority;
import bookcafe.data.entity.SiteUserAuthority.AuthorityType;
import bookcafe.data.repository.SiteUserAuthorityRepository;
import bookcafe.data.repository.SiteUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	SiteUserRepository userRepo;
	
	@Autowired
	SiteUserAuthorityRepository userAuthorityRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		SiteUser user = userRepo.findByUsername(username);
		
		List<SiteUserAuthority> userAuthorities = userAuthorityRepo.findByUserOwningAuthority(user);
		
		List<SimpleGrantedAuthority> authorities = userAuthorities.stream().map(SiteUserAuthority::getAuthorityType)
																		.map(AuthorityType::name)
																		.map(SimpleGrantedAuthority::new)
																		.toList();
		
		System.out.println(new CustomUserDetails(user, authorities));
		return new CustomUserDetails(user, authorities);
//		return new User(user.getUsername(), user.getPassword(), authorities);
	}
	
}
