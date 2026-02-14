package bookcafe.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookRestController {
	
	@GetMapping("/create")
	public String createBookFromApi(@ModelAttribute ApiBookCreationDto dto) {
		System.out.println(dto.title);
		System.out.println(dto.coverId);
		
		for(String s :dto.author.split(","))
			System.out.println(s);
		
		System.out.println(dto.bookShelfId);
		return dto.author;
	}
	
	
	@Getter
	@Setter
	private static class ApiBookCreationDto{
		private String title;
		private String coverId;
		private String author;
		private String bookShelfId;
	}
	
}
