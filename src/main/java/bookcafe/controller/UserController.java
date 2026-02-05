package bookcafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	SiteUserRepository userRepo;
	
	@Autowired
	SiteUserAuthorityRepository userAuthorityRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	BookShelfRepository bookShelfRepo;
	
	@GetMapping("/create")
	public String createUser(Model model) {
		model.addAttribute("createUserFormDTO", new SiteUser.SiteUserDTO());
		return "create_user_form";
	}
	
	
	
	@PostMapping("/create")
	public String processCreateUserForm(@ModelAttribute("createUserFormDTO") SiteUserDTO createUserFormDTO) {
		
		System.out.println(createUserFormDTO);
		
		createUserFormDTO.setPassword(
				passwordEncoder.encode(createUserFormDTO.getPassword())
				);
		
		SiteUser newUser = SiteUser.newSiteUserFromDTO(createUserFormDTO);
		
		System.out.println(newUser);
		
		SiteUserAuthority userAuthority = SiteUserAuthority.builder().userOwningAuthority(newUser)
															.authorityType(AuthorityType.NORMAL).build();
				
		AuditingConfig.setNewUser(
				userRepo.save(newUser)
				);
		
		userAuthorityRepo.save(userAuthority);
		
		System.out.println(
				bookShelfRepo.save(BookShelf.builder().name("default").build())
		);
				
		AuditingConfig.flushNewUser();

		return "redirect:/";
	}
	
	
	
	
	@GetMapping("/delete")
	public String deleteUser(@RequestParam("id") long id) {
		userRepo.deleteById(id);
		return "redirect:/";
	}

}
