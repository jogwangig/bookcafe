package bookcafe.restcontroller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bookcafe.data.entity.BookClubParticipant;
import bookcafe.data.repository.BookClubParticipantRepository;
import bookcafe.data.repository.BookClubRepository;
import bookcafe.security.CustomUserDetails;
import bookcafe.service.BookClubService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/book-club")
public class BookClubRestController {

    private BookClubService bookClubService;
	
	private BookClubParticipantRepository bookClubParticipantRepo; 
	
	private BookClubRepository bookClubRepo;

	
	@GetMapping("/register")
	public ResponseEntity<ApiResponse<?>> registerUser(@RequestParam("bookClubId") long bookClubId, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		if(!(userDetails instanceof CustomUserDetails))
			return ResponseEntity.badRequest().body(new ApiResponse<>("독서 모임에 가입하기 위해서는 로그인 해야합니다." , null));
		
		Optional<BookClubParticipant> participant
						= bookClubParticipantRepo.findByUserIdAndBookClubId(userDetails.getId(), bookClubId);
		
		if(participant.isPresent())
			return ResponseEntity.badRequest().body(new ApiResponse<>("이미 해당 모임에 가입되어있습니다." , null));
		
		
		bookClubService.registerUser(bookClubId);
		
		return ResponseEntity.ok(new ApiResponse<>("모임에 가입되었습니다." , null));
		
		
	}
	
	@GetMapping("/unregister")
	public ResponseEntity<ApiResponse<?>> unregisterUser(@RequestParam("bookClubId") long bookClubId, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		if(!(userDetails instanceof CustomUserDetails))
			return ResponseEntity.badRequest().body(new ApiResponse<>("독서 모임에 탈퇴하기 위해서는 로그인 해야합니다." , null));
		
		Optional<BookClubParticipant> participant
						= bookClubParticipantRepo.findByUserIdAndBookClubId(userDetails.getId(), bookClubId);
		
		if(participant.isEmpty())
			return ResponseEntity.badRequest().body(new ApiResponse<>("가입하지 않은 모임에 탈퇴 할 수 없습니다." , null));
		
		bookClubService.unregisterUser(bookClubId, userDetails.getId());

		return ResponseEntity.ok(new ApiResponse<>("모임에 탈퇴했습니다." , null));
		
	}

}
