package bookcafe.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bookcafe.data.dto.display.BookShelfWithBooksDto;
import bookcafe.security.CustomUserDetails;
import bookcafe.service.BookShelfDisplayService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/library")
@AllArgsConstructor
public class LibraryController {
	
//	private BookShelfRepository  bookShelfRepo;
	
	private BookShelfDisplayService bsService;
	
//	private BookRepository bookRepo;
	
	@GetMapping
	public String displayMyLibrary(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		List<BookShelfWithBooksDto> bookShelfWithBooksDtos = 
				bsService.getAllBookShelfDtosForDisplay(userDetails.getId());

		
		model.addAttribute("bookShelfWithBooksDtos", bookShelfWithBooksDtos);
		
		return "/my-library";
	}

}
