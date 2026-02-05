package bookcafe.controller;

import java.security.Principal;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.BookShelfRepository;
import bookcafe.security.CustomUserDetails;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/library")
@AllArgsConstructor
public class LibraryController {
	
	private BookShelfRepository  bookShelfRepo;
	
	private BookRepository bookRepo;
	
	@GetMapping()
	public String myLibrary(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		bookShelfRepo.findBySiteUserId(userDetails.getId()).forEach(a->System.out.println(a.getName()));
		
		return "my-library";
	}

}
