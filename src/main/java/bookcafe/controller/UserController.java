package bookcafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.config.AuditingConfig;
import bookcafe.data.entity.BookShelf;
import bookcafe.data.entity.SiteUser;
import bookcafe.data.entity.SiteUser.SiteUserDTO;
import bookcafe.data.entity.SiteUserAuthority;
import bookcafe.data.entity.SiteUserAuthority.AuthorityType;
import bookcafe.data.repository.BookShelfRepository;
import bookcafe.data.repository.SiteUserAuthorityRepository;
import bookcafe.data.repository.SiteUserRepository;
import bookcafe.security.CustomUserDetails;
import bookcafe.security.CustomUserDetailsService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
	
	SiteUserRepository userRepo;
	
	SiteUserAuthorityRepository userAuthorityRepo;
	
	PasswordEncoder passwordEncoder;
	
	BookShelfRepository bookShelfRepo;
	
	CustomUserDetailsService userDetailsService;
	
	@GetMapping("/create")
	public String createUser(Model model) {
		model.addAttribute("createUserFormDTO", new SiteUser.SiteUserDTO());
		return "create_user_form";
	}
	
	
	
	@PostMapping("/create")
	public String processCreateUserForm(@ModelAttribute("createUserFormDTO") SiteUserDTO createUserFormDTO) {
		
		
		createUserFormDTO.setPassword(
				passwordEncoder.encode(createUserFormDTO.getPassword())
				);
		
		SiteUser newUser = SiteUser.newSiteUserFromDTO(createUserFormDTO);
		

		
		SiteUserAuthority userAuthority = SiteUserAuthority.builder()
											.authorityType(AuthorityType.NORMAL).build();
				
		userRepo.save(newUser);
		
		
		SecurityContext ctx = SecurityContextHolder.createEmptyContext();
		
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getUsername());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "" ,userDetails.getAuthorities());
		ctx.setAuthentication(authentication);
		SecurityContextHolder.setContext(ctx);
		
		userAuthorityRepo.save(userAuthority);
		
		bookShelfRepo.save(BookShelf.builder().name("default").build());
				

		return "redirect:/";
	}
	
	
	
	
	@GetMapping("/delete")
	public String deleteUser(@RequestParam("id") long id) {
		userRepo.deleteById(id);
		return "redirect:/";
	}

}
