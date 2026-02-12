package bookcafe.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	
	
	@GetMapping
	public String getBookShelfById(Model model, @RequestParam("bookId")long bookId) {
		Book book = bookRepo.findById(bookId).get();
		
		if(book.getCoverImage() != null) {
			String coverImg = Base64.getEncoder().encodeToString(book.getCoverImage());
			model.addAttribute("coverImg", coverImg);
		}
		
		model.addAttribute("book", book);
		
		return "/book";
	}
	
	
	
	@GetMapping("/create")
	public String createBook(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		List<BookShelf> bookShelfs = bookShelfRepo.findByUserId(userDetails.getId());

		model.addAttribute("bookInfo", new BookInfo());
		model.addAttribute("bookShelfs", bookShelfs);
		return "/form/book-creation-form";
	}
	
	@PostMapping("/create")
	public String createBook(@ModelAttribute("bookInfo")BookInfo bookInfo, @ModelAttribute("bookshelf-select")Long bookShelfId
			,@RequestParam("coverImage")MultipartFile coverImg) throws IOException{
		
		if(!coverImg.isEmpty()) {
			bookRepo.save(Book.builder().
					coverImage(coverImg.getBytes()).
					bookInfo(bookInfo).
					bookShelf(bookShelfRepo.getReferenceById(bookShelfId)).build());
		}else {
			bookRepo.save(Book.builder().
					bookInfo(bookInfo).
					bookShelf(bookShelfRepo.getReferenceById(bookShelfId)).build());
		}
		
		return "redirect:/";
	}
}
