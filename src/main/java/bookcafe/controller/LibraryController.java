package bookcafe.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bookcafe.data.dto.BookShelfWithBooksDto;
import bookcafe.data.entity.BookShelf;
import bookcafe.data.entity.BookShelf.BookShelfDTO;
import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.BookShelfRepository;
import bookcafe.security.CustomUserDetails;
import bookcafe.service.BookShelfDtoService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/library")
@AllArgsConstructor
public class LibraryController {
	
	private BookShelfRepository  bookShelfRepo;
	
	private BookShelfDtoService bsDtoService;
	
	private BookRepository bookRepo;
	
	@GetMapping
	public String displayMyLibrary(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
//		List<BookShelf> bookShelves = bookShelfRepo.findByUserId(userDetails.getId());
		
		List<BookShelfWithBooksDto> bookShelfWithBooksDtos = 
				bsDtoService.getDtoForBookShelfDisplay(userDetails.getId());
//		List<BookShelfDTO> bookShelfDTOs = new ArrayList<>();
		
//		bookShelfRepo.findByUserIdWithBooks(userDetails.getId()).forEach(System.out::println);
//		bookShelves.forEach(bs->{
//			bookRepo.findDtoByBookShelfId(bs.getId()).forEach(b->{
//				System.out.println(b);
//			});
//		});
//		
//		for(BookShelf bookShelf :bookShelves) {
//			BookShelfDTO b = BookShelfDTO.builder().id(bookShelf.getId()).name(bookShelf.getName())
//													.books(bookRepo.findByBookShelfId(bookShelf.getId())).build();
//			bookShelfDTOs.add(b);
//		}
		
		model.addAttribute("bookShelfWithBooksDtos", bookShelfWithBooksDtos);
//		model.addAttribute("bookShelves", bookShelves);
		
		return "/my-library";
	}

}
