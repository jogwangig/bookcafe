package bookcafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bookcafe.data.entity.Book;
import bookcafe.data.entity.BookShelf.BookShelfDTO;
import bookcafe.data.repository.BookShelfRepository;
import bookcafe.data.valueobject.BookInfo;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/book-shelf")
@AllArgsConstructor
public class BookShelfController {
	
	BookShelfRepository bookShelfRepo;
	
	@GetMapping("/create")
	public String createBookShelf(Model model) {
		model.addAttribute("bookShelfDTO", new BookShelfDTO());
		return "/form/book-shelf-creation-form";
	}
	
	@PostMapping("/create")
	public String processBookShelfCreation(@ModelAttribute("bookShelfDTO")BookShelfDTO bookShelfDTO) {
		return "redirect:/";
	}

}
