package bookcafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.data.entity.SiteUser;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@GetMapping("/create")
	public String createUser(Model model) {
		model.addAttribute("createUserFormDTO", new SiteUser.SiteUserDTO());
		return "create_user_form";
	}
	
	@PostMapping("/create")
	public String processCreateUserForm(@ModelAttribute("createUserFormDTO") SiteUser.SiteUserDTO createUserFormDTO) {
		System.out.print(createUserFormDTO);
		return "redirect:/";
	}
	
	@DeleteMapping("/delete")
	public String deleteUser(@RequestParam("userId") long userId) {
		System.out.println(userId);
		return "redirect:/";
	}

}
