package bookcafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.BookShelfRepository;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/library")
@AllArgsConstructor
public class LibraryController {
	
	private BookShelfRepository  bookShelfRepo;
	
	private BookRepository bookRepo;
	
	@GetMapping("/")
	public String myLibrary(Model model) {
		
		return "my-library";
	}

}
