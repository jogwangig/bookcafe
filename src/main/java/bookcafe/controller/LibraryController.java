package bookcafe.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bookcafe.data.entity.BookShelf;
import bookcafe.data.entity.BookShelf.BookShelfDTO;
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
	
	@GetMapping
	public String myLibrary(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		List<BookShelf> bookShelfList = bookShelfRepo.findByUserId(userDetails.getId());
		List<BookShelfDTO> bookShelfDTOs = new ArrayList<>();
		
		for(BookShelf bookShelf : bookShelfList) {
			BookShelfDTO b = BookShelfDTO.builder().id(bookShelf.getId()).name(bookShelf.getName())
													.books(bookRepo.findByBookShelfId(bookShelf.getId())).build();
			bookShelfDTOs.add(b);
		}
		
		model.addAttribute("bookShelfDTOs", bookShelfDTOs);
		
		return "/my-library";
	}

}
