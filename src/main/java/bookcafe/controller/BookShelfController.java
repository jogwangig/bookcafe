package bookcafe.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bookcafe.data.dto.creation.BookShelfCreationDto;
import bookcafe.data.dto.display.BookShelfWithBooksDisplayDto;
import bookcafe.data.entity.Book;
import bookcafe.data.entity.BookShelf;
import bookcafe.data.repository.BookShelfRepository;
import bookcafe.service.BookShelfDisplayService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/book-shelf")
@AllArgsConstructor
public class BookShelfController {
	
	
	BookShelfRepository bookShelfRepo;
	
	private BookShelfDisplayService bsService;
	
	@GetMapping
	public String displayBookShelfById(Model model, @RequestParam("bookShelfId")long bookShelfId) {
		
		BookShelfWithBooksDisplayDto bookShelfWithBooks = 
				bsService.getBookShelfDtosForDisplay(bookShelfId);
				
		model.addAttribute("bookShelfWithBooks", bookShelfWithBooks);
		
		return "/book-shelf";
	}
	
	
	@GetMapping("/create")
	public String getBookShelfCreationForm(Model model) {
		model.addAttribute("bookShelfCreationDto", new BookShelfCreationDto());
		return "/form/book-shelf-creation-form";
	}
	
	
	@PostMapping("/create")
	public String processBookShelfCreationForm(@ModelAttribute("bookShelfCreationDto")BookShelfCreationDto bookShelfDTO) {
		bookShelfRepo.save(bookShelfDTO.toEntity());
		return "redirect:/";
	}
	
	
	@GetMapping(value = "/modify", params = "bookShelfId")
	public String getBookShelfModificationForm(Model model, @RequestParam("bookShelfId")Long bookShelfId) {
		
		model.addAttribute("bookShelfId",bookShelfId);
		model.addAttribute("bookShelfCreationDto", bookShelfRepo.findCreationDtoById(bookShelfId));
		return "/form/book-shelf-creation-form";
	}
	
	
	
	@PostMapping(value = "/modify", params = "bookShelfId")
	public String processBookShelfModificationForm(@RequestParam("bookShelfId")Long bookShelfId,
			@ModelAttribute("bookShelfCreationDto")BookShelfCreationDto bookShelfDTO) {
		
		BookShelf bs = bookShelfRepo.findById(bookShelfId).get();
		
		bs.setName(bookShelfDTO.getName());
		
		bookShelfRepo.save(bs);
		
		
		return "redirect:/";
	}
	
	@ResponseBody
	@DeleteMapping("/delete")
	public String delete(@RequestParam("bookShelfId")Long bookShelfId) {
		
		List<Book> books = bookShelfRepo.findById(bookShelfId).get().getBooks();
		
		
		if(!books.isEmpty()) {
			return "책이 존재하는 책장은 삭제 할 수 없습니다.";
		}
		
		bookShelfRepo.deleteById(bookShelfId);
		
		return "삭제 성공";
	}
	

}
