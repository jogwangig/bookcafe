package bookcafe.controller;

import java.io.IOException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.data.dto.BookClubDto;
import bookcafe.data.repository.BookClubRepository;
import bookcafe.security.CustomUserDetails;
import bookcafe.service.BookClubService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/book-club")
public class BookClubController {
	
	private BookClubService bookClubService;
	
	private BookClubRepository bookClubRepo;
	
	@GetMapping
	public String displayBookClubs(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		model.addAttribute("bookClubs",bookClubRepo.findDtos(userDetails.getId()));
		return "/book-club-list";
	}
	
	@GetMapping(params = "bookClubId")
	public String displayBookClub(Model model , @RequestParam("bookClubId")long bookClubId) {
		model.addAttribute("bookClub", bookClubRepo.findById(bookClubId).get());
		return "book-club";
	}
	
	
	@GetMapping("/create")
	public String getBookClubCreationForm(Model model) {
		model.addAttribute("bookClub", new BookClubDto());
		return "/form/book-club-creation-form";
	}
	
	
	@PostMapping("/create")
	public String processBookClubCreationForm(BookClubDto bookClubDto) throws IOException {

		bookClubService.createNewBookClub(bookClubDto);
		return "redirect:/";
	}
}
