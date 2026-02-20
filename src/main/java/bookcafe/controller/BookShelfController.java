package bookcafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.data.dto.BookShelfWithBooksDto;
import bookcafe.data.dto.creation.BookShelfCreationDto;
import bookcafe.data.entity.BookShelf;
import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.BookShelfRepository;
import bookcafe.service.BookShelfDtoService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/book-shelf")
@AllArgsConstructor
public class BookShelfController {
	
//	BookRepository bookRepo;
	
	BookShelfRepository bookShelfRepo;
	
	private BookShelfDtoService bsDtoService;
	
	@GetMapping
	public String displayBookShelfById(Model model, @RequestParam("bookShelfId")long bookShelfId) {
		
		BookShelfWithBooksDto bookShelfWithBooks = 
				bsDtoService.getBookShelfDtosForDisplay(bookShelfId);
				
		model.addAttribute("bookShelfWithBooks", bookShelfWithBooks);
		
//		model.addAttribute("bookShelf", bookShelfRepo.findById(bookShelfId).get());
//		model.addAttribute("books", bookRepo.findByBookShelfId(bookShelfId));
		
		return "/book-shelf";
	}
	
	
	@GetMapping("/create")
	public String getBookShelfCreationForm(Model model) {
		model.addAttribute("bookShelfCreationDto", new BookShelfCreationDto());
		return "/form/book-shelf-creation-form";
	}
	
	
	@PostMapping("/create")
	public String processBookShelfCreationForm(@ModelAttribute("bookShelfCreationDto")BookShelfCreationDto bookShelfDTO) {
		bookShelfRepo.save(BookShelf.builder().name(bookShelfDTO.getName()).build());
		return "redirect:/";
	}
	
	@GetMapping(value = "/modify", params = "bookShelfId")
	public String getBookShelfModificationForm(Model model, @RequestParam("bookShelfId")Long bookShelfId) {
		
		model.addAttribute("bookShelfCreationDto", bookShelfRepo.findCreationDtoById(bookShelfId));
		return "/form/book-shelf-creation-form";
	}
	

}
