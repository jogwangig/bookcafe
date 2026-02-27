package bookcafe.controller;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.BookShelfRepository;
import bookcafe.restcontroller.ApiResponse;
import bookcafe.security.CustomUserDetails;
import bookcafe.service.BookShelfDisplayService;
import bookcafe.util.ItemOwnerChecker;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/book-shelf")
@AllArgsConstructor
public class BookShelfController {
	
	private BookRepository bookRepo;
	
	private BookShelfRepository bookShelfRepo;
	
	private BookShelfDisplayService bsService;
	
	private ItemOwnerChecker itemOwnerChecker;
	
	@GetMapping
	public String displayBookShelfById(Model model, @RequestParam("bookShelfId")long bookShelfId) {
		
		BookShelf bookShelf = bookShelfRepo.findById(bookShelfId).
				orElseThrow(()->new NoSuchElementException("존재하지 않는 책장입니다."));
		
		itemOwnerChecker.throwExceptionIfNotOwner(bookShelf);
		
		
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
		
		BookShelf bookShelf = bookShelfRepo.findById(bookShelfId).
				orElseThrow(()->new NoSuchElementException("존재하지 않는 책장입니다."));
		
		itemOwnerChecker.throwExceptionIfNotOwner(bookShelf);
		
		model.addAttribute("bookShelfId",bookShelfId);
		model.addAttribute("bookShelfCreationDto", bookShelfRepo.findCreationDtoById(bookShelfId));
		return "/form/book-shelf-creation-form";
	}
	
	
	
	@PostMapping(value = "/modify", params = "bookShelfId")
	public String processBookShelfModificationForm(@RequestParam("bookShelfId")Long bookShelfId,
			@ModelAttribute("bookShelfCreationDto")BookShelfCreationDto bookShelfDTO) {
		
		BookShelf bookShelf = bookShelfRepo.findById(bookShelfId).
				orElseThrow(()->new NoSuchElementException("존재하지 않는 책장입니다."));
		
		itemOwnerChecker.throwExceptionIfNotOwner(bookShelf);
		
		bookShelf.setName(bookShelfDTO.getName());
		
		bookShelfRepo.save(bookShelf);
		
		
		return "redirect:/";
	}
	
	@ResponseBody
	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse<?>> delete(@RequestParam("bookShelfId")Long bookShelfId, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		BookShelf bookShelf = bookShelfRepo.findById(bookShelfId).
						orElseThrow(()->new NoSuchElementException("존재하지 않는 책입니다."));
		
		itemOwnerChecker.throwExceptionIfNotOwner(bookShelf);
		
		long bookNum = bookRepo.countByBookShelfId(bookShelfId);
				
		
		if(bookNum != 0) {
			return ResponseEntity.badRequest().body(new ApiResponse<>("책이 존재하는 책장은 삭제 할 수 없습니다.", null));
					
		}
		
		
		bookShelfRepo.deleteById(bookShelfId);
		
		return ResponseEntity.ok(new ApiResponse<>("책장이 삭제 되었습니다.", null));
				
	}
	

}
