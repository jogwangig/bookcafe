package bookcafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bookcafe.data.entity.Book;
import bookcafe.data.repository.BookRepository;
import bookcafe.data.valueobject.BookInfo;

@Controller
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	BookRepository bookRepo;
	
	@GetMapping("/create")
	public String createBook(Model model) {
		model.addAttribute("bookInfo", new BookInfo());
		return "create_book_form";
	}
	
	@PostMapping("/create")
	public String createBook(@ModelAttribute("bookInfo")BookInfo bookInfo) {
		System.out.println(bookInfo);
		System.out.println(
		bookRepo.save(Book.builder().numberOfReadingRecord(8).bookInfo(bookInfo).build())
		);
		return "redirect:/";
	}
}
