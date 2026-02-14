package bookcafe.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class RestBookController {
	
	@GetMapping("/create")
	public String createBookFromApi(@RequestParam("title")String title) {
		System.out.println(title);
		return title;
	}
}
