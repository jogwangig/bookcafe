package bookcafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.data.entity.SiteUser;
import bookcafe.data.entity.SiteUser.SiteUserDTO;
import bookcafe.data.repository.SiteUserRepository;
import bookcafe.service.UserService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
	
	SiteUserRepository userRepo;

	
	private UserService userService;
	
	@GetMapping("/create")
	public String getUserCreationForm(Model model) {
		model.addAttribute("createUserFormDTO", new SiteUser.SiteUserDTO());
		return "/form/user-creation-form";
	}
	
	
	
	@PostMapping("/create")
	public String processUserCreationForm(@ModelAttribute("createUserFormDTO") SiteUserDTO createUserFormDTO) {
		
		userService.createNewUser(createUserFormDTO);		

		return "redirect:/";
	}
	
	
	
	
	@GetMapping("/delete")
	public String deleteUser(@RequestParam("id") long id) {
		userRepo.deleteById(id);
		return "redirect:/";
	}

}
