package bookcafe.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bookcafe.data.entity.Book;
import bookcafe.data.entity.BookShelf;
import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.BookShelfRepository;
import bookcafe.data.valueobject.BookInfo;
import bookcafe.security.CustomUserDetails;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/book")
@AllArgsConstructor
public class BookController {
	
	BookRepository bookRepo;
	
	BookShelfRepository bookShelfRepo;
	
	@GetMapping("/create")
	public String createBook(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		List<BookShelf> bookShelfs = bookShelfRepo.findByUserId(userDetails.getId());

		model.addAttribute("bookInfo", new BookInfo());
		model.addAttribute("bookShelfs", bookShelfs);
		return "/form/book-creation-form";
	}
	
	@PostMapping("/create")
	public String createBook(@ModelAttribute("bookInfo")BookInfo bookInfo, @ModelAttribute("bookshelf-select")Long bookShelfId) {
		
		bookRepo.save(Book.builder().
				bookInfo(bookInfo).
				bookShelf(bookShelfRepo.getReferenceById(bookShelfId)).build());
		return "redirect:/";
	}
}
