package bookcafe.controller;

import java.util.Base64;
import java.util.List;
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

import bookcafe.data.dto.creation.BookCreationDto;
import bookcafe.data.dto.display.BookShelfWithBooksDisplayDto;
import bookcafe.data.entity.Book;
import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.BookShelfRepository;
import bookcafe.exception.InaccessibleItemException;
import bookcafe.restcontroller.ApiResponse;
import bookcafe.security.CustomUserDetails;
import bookcafe.service.BookService;
import bookcafe.util.ItemOwnerChecker;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/book")
@AllArgsConstructor
public class BookController {
	
	BookRepository bookRepo;
	
	BookShelfRepository bookShelfRepo;
	
	BookService bookService;
	
	private ItemOwnerChecker itemOwnerChecker;
	
	
	@GetMapping
	public String displayBookDetailById(Model model, @RequestParam("bookId")long bookId) {
		
		
		Book book = bookRepo.findById(bookId).
				orElseThrow(()->new NoSuchElementException("존재하지 않는 책입니다."));
		
		itemOwnerChecker.throwExceptionIfNotOwner(book);
		
//		if(!ItemOwnerChecker.isOwnerOfItem(book, userDetails))
//			throw new InaccessibleItemException("접근이 불가능한 책입니다.");
		
		
		if(book.getCoverImage() != null) {
			String coverImg = Base64.getEncoder().encodeToString(book.getCoverImage());
			model.addAttribute("coverImg", coverImg);
		}
		
		model.addAttribute("book", book);
		
		return "/book";
	}
	
	
	
	@GetMapping("/create")
	public String getBookCreationForm(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		List<BookShelfWithBooksDisplayDto> bookShelves = bookShelfRepo.findAllDisplayDtosByUserIdForOnlyName(userDetails.getId());
						

		model.addAttribute("book", new BookCreationDto());
		model.addAttribute("bookShelves", bookShelves);
		return "/form/book-creation-form";
	}
	
	
	
	@PostMapping("/create")
	public String processBookCreationForm(@ModelAttribute("bookCreationDto")BookCreationDto bookCreationDto){
		
		bookService.createNewBook(bookCreationDto);
	
		return "redirect:/";
	}
	
	
	@GetMapping("/modify")
	public String getBookModificationForm(Model model, @AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestParam("bookId")long bookId) {
		
		
		List<BookShelfWithBooksDisplayDto> bookShelves = bookShelfRepo.findAllDisplayDtosByUserIdForOnlyName(userDetails.getId());
				
		Book b = bookRepo.findById(bookId).
				orElseThrow(()->new NoSuchElementException("존재하지 않는 책입니다."));
		
		itemOwnerChecker.throwExceptionIfNotOwner(b);
		
//		if(!ItemOwnerChecker.isOwnerOfItem(b, userDetails))
//			throw new InaccessibleItemException("접근이 불가능한 책입니다.");
		
		
		BookCreationDto book = bookRepo.findCreationDtoById(bookId);

		
		model.addAttribute("book", book);
		model.addAttribute("bookId", bookId);
		model.addAttribute("bookShelves", bookShelves);
		
		return "/form/book-creation-form";
	}
	
	
	@PostMapping("/modify")
	public String processBookModificationForm(@ModelAttribute("bookCreationDto")BookCreationDto bookCreationDto ,
			@ModelAttribute("bookId")Long bookId){
	
		
		bookService.modifyBookInfo(bookCreationDto, bookId);
		
		return "redirect:/";
	}
	
	
	
	@ResponseBody
	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse<?>> delete(@RequestParam("bookId")Long bookId, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		Book book = bookRepo.findById(bookId).
				orElseThrow(()->new NoSuchElementException("존재하지 않는 책입니다."));
		
		itemOwnerChecker.throwExceptionIfNotOwner(book);
		
		
		bookRepo.deleteById(bookId);
		
		return ResponseEntity.ok(new ApiResponse<>("책이 삭제되었습니다.", null));
	}
}
