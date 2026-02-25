package bookcafe.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import bookcafe.data.repository.BookShelfRepository;
import bookcafe.data.repository.MessageRepository;
import bookcafe.security.CustomUserDetails;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class DefaultController {
			
	private BookShelfRepository bookShelfRepo;	
	
	private MessageRepository msgRepo;
		
	@GetMapping("/")
	public String index(Model model, @AuthenticationPrincipal Object principal) {
		if(principal instanceof CustomUserDetails)
			model.addAttribute("msgNum", msgRepo.countUnreadMsgByReceipientId( ((CustomUserDetails)principal).getId() ));
		
		return "/index";
	}
	
	
	@GetMapping("/search-book")
	public String searchBookByOpenLibraryApi(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		model.addAttribute("bookShelves" , bookShelfRepo.findByUserId(userDetails.getId()));
		return "/search-book";
	}
	

}
