package bookcafe.restcontroller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bookcafe.data.entity.Book;
import bookcafe.data.repository.BookRepository;
import bookcafe.data.repository.BookShelfRepository;
import bookcafe.data.valueobject.BookInfo;
import bookcafe.service.OpenLibraryApiService;
import bookcafe.util.ItemOwnerChecker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookRestController {
	
	private OpenLibraryApiService olApiService;
	
	private ItemOwnerChecker itemOwnerChecker;
	
	private BookRepository bookRepo;
	
	private BookShelfRepository bookShelfRepo;
	
	@GetMapping("/create")
	public ResponseEntity<ApiResponse<?>> createBookFromApi(@ModelAttribute ApiBookCreationDto dto) {
		
		itemOwnerChecker.throwExceptionIfNotOwner(bookShelfRepo.getReferenceById(dto.getBookShelfId()));
		
		System.out.println("OpenLibrary Api을 사용하여 조회된 책으로부터 책 생성을 요청 받았습니다.");
		
		byte[] coverImg = olApiService.fetchCoverImgByCoverId(dto.coverId);
		
		Book book = Book.builder().bookInfo(BookInfo.builder().
									title(dto.title).build()).
									bookShelf(bookShelfRepo.getReferenceById(dto.bookShelfId)).
									coverImage(coverImg).build();
		
		bookRepo.save(book);
		
		
		return ResponseEntity.ok(new ApiResponse<>("책이 성공적으로 등록되었습니다", null));
	}
	
	
	@Getter
	@Setter
	private static class ApiBookCreationDto{
		private String title;
		private String coverId;
		private String author;
		private long bookShelfId;
	}
	
}
