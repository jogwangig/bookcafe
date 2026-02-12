package bookcafe.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import bookcafe.data.entity.Book;
import bookcafe.data.entity.BookShelf;
import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.BookShelfRepository;
import bookcafe.data.valueobject.BookInfo;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class DefaultController {
		
	private BookRepository bookRepo;
	
	private BookShelfRepository bookShelfRepo;	
		
	@GetMapping("/")
	public String index() {
		return "/index";
	}
	
	@GetMapping("/init")
	public String init() {
				
		BookShelf bs;
		Book b;
		for(int i = 0; i < 5; i++) {
			
			bs = BookShelf.builder().name("책장 : " + i).build();
			bs = bookShelfRepo.save(bs);
			
			for(int j = 0; j < 5; j++) {
				b = Book.builder().bookInfo(BookInfo.builder().title("책 제목 : " + j).build()).bookShelf(bs).build();
				bookRepo.save(b);
			}
			
		}
		
		return "/index";
	}

}
