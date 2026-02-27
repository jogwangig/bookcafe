package bookcafe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookcafe.data.dto.creation.BookClubCreationDto;
import bookcafe.data.dto.display.BookClubDisplayDto;
import bookcafe.data.entity.BookClub;
import bookcafe.data.entity.BookClubComment;
import bookcafe.data.entity.BookClubParticipant;
import bookcafe.data.entity.SiteUser;
import bookcafe.data.repository.BookClubCommentRepository;
import bookcafe.data.repository.BookClubParticipantRepository;
import bookcafe.data.repository.BookClubRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookClubService {
	
	private BookClubRepository bookClubRepo;
	
	private BookClubParticipantRepository bookClubParticipantRepo;
	
	private BookClubCommentRepository bookClubCommentRepo;
	
	private MessageService msgService;
	
	
	public void createNewBookClub(BookClubCreationDto bookClubCreationDto){
		BookClub bc = bookClubCreationDto.toEntity();
		
		bookClubRepo.save(bc);
	}
	
	
	public void registerCommentToBookClub(Long bookClubId, BookClubComment newComment) {
		
		List<BookClubParticipant> participants = bookClubParticipantRepo.findByBookClubId(bookClubId);
		
		List<SiteUser> users = participants.stream().map(BookClubParticipant::getUser)
													.toList();
		
		BookClubDisplayDto bookClub = bookClubRepo.findDisplayDtoByIdForOnlyName(bookClubId);
		
		String msgContent = "독서 모임 : " + bookClub.getName() + "  에 새로운 댓글이 달렸습니다.";
		
		newComment.setBookClub(bookClubRepo.getReferenceById(bookClubId));
		
		bookClubCommentRepo.save(newComment);
		
		msgService.sendMessageFromAdmin(users, msgContent);
	}
	
	public void registerUser(Long bookClubId) {
		
		bookClubParticipantRepo.save(new BookClubParticipant(
								bookClubRepo.getReferenceById(bookClubId)));
	}
	
	public void unregisterUser(Long bookClubId, Long userId) {
		
		bookClubParticipantRepo.deleteByUserIdAndBookClubId(userId , bookClubId);
		
	}
	
	
	

}
