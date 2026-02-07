package bookcafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.data.entity.Book;
import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.BookShelfRepository;
import bookcafe.data.valueobject.BookInfo;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/book")
@AllArgsConstructor
public class BookController {
	
	BookRepository bookRepo;
	
	BookShelfRepository bookShelfRepo;
	
	@GetMapping("/create")
	public String createBook(Model model) {
		model.addAttribute("bookInfo", new BookInfo());
		return "create_book_form";
	}
	
	@PostMapping("/create")
	public String createBook(@ModelAttribute("bookInfo")BookInfo bookInfo) {
		bookRepo.save(Book.builder().
				bookInfo(bookInfo).
				bookShelf(bookShelfRepo.findById(1L).get()).build());
		return "redirect:/";
	}
}
