package bookcafe.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.data.dto.creation.UserCreationDto;
import bookcafe.data.entity.Message;
import bookcafe.data.entity.SiteUser;
import bookcafe.data.repository.MessageRepository;
import bookcafe.data.repository.SiteUserRepository;
import bookcafe.security.CustomUserDetails;
import bookcafe.service.UserService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
	
	private SiteUserRepository userRepo;
	
	private MessageRepository msgRepo;
	
	private UserService userService;
	
	@GetMapping("/create")
	public String getUserCreationForm(Model model) {
		
		model.addAttribute("userCreationDto", new UserCreationDto());
		return "/form/user-creation-form";
	}
	
	
	
	@PostMapping("/create")
	public String processUserCreationForm(@ModelAttribute("userCreationDto")UserCreationDto userCreationDto) {
		
		userService.createNewUser(userCreationDto);		

		return "redirect:/";
	}
	
	@GetMapping("/modify")
	public String getUserModificationForm(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		SiteUser user = userRepo.findById(userDetails.getId()).get();
		
		UserCreationDto userCreationDto = UserCreationDto.fromEntity(user);
		
		model.addAttribute("userCreationDto", userCreationDto);
		return "/form/user-creation-form";
	}
	
	
	@PostMapping("/modify")
	public String processUserModificationForm(@ModelAttribute("userCreationDto") UserCreationDto userCreationDto,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		
		
		userService.modifyUserInfo(userCreationDto, userDetails.getId());
		
		return "redirect:/";
	}
	
	
	@GetMapping("/delete")
	public String deleteUser(@RequestParam("id") long id) {
		userRepo.deleteById(id);
		return "redirect:/";
	}
	
	@GetMapping("/msg")
	public String displayMsgBox(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		List<Message> msgs = msgRepo.findByReceipientId(userDetails.getId());
		
		msgs.forEach(m->{
			if(!m.isRead())
				m.setRead(true);
		});
		
		msgRepo.saveAll(msgs);
		
		model.addAttribute("msgs", msgs);
		
		return "msg-box";
	}
	
	

}
