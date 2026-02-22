package bookcafe.controller;

import java.util.Base64;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bookcafe.data.dto.BookClubDetailDto;
import bookcafe.data.dto.creation.BookClubCreationDto;
import bookcafe.data.entity.BookClubComment;
import bookcafe.data.repository.BookClubCommentRepository;
import bookcafe.data.repository.BookClubRepository;
import bookcafe.security.CustomUserDetails;
import bookcafe.service.BookClubService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/book-club")
public class BookClubController {
	
	private BookClubService bookClubService;
	
	private BookClubCommentRepository bookClubCommentRepo;
	
	private BookClubRepository bookClubRepo;
		
	
	@GetMapping
	public String displayBookClubs(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		model.addAttribute("bookClubs",bookClubRepo.findAllDisplayDtos(userDetails.getId()));
		
		return "/book-club-list";
	}
	
	
	@GetMapping(params = "bookClubId")
	public String displayBookClub(Model model , @RequestParam("bookClubId")long bookClubId) {
		BookClubDetailDto bookClub = bookClubRepo.findDetailDtoById(bookClubId);
		
		if(bookClub.getCoverImg() != null) {
			String coverImg = Base64.getEncoder().encodeToString(bookClub.getCoverImg());
			model.addAttribute("coverImg", coverImg);
		}
		
		model.addAttribute("comments", bookClubCommentRepo.findByBookClubId(bookClubId));
		model.addAttribute("newComment", new BookClubComment());
		model.addAttribute("bookClub", bookClub);
		return "book-club";
	}
	
	
	
	@PostMapping("/register")
	public String registerCommentToBookClub(@RequestParam("bookClubId")Long bookClubId,
										@ModelAttribute("newComment") BookClubComment newComment) {
				
		bookClubService.registerCommentToBookClub(bookClubId, newComment);
		
		return "redirect:/";
	}
	
	
	
	@GetMapping("/create")
	public String getBookClubCreationForm(Model model) {
		model.addAttribute("bookClub", new BookClubCreationDto());
		return "/form/book-club-creation-form";
	}
	
	
	@PostMapping("/create")
	public String processBookClubCreationForm(BookClubCreationDto bookClubCreationDto){

		bookClubService.createNewBookClub(bookClubCreationDto);
		
		return "redirect:/";
	}
}
