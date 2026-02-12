package bookcafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.data.entity.BookShelf;
import bookcafe.data.entity.BookShelf.BookShelfDTO;
import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.BookShelfRepository;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/book-shelf")
@AllArgsConstructor
public class BookShelfController {
	
	BookRepository bookRepo;
	
	BookShelfRepository bookShelfRepo;
	
	@GetMapping
	public String getBookShelfById(Model model, @RequestParam("bookShelfId")long bookShelfId) {
		model.addAttribute("bookShelf", bookShelfRepo.findById(bookShelfId).get());
		model.addAttribute("books", bookRepo.findByBookShelfId(bookShelfId));
		
		return "/book-shelf";
	}
	
	@GetMapping("/create")
	public String createBookShelf(Model model) {
		model.addAttribute("bookShelfDTO", new BookShelfDTO());
		return "/form/book-shelf-creation-form";
	}
	
	@PostMapping("/create")
	public String processBookShelfCreation(@ModelAttribute("bookShelfDTO")BookShelfDTO bookShelfDTO) {
		bookShelfRepo.save(BookShelf.builder().name(bookShelfDTO.getName()).build());
		return "redirect:/";
	}

}
