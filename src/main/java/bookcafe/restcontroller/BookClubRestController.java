package bookcafe.restcontroller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> registerUser(@RequestParam("bookClubId") long bookClubId, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		if(!(userDetails instanceof CustomUserDetails))
			return ResponseEntity.badRequest().body(new ApiResponse<>("독서 모임에 가입하기 위해서는 로그인 해야합니다." , null));
		
		Optional<BookClubParticipant> participant
						= bookClubParticipantRepo.findByUserIdAndBookClubId(userDetails.getId(), bookClubId);
		
		if(participant.isPresent())
			return ResponseEntity.badRequest().body(new ApiResponse<>("이미 해당 모임에 가입되어있습니다." , null));
		
		bookClubParticipantRepo.save(new BookClubParticipant(
				bookClubRepo.getReferenceById(bookClubId)));
		
		return ResponseEntity.ok(new ApiResponse<>("모임에 가입되었습니다." , null));
		
//		.ifPresentOrElse(e->{
//			return ResponseEntity.badRequest().body(new ApiResponse<>("이미 해당 모임에 가입되어있습니다." , null));
//			System.out.println("등록 실패");},
//				()->{
//						bookClubParticipantRepo.save(new BookClubParticipant(
//									bookClubRepo.getReferenceById(bookClubId)));});
//		
//		return ResponseEntity.ok(new ApiResponse<>("모임에 가입되었습니다." , null));
		
	}
	
	@GetMapping("/unregister")
	public ResponseEntity<?> unregisterUser(@RequestParam("bookClubId") long bookClubId, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		if(!(userDetails instanceof CustomUserDetails))
			return ResponseEntity.badRequest().body(new ApiResponse<>("독서 모임에 탈퇴하기 위해서는 로그인 해야합니다." , null));
		
		Optional<BookClubParticipant> participant
						= bookClubParticipantRepo.findByUserIdAndBookClubId(userDetails.getId(), bookClubId);
		
		if(participant.isEmpty())
			return ResponseEntity.badRequest().body(new ApiResponse<>("가입하지 않은 모임에 탈퇴 할 수 없습니다." , null));
		
		return ResponseEntity.ok(new ApiResponse<>("모임에 탈퇴했습니다." , null));
		
//		bookClubParticipantRepo.findByUserIdAndBookClubId(userDetails.getId(), bookClubId)
//		.ifPresentOrElse(e->{
//			bookClubParticipantRepo.delete(e);},
//				()->{
//					System.out.println("등록 되지않은 사용자입니다");});
//		
//		return "틍록해제";
	}

}
