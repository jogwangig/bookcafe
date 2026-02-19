package bookcafe.restcontroller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bookcafe.data.entity.BookClubParticipant;
import bookcafe.data.repository.BookClubParticipantRepository;
import bookcafe.data.repository.BookClubRepository;
import bookcafe.security.CustomUserDetails;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/book-club")
@AllArgsConstructor
public class BookClubRestController {
	
	private BookClubParticipantRepository bookClubParticipantRepo; 
	
	private BookClubRepository bookClubRepo;
	
	@GetMapping("/register")
	public String registerUser(@RequestParam("bookClubId") long bookClubId, @AuthenticationPrincipal CustomUserDetails userDetails) {
		bookClubParticipantRepo.findByUserIdAndBookClubId(userDetails.getId(), bookClubId)
		.ifPresentOrElse(e->{
			System.out.println("등록 실패");},
				()->{
						bookClubParticipantRepo.save(new BookClubParticipant(
									bookClubRepo.getReferenceById(bookClubId)));});
		
		return "틍록완료";
	}

}
