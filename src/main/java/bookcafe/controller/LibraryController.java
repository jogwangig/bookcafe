package bookcafe.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bookcafe.data.dto.display.BookShelfWithBooksDisplayDto;
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
		
		List<BookShelfWithBooksDisplayDto> bookShelfWithBooksDisplayDtos = 
				bsService.getAllBookShelfDisplayDtos(userDetails.getId());

		
		model.addAttribute("bookShelfWithBooksDtos", bookShelfWithBooksDisplayDtos);
		
		return "/my-library";
	}

}
